package com.sxbang.friday;

import com.sxbang.friday.dao.UserDao;
import com.sxbang.friday.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FridayApplicationTests {
    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads() {
        SysUser sysUser =new SysUser();
        sysUser.setId(41);
        sysUser.setPassword(new BCryptPasswordEncoder().encode("123456"));
        userDao.updateUser(sysUser);
    }

}
