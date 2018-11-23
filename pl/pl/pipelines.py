# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import os
import io
import sys
import cx_Oracle

from scrapy.utils.project import get_project_settings
from pl.items import WenkuItem
from pl.items import BookItem

reload(sys)
sys.setdefaultencoding('utf-8')


# 以文件形式保存
def process_item(item, spider):
    curPath = 'F:\content'
    targetPath = curPath + os.path.sep
    if not os.path.exists(targetPath):
        os.makedirs(targetPath)

    filename_path = 'F:\content' + os.path.sep + str(item['sort']) + str(item['chaptername']) + '.txt'

    f = io.open(filename_path.decode('utf-8'), 'w+', encoding='utf-8')
    f.write((item['chaptercontent']).decode('utf-8'))
    f.flush()
    return item


# 关闭数据库连接ef close_spider(self, spider):
def close_spider(self, spider):
    self.cursor.close()
    self.conn.close()


def book_insert(self, item, spider):
    sql = "insert into t_book (id, name) values ('%s','%s')" % (item['id'], item['name'])
    self.cursor.execute(sql)
    self.conn.commit()
    return item


def content_insert(self, item, spider):
    text = item['text']

    sql = "declare \n" \
          "v_clob clob := :text;\n" \
          "begin \n" \
          "insert into T_CONTENT (id, name,text,bookid,sort) values (:id,:name,v_clob,:bookid,:sort);\n" \
          "end;"
    self.cursor.prepare(sql)
    self.cursor.execute(None, {'text': text, 'id': str(item['id']), 'name': str(item['name']), 'bookid': str(item['bookid']),
                               'sort': item['sort']})
    self.conn.commit()
    return item


# 存入数据库
class PlPipeline(object):

    def __init__(self):
        settings = get_project_settings()

        self.name = settings['DB_NAME']
        self.password = settings['DB_PASSWORD']
        self.user = settings['DB_USER']
        self.port = settings['DB_PORT']
        self.host = settings['DB_HOST']

        self.conn = cx_Oracle.connect(
            user=self.user,
            password=self.password,
            dsn=self.host + '/' + self.name,
        )
        self.cursor = self.conn.cursor()

    def process_item(self, item, spider):
        if isinstance(item, BookItem):
            book_insert(self, item, spider)
        elif isinstance(item, WenkuItem):
            content_insert(self, item, spider)
