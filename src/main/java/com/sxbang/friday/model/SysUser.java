package com.sxbang.friday.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * @author kaneki
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<Integer> {

    private static final long serialVersionUID = 2580469046937020015L;

    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    private Integer sex;
    private Integer status;

    @Override
    public String toString() {
        return "SysUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", status=" + status +
                '}';
    }

    public interface Status {
        int VALID =1;
        int LOCKED = 0;
    }
}
