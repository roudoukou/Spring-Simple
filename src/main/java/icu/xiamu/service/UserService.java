package icu.xiamu.service;

import icu.spring.Autowired;
import icu.spring.Component;
import icu.spring.Scope;

@Component("userService")
@Scope("prototype")
public class UserService {
    @Autowired
    private OrderService orderService;

    public void testOrder() {
        System.out.println(orderService);
    }
}
