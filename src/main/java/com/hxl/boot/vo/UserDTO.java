package com.hxl.boot.vo;

import com.hxl.boot.annotation.StaticEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    //用户id
    private Integer userId;
    //用户身份
    private String identity;
}
