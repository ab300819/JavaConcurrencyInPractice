package com.exercise.demo.common;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by lenovo on 2017/8/22.
 */
public class HttpHelper {

    public static String getBodyString(ServletRequest request) {
        String str = null;
        try {
            InputStream inputStream = request.getInputStream();
            str = IOUtils.toString(new InputStreamReader(inputStream, Charset.forName("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
