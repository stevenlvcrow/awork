package com.miyou.utils;

import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.util.Properties;

public class PythonUtils {






    public static void main(String[] args) throws IOException, InterruptedException {
        Properties props = new Properties();
        props.put("python.home", "path to the Lib folder");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);

        PythonInterpreter interp = new PythonInterpreter();

        PathUtils pathUtils = new PathUtils();
        interp.exec("import sys");
        interp.exec("sys.path.append('C:/Python27/Lib/site-packages')");//jython自己的
        interp.exec("print(sys.path)");

        InputStream is = new FileInputStream(new File("E:/awork/javaserver/src/resource/python/pl/xiaohuar.py"));
        interp.execfile(is);
        PyFunction pyFunction = interp.get("main",PyFunction.class);

//        PyClass pyClass = interp.get("BookSpider",PyClass.class);
        PyObject dddRes = pyFunction.__call__();
        System.out.println(dddRes);
        interp.cleanup();

        interp.close();

    }
}
