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
	mincached=20,  # 初始化时，链接池中至少创建的空闲的链接，0表示不创建
	maxcached=5,  # 链接池中最多闲置的链接，0和None不限制
	maxshared=0,  # 链接池中最多共享的链接数量，0和None表示全部共享。PS: 无用，因为pymysql和MySQLdb等模块的 threadsafety都为1，所有值无论设置为多少，_maxcached永远为0，所以永远是所有链接都共享。
	blocking=True,	# 连接池中如果没有可用连接后，是否阻塞等待。True，等待；False，不等待然后报错
	maxusage=None,	# 一个链接最多被重复使用的次数，None表示无限制
	setsession=[],	# 开始会话前执行的命令列表。如：["set datestyle to ...", "set time zone ..."]
	ping=0,
	# ping MySQL服务端，检查是否服务可用。# 如：0 = None = never, 1 = default = whenever it is requested, 2 = when a cursor is created, 4 = when a query is executed, 7 = always
	host='185.207.153.189',
	port=3306,
	user='ys',
	password='hjj225',
	database='ys',
	charset='utf8'
)
proxys=[]
header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36'}
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
			html = requests.get(url,proxies={'http':proxys[i]},headers=header,timeout=5)
			if html!=None:
				return html.text
		except Exception:
			proxys.remove(proxys[i])
			if len(proxys)<5:
				get_proxy(10)
			return getHtml(url,True)
	else :
		html = requests.get(url,headers=header,timeout=5)
		return html.text
def main():
	global proxys
	get_proxy(10)
	print(proxys)
	conn = POOL.connection()
	cursor = conn.cursor()
	cursor.execute("SELECT id,pm,dy,lx,gkdz,xzdz FROM `ysb` ORDER BY `gxtime` DESC LIMIT 1000")
	data = cursor.fetchall()
	rpool = redis.ConnectionPool(host='185.207.153.189', port=6379,db=0,password='danbairedis225')
	pool = ThreadPool(30)
	for ys in data:
		try:
			r = redis.Redis(connection_pool=rpool)
			pool.apply_async(run, args=(ys,r))
		except Exception as e:
			print(e)
	pool.close()
	pool.join()
	print("结束")
def run(ys,r):
	if ys[4] != '[]':
		jys=json.loads(ys[4])
	else :
		jys=json.loads(ys[5])
	pm=ys[1]+ys[2]+ys[3]
	tnum=r.get(pm)
	if tnum==None:
		tnum=0
	else:
		tnum=int(tnum)
	if tnum<len(jys):
		fh=getHtml("http://v.qq.com/x/search/?q="+pm,True)
		ysid=re.compile("{id: '(.*)'; type: '2';}",re.M).findall(fh)[0]
		fh=getHtml("http://s.video.qq.com/get_playsource?plat=2&type=4&range=1&otype=json&id="+ysid,True)
		jsonj=json.loads(fh[13:-1])
		jlist=jsonj["PlaylistItem"]["videoPlayList"]
		taglist=[]
		if jsonj["PlaylistItem"]['payType']==2:
			for i in range (0,len(jlist)):
				tfh=getHtml("http://bullet.video.qq.com/fcgi-bin/target/regist?otype=json&vid="+jlist[i]['id'],True)
				tagid=json.loads(tfh[13:-1])['targetid']
				taglist.append(tagid)
			taglist.reverse()
			for i in range (0,len(jys)):
				r.set(pm+str(ys[0])+jys[i]['name'],taglist[i])
			if len(taglist) >0:
				print(pm+str(len(taglist)))
			r.set(pm,len(jys))
if __name__ == "__main__":
	main()