package com.miyou.utils;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelGet {

    public static void main(String[] args) {
        String baseUrl = "https://www.wenku8.net/novel/1/1787/";
        String nextUrl = "61149.htm";
        String destFilePath = "D:\\test.txt";
        System.out.println("开始爬取数据...");
        long startTime = System.currentTimeMillis();
        getNovel(baseUrl, nextUrl, destFilePath);
        long endTime = System.currentTimeMillis();
        System.out.println("用时 " + (endTime - startTime) / 1000 + "秒...");
    }

    public static void getNovel(String baseUrl, String nextUrl, String destFilePath) {
        try {
            File destFile = new File(destFilePath);
            // 目标文件存在则删除
            if (!destFile.exists()) {
                destFile.delete();
            }
            destFile.createNewFile();

            FileWriter fw = new FileWriter(destFile);
            String realUrl, resultContent;
            StringBuffer sb = new StringBuffer();
            BufferedReader br = null;
            // 正文正则匹配表达式
            Pattern contentPat = Pattern.compile("<div id=\"content\">(.+?)</div>");
            // 标题正则表达式
            Pattern titlePat = Pattern.compile("readtitle = \"(.+?)\"");
            // 下一章正则表达式
            Pattern nextPat = Pattern.compile("&rarr; <a href=\"(.+?)\">");
            Matcher matcher;
            // 下一张的url以 / 开头则是最新章节
            while (!nextUrl.startsWith("/")) {
                realUrl = baseUrl + nextUrl;
                // 获取目标url的response
                br = new BufferedReader(new InputStreamReader(new URL(realUrl).openStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                // 替换空格和换行符
                resultContent = sb.toString().replaceAll("&nbsp;", "").replaceAll("<br /><br />", "*****");
                // 换行
                fw.write("\r\n\r\n");
                // 匹配标题
                matcher = titlePat.matcher(resultContent);
                if (matcher.find()) {
                    fw.write(matcher.group(1));
                }
                fw.write("\r\n");
                // 匹配正文
                matcher = contentPat.matcher(resultContent);
                if (matcher.find()) {
                    fw.write(matcher.group(1));
                }
                // 匹配下一页url
                matcher = nextPat.matcher(resultContent);
                if (matcher.find()) {
                    nextUrl = matcher.group(1);
                }
                // 清空
                sb.delete(0, sb.length());
            }
            if (br != null) {
                br.close();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
