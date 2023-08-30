package icu.xiamu.service;

import icu.spring.*;

@Component("userService")
@Scope("prototype")
public class UserServiceImpl implements BeanNameAware, InitializingBean, UserService {
    @Autowired
    private OrderService orderService;

    private String beanName;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void testOrder() {
        System.out.println(orderService);
        // System.out.println(beanName);
        System.out.println(name);
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("初始化");
    }
}
