package com.miyou.utils;

public class PathUtils {



    public String getJavaPath(){
        return this.getClass().getResource("../").getPath();
    }
    public String getRootPath(){
        return this.getClass().getResource("").getPath();
    }
    public String getPythonPath(){
        return this.getClass().getResource("../../../python").getPath();
    }

    public static void main(String[] args) {
        PathUtils pathUtils = new PathUtils();
        System.out.println(pathUtils.getPythonPath());
    }
}
