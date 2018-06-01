package com.zx.kit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropReader {

    public static Properties load(String fileName) {
        Properties properties = new Properties();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(PropReader.class.getClassLoader()
                    .getResourceAsStream(fileName), "UTF-8");
            properties.load(inputStreamReader);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
