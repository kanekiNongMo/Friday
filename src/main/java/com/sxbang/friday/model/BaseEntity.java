package com.sxbang.friday.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author kaneki
 */
@Data
public class BaseEntity<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = -3661354527521884108L;
    private ID id;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime = new Date();

    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime = new Date();

}
