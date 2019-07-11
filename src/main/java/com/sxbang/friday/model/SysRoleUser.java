package com.sxbang.friday.model;

import lombok.Data;

import java.io.Serializable;
/**
 * @author kaneki
 */
@Data
public class SysRoleUser implements Serializable {

    private static final long serialVersionUID = -5467135682676980926L;

    private Integer userId;
    private Integer roleId;
}
