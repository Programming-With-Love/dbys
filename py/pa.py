import json
import random
import requests
from lxml import etree
import re
import pymysql
from DBUtils.PooledDB import PooledDB
from multiprocessing.pool import ThreadPool



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
	host='127.0.0.1',
	port=3306,
	user='ys',
	password='password',
	database='ys',
	charset='utf8'
)
class Ji:
	name=""
	url=""
	def __init__(self, name, url):
		self.name = name
		self.url = url

proxy=""
header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36'}
#获取代理
def get_proxy():
	global proxy
	r=requests.get("http://ip.jiangxianli.com/")
	if(r.status_code==200):
		selector=etree.HTML(r.text)
		i=random.randint(1,15)
		proxy=selector.xpath("//button[@class=\"btn btn-sm btn-copy\"]/@data-url")[i]
#获取页面html
def getHtml(url):
	global proxy
	try:
		html = requests.get(url,headers=header,timeout=5)
		return html
	except Exception as e:
		print(e)
		run(url)
def main():
	r=getHtml("http://www.okzy.co/?m=vod-index-pg-1.html")
	selector=etree.HTML(r.text)
	list=selector.xpath("//span[@class=\"xing_vb4\"]/a/@href")
	#创建线程池
	pool = ThreadPool(20)
	for s in list:
		url="http://www.okzyw.com"+s
		try:
			pool.apply_async(run, args=(url,))
		except Exception as e:
			print(e)
	pool.close()
	pool.join()
	print("结束")
def run(url):
	conn = POOL.connection()
	cursor = conn.cursor()
	#获取页面数据
	r=getHtml(url)
	if(r.status_code==200):
		selector=etree.HTML(r.text)
		#获取需要数据
		id=re.compile("id-(.*).html").findall(r.url)[0]
		list=selector.xpath("//div[@class=\"vodh\"]/h2/text()")
		pm=ifnull(list)
		list=selector.xpath("//img[@class=\"lazy\"]/@src")
		tp="http://img.p00q.cn:222/ys.php?src="+ifnull(list)+"&q=50&w=200&h=285"
		list=selector.xpath("//div[@class=\"vodh\"]/span/text()")
		zt=ifnull(list)
		list=selector.xpath("//div[@class=\"vodh\"]/label/text()")
		pf=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[1]/span/text()")
		bm=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[2]/span/text()")
		dy=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[3]/span/text()")
		zy=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[4]/span/text()")
		lx=ifnull(list)
		if lx.find("福利") > -1:
			return
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[5]/span/text()")
		dq=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[6]/span/text()")
		yy=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[7]/span/text()")
		sytime=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[8]/span/text()")
		pctime=ifnull(list)
		list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[9]/span/text()")
		gxtime=ifnull(list)
		list=selector.xpath("//div[@class=\"vodplayinfo\"]/text()")
		if(len(list)>0):
			js=list[3]
		jilist=selector.xpath("//span[text()=\"ckm3u8\"]/../../ul/li/text()")
		if len(jilist)>0:
			list=fenji(jilist)
			gkdz = json.dumps(list, ensure_ascii=False)
		else:
			gkdz="[]"
		jilist=selector.xpath("//div[@id=\"down_1\"]/ul/li/text()")
		if len(jilist)>0:
			list=fenji(jilist)
			xzdz = json.dumps(list, ensure_ascii=False)
		else:
			xzdz="[]"
		if pm!="":
			#向数据库插入数据
			sql="INSERT INTO ysb (id,pm,tp,zt,pf,bm,dy,zy,lx,dq,yy,sytime,pctime,gxtime,js,gkdz,xzdz) VALUES ({},'{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}','{}') ON DUPLICATE KEY UPDATE zt='{}',gxtime='{}',gkdz='{}',xzdz='{}',tp='{}';".format(id,pm,tp,zt,pf,bm,dy,zy,lx,dq,yy,sytime,pctime,gxtime,js,gkdz,xzdz,zt,gxtime,gkdz,xzdz,tp)
			cursor.execute(sql)
			print("更新"+id+pm)
			conn.commit()
			conn.close()
def fenji(jilist):
	list=[]
	for j in jilist:
		ji =Ji(j.split("$")[0],j.split("$")[1])
		list.append(ji.__dict__)
	return list
def ifnull(list):
	if(len(list)>0):
		return list[0]
	return ""
if __name__ == "__main__":
	main()