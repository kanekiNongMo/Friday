package com.sxbang.friday.dto;

import com.sxbang.friday.model.SysRole;
import lombok.Data;

import java.util.List;
/**
 * @author kaneki
 */
@Data
public class RoleDto extends SysRole {
    private static final long serialVersionUID = 2395630044418684315L;
    private List<Integer> permissionIds;
}
