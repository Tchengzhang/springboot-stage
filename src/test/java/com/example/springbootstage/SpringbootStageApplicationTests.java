package com.example.springbootstage;

import org.apache.coyote.http2.ByteUtil;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringbootStageApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test(){
//        String password = "123456";
//        String name = "admin";
//        String salt = "0502833dc6fa41a2ab3875f74121b026";
//        ByteSource byteSource = ByteSource.Util.bytes(name + salt);
//        Object obj = new SimpleHash("MD5", password, byteSource, 2);
//        System.out.println("===================================="+obj.toString());
        System.out.println("++++++++++++++++++++++++++++++"+System.getProperty("java.io.tmpdir"));
    }









}
