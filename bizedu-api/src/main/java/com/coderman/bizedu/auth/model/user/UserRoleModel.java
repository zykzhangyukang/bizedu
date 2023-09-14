package com.coderman.bizedu.auth.model.user;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the base record class for table: auth_user_role
 * Generated by MyBatis Generator.
 * @author MyBatis Generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="UserRoleModel", description = "auth_user_role 实体类")
public class UserRoleModel extends BaseModel {
    

    @ApiModelProperty(value = "组件")
    private Integer userRoleId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}