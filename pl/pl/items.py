# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class PlItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    pass


class WenkuItem(scrapy.Item):
    id = scrapy.Field()
    name = scrapy.Field()
    text = scrapy.Field()
    bookid = scrapy.Field()
    sort = scrapy.Field()


class BookItem(scrapy.Item):
    id = scrapy.Field()
    name = scrapy.Field()
