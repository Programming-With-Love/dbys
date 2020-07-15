import json
import random
import requests
from lxml import etree
import re
import pymysql
from DBUtils.PooledDB import PooledDB
from multiprocessing.pool import ThreadPool
import redis

POOL = PooledDB(
	creator=pymysql,  # 使用链接数据库的模块
	maxconnections=0,  # 连接池允许的最大连接数，0和None表示不限制连接数
	mincached=0,  # 初始化时，链接池中至少创建的空闲的链接，0表示不创建
	maxcached=5,  # 链接池中最多闲置的链接，0和None不限制
	maxshared=0,  # 链接池中最多共享的链接数量，0和None表示全部共享。PS: 无用，因为pymysql和MySQLdb等模块的 threadsafety都为1，所有值无论设置为多少，_maxcached永远为0，所以永远是所有链接都共享。
	blocking=True,	# 连接池中如果没有可用连接后，是否阻塞等待。True，等待；False，不等待然后报错
	maxusage=None,	# 一个链接最多被重复使用的次数，None表示无限制
	setsession=[],	# 开始会话前执行的命令列表。如：["set datestyle to ...", "set time zone ..."]
	ping=0,
	# ping MySQL服务端，检查是否服务可用。# 如：0 = None = never, 1 = default = whenever it is requested, 2 = when a cursor is created, 4 = when a query is executed, 7 = always
	host='128.0.0.1',
	port=3306,
	user='ys',
	password='123',
	database='ys',
	charset='utf8'
)
proxys=[]
header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36'}
#获取代理
def get_proxy(i):
	global proxys
	for num in range(0,i):
		r=requests.get("http://api.p00q.cn/proxy/get")
		if(r.status_code==200):
			rjson = r.text
			rjsonp = json.loads(rjson)
			dl=rjsonp.get('ip')+":"+rjsonp.get("port")
			proxys.append(dl)
#获取页面html
def getHtml(url,p):
	if p:
		global proxys
		try:
			i=random.randint(0,len(proxys)-1)
			proxies={'http': 'http://'+proxys[i]}
			html = requests.get(url,proxies=proxies,headers=header,timeout=3)
			if html!=None:
				return html
		except Exception:
			proxys.remove(proxys[i])
			if len(proxys)<2:
				get_proxy(10)
			return getHtml(url,True)
	else :
		html = requests.get(url,headers=header,timeout=5)
		return html
def main():
	print("开始")
	global proxys
	#get_proxy(10)
	print(getHtml("http://api.p00q.cn/ip",False).text)
	type("tv")
	type("cartoon")
	type("movie")
	print("结束")
def type(t):
	pool = ThreadPool(10)
	rpool = redis.ConnectionPool(host='127.0.0.1', port=6379,db=0,password='123123')
	conn = POOL.connection()
	for j in range(10):
		try:
			html=getHtml("http://v.qq.com/x/bu/pagesheet/list?_all=1&append=1&channel="+t+"&listpage=2&pagesize=30&sort=19&offset=1"+str(j*30),False)
			rstr=html.content.decode(encoding="utf-8", errors="strict")
			selector=etree.HTML(rstr)
			idlist=selector.xpath("//a[@class=\"figure_title figure_title_two_row bold\"]/@href")
			pmlist=selector.xpath("//a[@class=\"figure_title figure_title_two_row bold\"]/text()")
			for i in range(0,len(idlist)):
				cursor = conn.cursor()
				cursor.execute("SELECT id,pm,dy,lx,gkdz,xzdz FROM `ysb` WHERE `pm` LIKE '"+pmlist[i]+"'")
				data = cursor.fetchone()
				if(data!=None):
					r = redis.Redis(connection_pool=rpool)
					if data[4] != '[]':
						jys=json.loads(data[4])
					else :
						jys=json.loads(data[5])
					pmkey=data[1]+data[2]+data[3]
					tnum=r.get(pmkey)
					if tnum==None:
						tnum=0
					else:
						tnum=int(tnum)
					id=(re.compile("https://v.qq.com/x/cover/(.*).html").findall(idlist[i])[0])
					if tnum<len(jys):
						pool.apply_async(run, args=(id,r,jys,pmkey,data[0]))
		except Exception as e:
			print(e)
	pool.close()
	pool.join()
def run(id,r,jys,pmkey,ysid):
	try:
		fh=getHtml("http://s.video.qq.com/get_playsource?plat=2&type=4&range=1&otype=json&id="+id,False).text
		jsonj=json.loads(fh[13:-1])
		jlist=jsonj["PlaylistItem"]["videoPlayList"]
		taglist=[]
		for i in range (0,len(jlist)):
			tfh=getHtml("http://bullet.video.qq.com/fcgi-bin/target/regist?otype=json&vid="+jlist[i]['id'],False).text
			tagid=json.loads(tfh[13:-1])['targetid']
			taglist.append(tagid)
		if(len(taglist)>0):
			taglist.reverse()
		else:
			return
		for k in range (0,len(jys)):
			r.set(pmkey+str(ysid)+jys[k]['name'],taglist[k])
		if len(taglist) >0:
			print(pmkey+str(len(taglist)))
			r.set(pmkey,len(taglist))
	except Exception as e:
			print(e)
if __name__ == "__main__":
	main()