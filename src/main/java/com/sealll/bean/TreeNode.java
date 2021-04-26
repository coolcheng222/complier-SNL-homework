package com.sealll.bean;

import java.util.List;

/**
 * @author sealll
 * @time 2021/4/19 14:04
 */
public class TreeNode {
    private String val;
    private List<TreeNode> children;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val='" + val + '\'' +
                '}';
    }
}
