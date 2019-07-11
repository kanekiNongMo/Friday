package com.sxbang.friday.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxbang.friday.model.SysPermission;
import org.w3c.dom.ls.LSException;

import java.util.List;

public class TreeUtils {

    /**
     * 菜单树
     *
     * @param parentId
     * @param permissionsAll
     * @param array
     */
    public static void setPermissionsTree(Integer parentId, List<SysPermission> permissionsAll, JSONArray array) {
        for (SysPermission per : permissionsAll) {
            if (per.getParentId().equals(parentId)) {
                String string = JSONObject.toJSONString(per);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);
                if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(per.getId(), permissionsAll, child);
                }
            }
        }
    }

    /**
     * 构建的菜单树不包括自己和子节点，主要用于菜单管理时，修改菜单的操作
     *
     * @param parentId
     * @param id
     * @param permissionsAll
     * @param array
     */
    public static void setPermissionsTreeNoSonById(Integer parentId, Integer id, List<SysPermission> permissionsAll, JSONArray array) {
        for (SysPermission permission : permissionsAll) {
            if (permission.getParentId().equals(parentId) && !permission.getParentId().equals(id) && !permission.getId().equals(id)) {
                String string = JSONObject.toJSONString(permission);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);
                if (permissionsAll.stream().filter(p -> p.getParentId().equals(permission.getId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTreeNoSonById(permission.getId(), id, permissionsAll, child);
                }
            }
        }
    }
}
