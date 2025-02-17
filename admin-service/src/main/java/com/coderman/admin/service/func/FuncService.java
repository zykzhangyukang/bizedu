package com.coderman.admin.service.func;


import com.coderman.admin.dto.func.FuncPageDTO;
import com.coderman.admin.dto.func.FuncRescUpdateDTO;
import com.coderman.admin.dto.func.FuncSaveDTO;
import com.coderman.admin.dto.func.FuncUpdateDTO;
import com.coderman.admin.model.func.FuncModel;
import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.admin.vo.func.FuncVO;
import com.coderman.admin.vo.func.MenuVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author coderman
 * @Title: 功能服务
 * @Description: TOD
 * @date 2022/3/1915:38
 */
public interface FuncService {

    /**
     * 获取功能树
     *
     * @return
     */
    List<FuncTreeVO> selectAllFuncTree();



    /**
     * 功能列表
     * @param funcPageDTO 查询参数
     * @return
     */
    ResultVO<PageVO<List<FuncVO>>> page(FuncPageDTO funcPageDTO);


    /**
     * 保存功能
     * @param funcSaveDTO 参数
     * @return
     */
    ResultVO<Void> save(FuncSaveDTO funcSaveDTO);


    /**
     * 更新功能
     * @param funcUpdateDTO
     * @return
     */
    ResultVO<Void> update(FuncUpdateDTO funcUpdateDTO);


    /**
     * 删除功能
     * @param funcId
     * @return
     */
    ResultVO<Void> delete(Integer funcId);


    /**
     * 获取功能
     * @param funcId
     * @return
     */
    ResultVO<FuncVO> selectById(Integer funcId);

    /**
     * 功能解绑用户
     *
     * @param funcId
     * @return
     */
    ResultVO<Void> deleteUserBind(Integer funcId);

    /**
     * 功能解绑资源
     *
     * @param funcId
     * @return
     */
    ResultVO<Void> funcRescRemove(Integer funcId);

    /**
     * 功能绑定资源
     *
     * @param funcRescUpdateDTO
     * @return
     */
    ResultVO<Void> updateFuncResc(FuncRescUpdateDTO funcRescUpdateDTO);


    /**
     * 根据角色id查询功能列表
     *
     * @param roleId
     * @return
     */
    List<FuncModel> selectByRoleId(Integer roleId);

    /**
     * 根据功能id计划查询功能
     *
     * @param funcIdList
     * @return
     */
    List<FuncModel> selectAllByFuncIdList(Collection<Integer> funcIdList);

    /**
     * 获取用户菜单 (tree)
     *
     * @param menuVOList
     * @return
     */
    List<MenuVO> selectUserMenusTree(List<MenuVO> menuVOList);

    /**
     * 获取用户菜单 （扁平化）
     * @param userId
     * @return
     */
    List<MenuVO> selectUserAllMenus(Integer userId);
    /**
     * 获取用户按钮
     *
     * @param userId
     * @return
     */
    List<String> selectUserButtons(Integer userId);

    /**
     * 列表导出
     * @param funcPageDTO
     */
    void export(FuncPageDTO funcPageDTO);
}
