package icu.xiamu;

import icu.spring.XiaMuApplicationContext;
import icu.xiamu.AppConfig;
import icu.xiamu.service.UserService;

public class Test {
    public static void main(String[] args) {


        XiaMuApplicationContext xiaMuApplicationContext = new XiaMuApplicationContext(AppConfig.class);
        UserService userService = (UserService) xiaMuApplicationContext.getBean("userService");
        userService.testOrder();
    }
}
