package com.neo;

import com.neo.mapper.UserMapper;
import com.neo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
@MapperScan("com.neo.mapper")
public class MybatisAnnotationApplication implements CommandLineRunner {


    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisAnnotationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> list = userMapper.getAll();
        log.info("{}", list);
    }
}
