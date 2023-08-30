package icu.xiamu.service;

import icu.spring.BeanPostProcessor;
import icu.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class XiaMuBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // if ("userService".equals(beanName)) {
        //     System.out.println("初始化之前");
        //     ((UserServiceImpl) bean).setName("黄磊好帅");
        // }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化之后");
        if ("userService".equals(beanName)) {
            Object proxyInstance = Proxy.newProxyInstance(XiaMuBeanPostProcessor.class.getClassLoader(),
                    bean.getClass().getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("代理逻辑");
                            return method.invoke(bean, args);
                        }
                    });
            return proxyInstance;
        }
        return bean;
    }
}
