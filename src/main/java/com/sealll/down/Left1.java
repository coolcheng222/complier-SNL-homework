package com.sealll.down;

import com.sealll.Constants;
import com.sealll.bean.Info;
import com.sealll.bean.Token;
import com.sealll.util.InfoTreeUtils;
import com.sealll.util.LL1SetCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sealll
 * @time 2021/4/19 14:55
 */
@Service
public class Left1 {
    @Autowired
    private LL1SetCalculator calculator;

    @Autowired
    private InfoTreeUtils treeUtils;

    @Autowired
    private Map<String, List<List<String>>> map;


    public void buildTree(List<Token> tokens) {
        ArrayList<Token> tokens1 = new ArrayList<>(tokens);
        tokens1.add(new Token("$", "$"));
        Info dfs = dfs(tokens1, 0, Constants.START);
        if (dfs.isSucceed()) {
            boolean flag = false;
            List<Info> infos = dfs.getInfos();
            for (Info info : infos) {
                int end = info.getEnd();
                if (end == tokens.size()) {
                    flag = true;
                }
            }
            if (flag) {

                treeUtils.drawTree(tokens, dfs, Constants.START, 0);
            } else {
                throw new RuntimeException("语法错误.");
            }
        } else {
            throw new RuntimeException("语法错误");
        }
    }

    private Info dfs(List<Token> tokens, int start, String root) {
        if ("X".equalsIgnoreCase(root)) {
            return new Info(true, start, 0, true);
        }
        //判断下一个文法符号是否为终结符
        if (!map.keySet().contains(root)) {
            //是终结符,进行匹配
            Token token = tokens.get(start);
            if (root.equalsIgnoreCase(token.getKey())) {
                //匹配成功
                return new Info(true, start + 1, 0);
            } else {
                //匹配失败
                return new Info(false);
            }
        } else {
            Token token = tokens.get(start);
            Integer index = calculator.getPredict(root, token.getKey());
            if (index < 0) {
                return new Info(false);
            }
            List<List<String>> lists = map.get(root);
            if (lists == null) {
                throw new RuntimeException("找不到该非终结符");
            }
            List<Info> infos = new ArrayList<>();
            List<String> list = lists.get(index);
            boolean flag = true;
            int startTmp = start;

            for (String s : list) {
                //文法中的各个终结符/非终结符
                Info dfs = dfs(tokens, startTmp, s);
                if (dfs.isSucceed()) {
                    infos.add(dfs);
                    startTmp = dfs.getEnd();
                } else {
                    infos.clear();
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return new Info(true, startTmp, index, infos);
            }
        }
        //匹配不到,返回失败

        return new Info(false);
    }

}
