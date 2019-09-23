package com.example.stressbuster;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.util.List;

public class InstagramScraperStuff {

    String userName;
    String Password;

    InstagramScraperStuff(String username, String password) {

        userName = username;
        Password = password;

    }

    public List<String> getStuffDone() {

        Python python = Python.getInstance();
        PyObject importingJsonModule = python.getModule("json");
        PyObject importingCodecs = python.getModule("codecs");
        PyObject importingDateTime = python.getModule("datetime");
        PyObject importingOsDotPath = python.getModule("os.path");
        PyObject importingLogging = python.getModule("logging");

        return null;

    }
}
