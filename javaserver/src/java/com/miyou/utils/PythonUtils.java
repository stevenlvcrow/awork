package com.miyou.utils;

import lombok.extern.java.Log;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Log
@Component
public class PythonUtils {

    @Value("${python.home}")     private String pythonHome;

    public void invokPython(String pyName) throws FileNotFoundException {


        PythonInterpreter interp = new PythonInterpreter();

        PathUtils pathUtils = new PathUtils();
        interp.exec("import sys");
        interp.exec("sys.path.append('"+pythonHome+"')");//jython自己的
        interp.exec("print(sys.path)");

        InputStream is = new FileInputStream(new File(pathUtils.getPythonPath()+"/pl/"+pyName));
        interp.execfile(is);
        PyFunction pyFunction = interp.get("main",PyFunction.class);

//        PyClass pyClass = interp.get("BookSpider",PyClass.class);
        PyObject dddRes = pyFunction.__call__();
        System.out.println(dddRes);
        interp.cleanup();
        interp.close();
    }




    public static void main(String[] args) throws IOException, InterruptedException {


    }
}
