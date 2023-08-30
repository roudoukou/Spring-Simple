package icu.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class XiaMuApplicationContext {
    private Class configClass;

    private ConcurrentMap<String, Object> singletonObjects = new ConcurrentHashMap<>(); // 单例池
    private ConcurrentMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public XiaMuApplicationContext(Class configClass) {
        this.configClass = configClass;

        scan(configClass);
    }

    private void scan(Class configClass) {
        // 解析配置类
        // ComponentScan注解 => 扫描路径 => 扫描

        ComponentScan componentScanAnnotation
                = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();
        System.out.println(path);

        // 扫描
        // Bootstrap --> jre/lib
        // Ext --------> jre/ext/lib
        // App --------> classpath
        ClassLoader classLoader = XiaMuApplicationContext.class.getClassLoader(); // app
        path = path.replace(".", "/");
        URL resource = classLoader.getResource(path);
        String fileResource = resource.getFile();
        File file = new File(fileResource);
        System.out.println(file);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absolutePath = f.getAbsolutePath();
                // System.out.println(absolutePath);
                if (absolutePath.endsWith(".class")) {
                    String clasName
                            = absolutePath.substring(absolutePath.indexOf("icu"),
                            absolutePath.indexOf(".class"));
                    clasName = clasName.replace("\\", ".");
                    System.out.println(clasName);
                    try {
                        Class<?> clazz = classLoader.loadClass(clasName);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            // 表示当前这个类是一个Bean
                            // 解析类-->BeanDefinition
                            Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnnotation.value();

                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(clazz);

                            if (clazz.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            } else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                return beanDefinitionMap.get(beanName);
            } else {
                // 创建bean对象
                return createBean(beanDefinition);
            }
        } else {
            // 不存在对应的bean
            throw new NullPointerException("没有对应bean实例");
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getClazz();
        Object instance = null;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();

            // 依赖注入
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }
}
