package com.coder520.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by JackWangon[www.coder520.com] 2017/8/1.
 *
 * 用于缓存的user信息体
 */
@Data
public class UserElement implements Serializable{

    private Long userId;

    private Long uuid;

    private String email;

    private  String nickname;

}
