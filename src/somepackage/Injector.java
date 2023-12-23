package somepackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

public class Injector {

    private Properties properties;

    public Injector() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T inject(T object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                String implementationName = properties.getProperty(fieldType.getName());
                if (implementationName == null) {
                    throw new IllegalArgumentException("Не найдена реализация для " + fieldType.getName());
                }
                Class<?> implClass = Class.forName(implementationName);
                Object implInstance = implClass.getDeclaredConstructor().newInstance();
                field.set(object, implInstance);
            }
        }
        return object;
    }
}