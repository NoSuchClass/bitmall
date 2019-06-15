package com.bitmall.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author liuyuehe
 * @date 2019/6/15 17:07
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties properties;

    static {
        String filename = "bitmall.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(filename), "UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常:" + e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key, String defaultKey) {
        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return getProperty(defaultKey);
        }
        return value.trim();
    }
}
