package com.miyou.utils;

import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class PythonUtils {





    public static void main(String[] args) {
        PathUtils pathUtils = new PathUtils();
        PythonInterpreter pi1 = new PythonInterpreter();
        pi1.execfile(pathUtils.getPythonPath()+"/add.py");
        PyFunction pyf = pi1.get("add", PyFunction.class);
        PyObject dddRes = pyf.__call__(Py.newInteger(2), Py.newInteger(3));
        System.out.println(dddRes);
        pi1.cleanup();
        pi1.close();
    }
}
