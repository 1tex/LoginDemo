package com.group4.logindemo;

import com.group4.logindemo.model.User;
import com.group4.logindemo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 使用主配置文件中的数据源
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");

        userRepository.save(user);

        User found = userRepository.findByUsername("testuser");
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }
}
