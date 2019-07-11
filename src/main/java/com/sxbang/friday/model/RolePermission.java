package com.sxbang.friday.model;

import lombok.Data;

import java.io.Serializable;
/**
 * @author kaneki
 */
@Data
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 2972590235606370682L;

    private Integer roleId;
    private Integer permissionId;
}
