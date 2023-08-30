package icu.xiamu.service;

import icu.spring.Autowired;
import icu.spring.BeanNameAware;
import icu.spring.Component;
import icu.spring.Scope;

@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware {
    @Autowired
    private OrderService orderService;

    private String beanName;

    public void testOrder() {
        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }
}
