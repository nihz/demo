package com.nee.demo.edu.spring.servlet;

import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ViewResolver {

    private String viewName;
    private File templateFile;

    public ViewResolver(String viewName, File templateFile) {

        this.viewName = viewName;
        this.templateFile = templateFile;

    }

    private String viewResolver(ModelAndView mv) {

        StringBuffer sb = new StringBuffer();
        try {
            RandomAccessFile ra = new RandomAccessFile(this.templateFile, "r");
            String line = null;
            while (null != (line = ra.readLine())) {
                Matcher m = matcher(line);
                while (m.find()) {
                    for (int i = 0; i < m.groupCount(); i++) {
                        String paramName = m.group(i);
                        Object paramsValue = mv.getModel().get(paramName);
                        if (null == paramsValue) continue;
                        line = line.replaceAll("￥\\{}" + paramName + "\\}", paramsValue.toString());

                        sb.append(line);
                    }
                }

            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("￥\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }

}
