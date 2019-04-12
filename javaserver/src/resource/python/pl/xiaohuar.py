import re
import requests
import os
import urllib


def get_index(url):
    print(url)
    respose = requests.get(url)
    if respose.status_code == 200:
        return respose.text


def parse_index(res):
    urls = re.findall(r'span class="title".*?href="(.*?)"', res, re.S)
    return urls


def get_detail(urls):
    for url in urls:
        url = 'https://acg.rip%s' % url
        try:
            result = requests.get(url)
            if result.status_code == 200:
                downUrl = re.findall(r'a class="btn btn-primary".*?href="(.*?)"', result.text, re.S)
                save(downUrl[0])
        except requests.exceptions.Timeout, e:
            print e
        except IOError, e:
            print e


def save(url):
    url = 'https://acg.rip%s' % url
    path = "D:\\acg"
    print(url)
    video = requests.get(url)
    if video.status_code == 200:
        if not os.path.exists(path):
            os.mkdir(path)
        filename = getFile(url)
        print(filename)
        filepath = r'D:\\acg\\%s' % filename
        with open(filepath, 'wb') as f:
            f.write(video.content)


def getFile(url):
    r = urllib.urlopen(url)
    if r.info().has_key('Content-Disposition'):
        fileName = r.info()['Content-Disposition'].split('filename=')[1]
        fileName = fileName.replace('"', '').replace("'", "")

    elif r.url != url:
        # if we were redirected, the real file name we take from the final URL
        from os.path import basename
        from urlparse import urlsplit
        fileName = basename(urlsplit(r.url)[2])
    else:
        fileName = os.path.basename(url)

    return fileName


def main():
    for i in range(1, 3):
        res1 = get_index('https://acg.rip/1/page/%s' % i)
        res2 = parse_index(res1)
        get_detail(res2)


if __name__ == '__main__':
    main()
