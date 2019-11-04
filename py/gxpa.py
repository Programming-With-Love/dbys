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
	user='123',
	password='123',
	database='ys',
	charset='utf8'
)
class Ji:
	name=""
	url=""
	def __init__(self, name, url):
		self.name = name
		self.url = url

proxys=[]
header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36'}
#获取代理
def get_proxy():
	global proxys
	r=requests.get("http://ip.jiangxianli.com/")
	if(r.status_code==200):
		selector=etree.HTML(r.text)
		proxys=proxys+selector.xpath("//button[@class=\"btn btn-sm btn-copy\"]/@data-url")
#获取页面html
def getHtml(url):
	global proxys
	p=proxys[random.randint(0,len(proxys)-1)]
	try:
		html = requests.get(url,proxies={'http':p},headers=header,timeout=5)
		if html!=None:
			return html.text
	except Exception:
		proxys.remove(p)
		if len(proxys)<2:
			get_proxy()
		return getHtml(url)
def main():
	get_proxy()
	list=[]
	for i in range(1,5):
		selector=etree.HTML(getHtml("http://www.zuidazy1.net/?m=vod-type-id-{}.html".format(i)))
		list+=selector.xpath("//li/span/a/@href")
	#创建线程池
	pool = ThreadPool(20)
	for urlstr in list:
		url="http://www.zuidazy1.net"+urlstr
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
	selector=etree.HTML(r)
	list=selector.xpath("//div[@class=\"vodh\"]/h2/text()")
	pm=ifnull(list)
	if cursor.execute("SELECT pf,dy,dq,yy,gkdz,xzdz,id,pctime FROM `ysb` WHERE `pm` LIKE '"+pm+"'")>0:
		try:
			ys=cursor.fetchall()[0]
			id=ys[6]
			pf=str(ys[0])
			dy=str(ys[1])
			dq=str(ys[2])
			yy=str(ys[3])
			gkdz=str(ys[4])
			xzdz=str(ys[5])
			pctime=str(ys[7])
			if pf=="" or pf=='0' or pf=="0.0":
				list=selector.xpath("//div[@class=\"vodh\"]/label/text()")
				gx=str(ifnull(list))
				cursor.execute("UPDATE `ysb` SET `pf` = '{}' WHERE `ysb`.`id` = {}".format(str(gx),str(id)))
				print("更新"+str(id)+"pf")
				conn.commit()
			if dy=="":
				list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[2]/span/text()")
				gx=ifnull(list)
				cursor.execute("UPDATE `ysb` SET `dy` = '{}' WHERE `ysb`.`id` = {}".format(str(gx),str(id)))
				print("更新"+str(id)+"dy")
				conn.commit()
			if dq=="":
				list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[5]/span/text()")
				gx=ifnull(list)
				cursor.execute("UPDATE `ysb` SET `dq` = '{}' WHERE `ysb`.`id` = {}".format(str(gx),str(id)))
				print("更新"+str(id)+"dq")
				conn.commit()
			if yy=="":
				list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[6]/span/text()")
				gx=ifnull(list)
				cursor.execute("UPDATE `ysb` SET `yy` = '{}' WHERE `ysb`.`id` = {}".format(str(gx),str(id)))
				print("更新"+str(id)+"yy")
				conn.commit()
			if gkdz=="[]":
				list=selector.xpath("//div[@id=\"play_1\"]/ul/li/text()")
				if len(list)>0:
					list=fenji(list)
					gkdz = json.dumps(list, ensure_ascii=False)
					cursor.execute("UPDATE `ysb` SET `gkdz` = '{}' WHERE `ysb`.`id` = {}".format(gkdz,str(id)))
					print("更新"+str(id)+"gkdz")
					conn.commit()
			if xzdz=="[]":
				list=selector.xpath("//div[@id=\"down_1\"]/ul/li/text()")
				if len(list)>0:
					list=fenji(list)
					xzdz = json.dumps(list, ensure_ascii=False)
					cursor.execute("UPDATE `ysb` SET `xzdz` = '{}' WHERE `ysb`.`id` = {}".format(xzdz,str(id)))
					print("更新"+str(id)+"xzdz")
					conn.commit()
			if pctime=="" or ys[7]=='0':
				list=selector.xpath("//div[@class=\"vodinfobox\"]/ul/li[8]/span/text()")
				gx=str(ifnull(list))
				cursor.execute("UPDATE `ysb` SET `pctime` = '{}' WHERE `ysb`.`id` = {}".format(str(gx),str(id)))
				print("更新"+str(id)+"pctime")
				conn.commit()
		except Exception as e:
			print(e)
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