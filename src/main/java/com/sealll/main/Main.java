package com.sealll.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sealll.bean.Token;
import com.sealll.config.SpringConfig;
import com.sealll.down.Left1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author sealll
 * @time 2021/4/19 22:09
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext ioc = new AnnotationConfigApplicationContext(SpringConfig.class);
        InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("vert.json");
        byte[] bytes = resourceAsStream.readAllBytes();
        String s = new String(bytes);
        Gson gson = ioc.getBean(Gson.class);
        List<Token> o = gson.fromJson(s, new TypeToken<List<Token>>() {
        }.getType());
//        Dfs dfs = ioc.getBean(Dfs.class);
//        dfs.buildTree(o);
        Left1 bean = ioc.getBean(Left1.class);
        bean.buildTree(o);
    }
}
