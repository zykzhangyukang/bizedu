package com.coderman.admin.service.resc;


import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.resc.RescPageDTO;
import com.coderman.admin.dto.resc.RescSaveDTO;
import com.coderman.admin.dto.resc.RescUpdateDTO;
import com.coderman.admin.vo.resc.RescVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author coderman
 * @Title: 资源服务
 * @Description: TOD
 * @date 2022/3/199:04
 */
public interface RescService {

    /**
     * 资源列表
     * @param rescPageDTO 查询参数
     * @return
     */
    ResultVO<PageVO<List<RescVO>>> page(RescPageDTO rescPageDTO);

    /**
     * 保存资源
     *
     * @param rescSaveDTO
     * @return
     */
    ResultVO<Void> save(RescSaveDTO rescSaveDTO);


    /**
     * 更新资源
     * @param rescUpdateDTO
     * @return
     */
    ResultVO<Void> update(RescUpdateDTO rescUpdateDTO);


    /**
     * 删除资源
     * @param rescId
     * @return
     */
    ResultVO<Void> delete(Integer rescId);


    /**
     * 获取资源
     *
     * @param rescId
     * @return
     */
    ResultVO<RescVO> selectById(Integer rescId);


    /**
     * 根据关键词搜索资源
     *
     * @param keyword
     * @return
     */
    ResultVO<List<RescVO>> search(String keyword);


    /**
     * 根据用户名获取用资源信息
     *
     * @param username
     * @return
     */
    List<RescVO> selectRescListByUsername(String username);


    /**
     * 获取所有资源
     * @param project 所属项目
     * @return
     */
    ResultVO<Map<String, Set<Integer>>> getSystemAllRescMap(String project);

    /**
     * 刷新系统资源
     *
     * @return
     */
    ResultVO<Void> refresh();

    /**
     * 列表导出
     * @param rescPageDTO
     */
    void export(RescPageDTO rescPageDTO);
}
