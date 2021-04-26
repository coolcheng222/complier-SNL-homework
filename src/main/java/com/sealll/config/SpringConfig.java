package com.sealll.config;

import com.google.gson.Gson;
import com.sealll.file.ContextBuilder;
import com.sealll.util.LL1SetCalculator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author sealll
 * @time 2021/4/19 13:56
 */
@Configuration
@ComponentScan("com.sealll")
public class SpringConfig {
    @Bean
    @DependsOn("contextBuilder")
    public Map<String, List<List<String>>> contextMap(ContextBuilder builder) throws IOException {
        Map<String, List<List<String>>> map = builder.getMap();
        return map;
    }
    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean
    @DependsOn("contextMap")
    public LL1SetCalculator calculator(@Qualifier("contextMap") Map<String, List<List<String>>> map){
        LL1SetCalculator ll1SetCalculator = new LL1SetCalculator();
        ll1SetCalculator.setMap(map);
        ll1SetCalculator.buildFirst();
        ll1SetCalculator.buildFollow();
        return ll1SetCalculator;
    }
}
