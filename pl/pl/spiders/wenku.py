# -*- coding:utf-8 -*-
import scrapy
import uuid
from scrapy.http import Request

from pl.items import WenkuItem
from pl.items import BookItem


def endHtml(url):
    link = str(url)
    links = link.split("/")
    return int(links[len(links)-1].replace('.htm', ''))


def parse_read(response):
    content = response.xpath("//div[@id='content']")[0].extract()
    title = response.xpath("//div[@id='title']/text()")[0].extract()
    item = response.meta['item']
    content1 = content.replace('<div id="content">', '')
    content2 = content1.replace('</div>', '')
    content3 = content2.replace('<br>', '')
    content4 = content3.replace('<ul id="contentdp">本文来自 轻小说文库(http://www.wenku8.com)</ul>', '')
    content5 = content4.replace('<ul id="contentdp">最新最全的日本动漫轻小说 轻小说文库(http://www.wenku8.com) 为你一网打尽！</ul>', '')
    item['text'] = content5.decode('UTF-8', 'ignore')
    item['name'] = title
    item['id'] = uuid.uuid1()
    item['sort'] = endHtml(response.url)
    yield item


class WenkuSpider(scrapy.spiders.Spider):
    name = "wenku"
    allowed_domains = ['wenku8.net']
    start_urls = [
        'https://www.wenku8.net/novel/1/1787/index.htm',
    ]

    def parse(self, response):
        titles = response.xpath('//table[@class="css"]/tr')
        name = response.xpath("//div[@id='title']/text()")[0].extract()
        bookitem = BookItem()
        bookitem['name'] = name
        bookid = uuid.uuid1()
        bookitem['id'] = bookid
        yield bookitem

        item = WenkuItem()
        for trs in titles:
            links = trs.xpath('//td[@class="ccss"]/a/@href').extract()
            for link in links:
                item['bookid'] = bookid
                yield Request('https://www.wenku8.net/novel/1/1787/' + link, callback=parse_read, meta={'item': item})
