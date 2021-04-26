package com.sealll.util;

import java.util.List;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/4/19 15:58
 */
public interface LL1Set {
    public Set<String> getFirst(String root);
    public Set<String> getFirst(List<String> root);
    public Set<String> getFollow(String root);
    public Integer getPredict(String root,String token);
}
