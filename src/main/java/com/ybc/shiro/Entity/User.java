package com.ybc.shiro.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Date date;
    private Integer status;
}
