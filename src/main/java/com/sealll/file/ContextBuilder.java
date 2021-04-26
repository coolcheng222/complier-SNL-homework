package com.sealll.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sealll
 * @time 2021/3/29 15:02
 */
@Service
public class ContextBuilder {
    public  Map<String,List<List<String>>> getMap() throws IOException {
        Map<String,List<List<String>>> map = new HashMap<>();
        InputStream is = ContextBuilder.class.getClassLoader().getResourceAsStream("by.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String s;
        while((s = reader.readLine()) != null) {
            if(!s.contains(":=")){
                continue;
            }
            s = s.substring(s.indexOf(")") + 1);

            String[] split = s.split("::=");
            map.put(split[0].trim(),getList(split[1]));
        }
        return map;

    }
    public Set<String> identList(){
        InputStream is = ContextBuilder.class.getClassLoader().getResourceAsStream("ident.json");
        InputStreamReader rea = new InputStreamReader(is);
        Set<String> o = new Gson().fromJson(rea, new TypeToken<Set<String>>() {
        }.getType());
        return o;
    }
    private List<List<String>> getList(String s){
        List<List<String>> res = new ArrayList<>();
            if(s.contains("|")){
                String[] split = s.split("\\|");
//                System.out.println(Arrays.asList(split));
                for (String s1 : split) {
                    String[] split1 = s1.split("\\s+");
                    Stream<String> stream = Arrays.stream(split1);
                    Stream<String> stringStream = stream.map(a -> a.trim());
                    stringStream = stringStream.filter(a -> a.length() > 0);
                    List<String> collect = stringStream.collect(Collectors.toList());
//                    System.out.println(collect);
                    res.add(collect);
                }
            }else{
                String[] split = s.split("\\s+");
                    Stream<String> stream = Arrays.stream(split);
                    Stream<String> stringStream = stream.filter(a -> !a.isEmpty());
                    List<String> collect = stringStream.collect(Collectors.toList());

//                    res.add(collect);
                List<String> strings = Arrays.asList(split);

                res.add(strings);
        }
        return res;
    }
}
