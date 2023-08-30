package icu.xiamu;

import icu.spring.XiaMuApplicationContext;
import icu.xiamu.AppConfig;

public class Test {
    public static void main(String[] args) {


        XiaMuApplicationContext xiaMuApplicationContext = new XiaMuApplicationContext(AppConfig.class);

        Object bean1 = xiaMuApplicationContext.getBean("userService");
        Object bean2 = xiaMuApplicationContext.getBean("userService");
        Object bean3 = xiaMuApplicationContext.getBean("userService");
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        System.out.println("bean3 = " + bean3);
    }
}
