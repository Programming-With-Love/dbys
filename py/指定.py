import json
import random
import requests
from lxml import etree
import re
import pymysql
from DBUtils.PooledDB import PooledDB
from multiprocessing.pool import ThreadPool

class Ji:
	name=""
	url=""
	def __init__(self, name, url):
		self.name = name
		self.url = url

header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36'}
#获取页面html
def getHtml(url):
	global proxy
	try:
		html = requests.get(url,headers=header,timeout=10)
		return html
	except Exception as e:
		print(e)
		run(url)
def main():
	run('http://1886zy.net/?m=vod-detail-id-33279.html')
def run(url):
	#获取页面数据
	r=getHtml(url)
	if(r.status_code==200):
		selector=etree.HTML(r.text)
		#获取需要数据
		jilist=selector.xpath("//div[@class=\"movievod\"]/ul/li/input/@value")
		gkdz=[]
		if len(jilist)>0:
			list=fenji(jilist)
			list.reverse()
			gkdz = json.dumps(list, ensure_ascii=False)
		else:
			gkdz="[]"

		print(gkdz)
def fenji(jilist):
	list=[]
	for j in jilist:
		if len(j)>20:
			ji =Ji(j.split("$")[0],j.split("$")[1])
			list.append(ji.__dict__)
	return list
def ifnull(list):
	if(len(list)>0):
		return list[0]
	return ""
if __name__ == "__main__":
	main()