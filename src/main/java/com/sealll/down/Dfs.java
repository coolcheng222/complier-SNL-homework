package com.sealll.down;

import com.sealll.Constants;
import com.sealll.bean.Info;
import com.sealll.bean.Token;
import com.sealll.file.ContextBuilder;
import com.sealll.util.InfoTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Characters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/3/29 15:57
 */
@Service
public class Dfs {

    @Autowired
    private Map<String, List<List<String>>> map;

    @Autowired
    private InfoTreeUtils treeUtils;


    public void buildTree(List<Token> tokens){
        Info dfs = dfs(tokens, 0, Constants.START);
        if(dfs.isSucceed()){
            List<Info> infos = dfs.getInfos();
            boolean flag = false;
            for (Info info : infos) {
                int end = info.getEnd();
                if(end == tokens.size() + 1){
                    flag = true;
                }
            }
            if(flag){

                treeUtils.drawTree(tokens,dfs,Constants.START,0);
            }else{
                throw new RuntimeException("语法错误.");
            }
        }else{
            throw new RuntimeException("语法错误");
        }
    }

    /**
     * 递归下降
     * @param tokens
     * @param start
     * @param root
     * @return
     */
    public Info dfs(List<Token> tokens, int start, String root){
//        System.out.println(root);
        if("X".equalsIgnoreCase(root)){
            return new Info(true,start,0,true);
        }
        //判断下一个文法符号是否为终结符
        if(!map.keySet().contains(root)){
            //是终结符,进行匹配
            Token token = tokens.get(start);
            if(root.equalsIgnoreCase(token.getKey())){
                //匹配成功
                return new Info(true,start + 1,0);
            }else{
                //匹配失败
                return new Info(false);
            }
        }else{
            //非终结符
            List<List<String>> lists = map.get(root);
            if(lists == null){
                throw new RuntimeException("找不到该非终结符");
            }
            List<Info> infos = new ArrayList<>();
            for (int i = 0;i < lists.size();i++) {
                //每种"或"分隔的多个文法
                List<String> list = lists.get(i);

                boolean flag = true;
                int startTmp = start;

                for (String s : list) {
                    //文法中的各个终结符/非终结符
                    Info dfs = dfs(tokens, startTmp, s);
                    if(dfs.isSucceed()){
                        infos.add(dfs);
                        startTmp = dfs.getEnd();
                    }else{
                        infos.clear();
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    return new Info(true,startTmp,i,infos);
                }
            }
            //匹配不到,返回失败

            return new Info(false);
        }
    }
}
