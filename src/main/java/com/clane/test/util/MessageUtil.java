package com.clane.test.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageUtil {

    private static Properties getProperties(String fileName){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = MessageUtil.class.getClassLoader().getResourceAsStream(fileName);
            if(input==null){
                System.out.println("Sorry, unable to find " + fileName);
                return null;
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }

    public static String msg(String key) {
        Properties property;
        String value;
        String lang = LocaleContextHolder.getLocale().getLanguage();
        property = MessageUtil.getProperties("messages_" + lang + ".properties");
        if(property != null){
            value = property.getProperty(key);
            if(value != null){
                return value;
            }else {
                property = MessageUtil.getProperties("messages.properties");
            }
        }else {
            property = MessageUtil.getProperties("messages.properties");
        }

        return !StringUtils.isEmpty(property) ? property.getProperty(key) : "Message not found";
    }
}