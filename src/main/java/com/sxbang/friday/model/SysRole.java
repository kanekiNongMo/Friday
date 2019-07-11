package com.sxbang.friday.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author kaneki
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1725760509366166568L;

    private String name;
    private String description;

    @Override
    public String toString() {
        return "SysRole{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
