package com.sealll.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sealll
 * @time 2021/4/19 14:03
 */
public class Info {
    private boolean isSucceed;
    /**
     * 匹配到末尾的后一位
     */
    private int end;
    /**
     * 匹配的链索引
     */
    private int index;

    private List<Info> infos = new ArrayList<>();

    private boolean isEmp;

    public Info(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    public boolean isEmp() {
        return isEmp;
    }

    public void setEmp(boolean emp) {
        isEmp = emp;
    }

    public Info(boolean isSucceed, int end, int index, List<Info> infos) {
        this.isSucceed = isSucceed;
        this.end = end;
        this.index = index;
        this.infos = infos;
    }
    public Info(boolean isSucceed, int end, int index) {
        this.isSucceed = isSucceed;
        this.end = end;
        this.index = index;
    }

    public Info(boolean isSucceed, int end, int index,boolean isEmp){
        this.isEmp = isEmp;
        this.isSucceed = isSucceed;
        this.end = end;
        this.index = index;

    }

    public Info() {
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }
}
