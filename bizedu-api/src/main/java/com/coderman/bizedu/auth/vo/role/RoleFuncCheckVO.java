package com.coderman.bizedu.auth.vo.role;

import com.coderman.api.model.BaseModel;
import com.coderman.bizedu.auth.model.func.FuncModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author coderman
 * @Title: 角色分配功能检查
 * @date 2022/5/2115:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleFuncCheckVO extends BaseModel {

    @ApiModelProperty(value = "本次新增")
    private List<FuncModel> insertList;

    @ApiModelProperty(value = "本次删除")
    private List<FuncModel> delList;
}
