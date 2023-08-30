package icu.xiamu.service;

import icu.spring.BeanPostProcessor;
import icu.spring.Component;

@Component
public class XiaMuBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if ("userService".equals(beanName)) {
            System.out.println("初始化之前");
            ((UserService) bean).setName("黄磊好帅");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化之后");
        return bean;
    }
}
