package com.sealll.util;

import com.sealll.bean.Info;
import com.sealll.bean.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author sealll
 * @time 2021/4/19 14:34
 */
@Component
public class InfoTreeUtils {
    @Autowired
    private Map<String, List<List<String>>> map;

    public void drawTree(List<Token> tokens,Info info, String root, int lay){
        boolean flag = true;
        for (int i = 0; i < lay; i++) {
            System.out.print("\t");
        }
        System.out.println(root);
        if(info.getInfos() == null || info.getInfos().size() == 0){
            return;
        }
        for (Info infoInfo : info.getInfos()) {
            flag = flag && (infoInfo.getInfos() == null || infoInfo.getInfos().size() == 0);
        }
        if(flag){
            for (int i = 0; i < lay + 1; i++) {
                System.out.print("\t");
            }
//            System.out.print("\t");
            for (Info infoInfo : info.getInfos()) {
                if(infoInfo.isEmp()){
                    System.out.print("E ");
                }else{
                    System.out.print(tokens.get(infoInfo.getEnd() - 1).getValue() + " ");
                }
            }
            System.out.println();
        }else{
            int index = info.getIndex();
            List<String> strings = map.get(root).get(index);
            for (int i = 0;i < info.getInfos().size();i++) {
                drawTree(tokens,info.getInfos().get(i),strings.get(i),lay + 1);
            }
        }

    }
}
