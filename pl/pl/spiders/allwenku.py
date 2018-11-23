# -*- coding:utf-8 -*-
import scrapy
import uuid
from scrapy.http import Request

from pl.items import WenkuItem
from pl.items import BookItem


class BookSpider(scrapy.spiders.Spider):
    name = "books"
    allowed_domains = ['wenku8.net']
    start_urls = [
        'https://www.wenku8.net/book/',
    ]

    def start_requests(self):
        pages = []
        for i in range(1, 9999):
            url = 'https://www.wenku8.net/book/%s.htm' % i
            page = scrapy.Request(url)
            pages.append(page)
        return pages

    def parse(self, response):
        readnode = response.xpath(u'//a[text()="小说目录"]/@href')
        print(readnode)
        pass
