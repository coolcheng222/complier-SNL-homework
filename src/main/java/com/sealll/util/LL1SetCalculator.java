package com.sealll.util;

import com.sealll.Constants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sealll
 * @time 2021/4/19 15:24
 */
public class LL1SetCalculator implements LL1Set{
    private Map<String,List<List<String>>> map;

    private Map<String,Set<String>> first;

    private Map<String,Set<String>> follow;

    public Set<String> getFirst(String root){
        Set<String> strings = first.get(root);
        if(strings == null){
            first_$(first,root);
        }
        return first.get(root);
    }

    @Override
    public Set<String> getFirst(List<String> root) {
        Set<String> res = new HashSet<>();
        for (String s : root) {
            res.addAll(firstResolve(s));
            if(!hasEmpty(s)){
                break;
            }
        }
        return res;
    }

    @Override
    public Set<String> getFollow(String root) {
        return followResolve(root);
    }

    @Override
    public Integer getPredict(String root, String token) {
        if(!map.containsKey(root)){
            if(root.equals(token)){
                return 0;
            }else{
                return -1;
            }
        }else{
            List<List<String>> lists = map.get(root);
            for (int i = 0; i < lists.size();i++) {
                List<String> list = lists.get(i);
                Set<String> first = getFirst(list);
                if(first.contains("X")){
                    Set<String> follow = getFollow(root);
                    if(follow.contains(token)){
                        return i;
                    }
                }else{
                    if(first.contains(token)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public void buildFirst(){
        HashMap<String, Set<String>> map1 = new HashMap<>();
        for (String s : map.keySet()) {
            if(!map1.containsKey(s)){
//                System.out.println(s);
                first_$(map1,s);

            }
        }
        first = map1;
//        System.out.println(first);
    }

    public void buildFollow(){
        follow = new HashMap<>();
        for (String s : map.keySet()) {
            follow.put(s,new HashSet<>());
        }
        follow.get(Constants.START).add("$");
        for (String s : map.keySet()) {
            follow_$(s);
        }
//        System.out.println(first);
    }

    private List<String> stack = new ArrayList<>();
    private void first_$(Map<String,Set<String>> firsting,String root){
//        System.out.println(root);
        stack.add(root);
        if(!map.containsKey(root)){
            Set<String> set = new HashSet<>();
            set.add(root);
            firsting.put(root,set);
        }else{
            Set<String> res = new HashSet<>();
            if(hasEmpty(root)){
                res.add("X");
            }
            List<List<String>> lists = map.get(root);
            for (List<String> list : lists) {
                for (String s : list) {
//                    if("ProcDecpart".equals(root)){
//                        System.out.println(firsting);
//                    }
                    if(!stack.contains(s)){

                        first_$(firsting,s);
                        Set<String> strings = firsting.get(s);
                        for (String string : strings) {
                            if(!string.startsWith("$_")){

                                res.add(string);
                            }
                        }

                        if(!hasEmpty(s)){
                            break;
                        }
                    }else{
                        res.add("$_" + s);
                    }

                }
            }
            firsting.put(root,res);
        }
        stack.remove(root);
    }
    private void follow_$(String root){
        List<List<String>> lists = map.get(root);

        for (List<String> list : lists) {
            for (int i = 0;i < list.size();i++) {
                String s = list.get(i);
                if(map.containsKey(s)){
                    if(i == list.size() - 1){
                        follow.get(s).add("$_" + root);
                    }else{
                        List<String> strings = list.subList(i + 1, list.size());
                        Set<String> first = getFirst(strings);
                        follow.get(s).addAll(first);
                        if(first.contains("X")){
                            follow.get(s).remove("X");
                            follow.get(s).add("$_" + root);
                        }
                    }
                }
            }
        }

    }
    private List<String> stack2 = new ArrayList<>();
    private Set<String> followResolve(String root){
        stack2.add(root);
        Set<String> strings = follow.get(root);
        Set<String> res = new HashSet<>(strings);
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if(next.startsWith("$_")){
                if(!stack2.contains(next.substring(2))){
                    res.remove(next);
                    String s = next.substring(2);
                    Set<String> strings1 = followResolve(s);
                    res.addAll(strings1);
                }
            }
        }
        follow.put(root, res);
        stack2.remove(root);
        return res;
    }
    private Set<String> firstResolve(String root){
        if(!map.containsKey(root)){
            HashSet<String> objects = new HashSet<>();
            objects.add(root);
            return objects;
        }
        Set<String> strings = first.get(root);
        Set<String> res = new HashSet<>(strings);
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if(next.startsWith("$_")){
                res.remove(next);
                String s = next.substring(2);
                Set<String> strings1 = firstResolve(s);
                res.addAll(strings1);
            }
        }
        first.put(root, res);
        return res;
    }
    private boolean hasEmpty(String root){
        List<List<String>> lists = map.get(root);
        if(lists == null){
//            System.out.println("出大问题");
            return false;
        }
        for (List<String> list : lists) {
            if("X".equals(list.get(0))){
                return true;
            }
        }
        return false;
    }

    public void setMap(Map<String, List<List<String>>> map) {
        this.map = map;
    }

}
