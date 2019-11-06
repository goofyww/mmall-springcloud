package com.oecoo.gateway;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiGatewayApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        int b = 0b0000_0000_0000_0000_0000_0000_0011_1101;
        System.out.printf("%x", b);

        List list = Lists.newArrayList();

//		new String();
//		new StringBuilder();
//		new StringBuffer();
//
//		System.out.println(Double.isNaN(12.2));
    }

}
