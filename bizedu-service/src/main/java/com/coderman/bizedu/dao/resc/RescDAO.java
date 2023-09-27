package com.coderman.bizedu.dao.resc;

import com.coderman.bizedu.model.resc.RescExample;
import com.coderman.bizedu.model.resc.RescModel;
import com.coderman.bizedu.vo.resc.RescVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface RescDAO extends BaseDAO<RescModel, RescExample> {

    /**
     * 根据url查询资源信息
     * @param rescUrl 资源url
     * @return
     */
    RescVO selectByRescUrl(@Param(value = "rescUrl") String rescUrl);

    /**
     * 用户列表
     * @param conditionMap 查询条件
     * @return
     */
    List<RescVO> page(Map<String, Object> conditionMap);

    /**
     * 分页条数
     *
     * @param conditionMap
     * @return
     */
    Long countPage(Map<String, Object> conditionMap);


    /**
     * 根据关键字搜索资源
     *
     * @param keyword
     * @return
     */
    List<RescVO> selectByKeyword(@Param(value = "keyword") String keyword);

    /**
     * 获取用户资源列表
     *
     * @param username
     * @return
     */
    List<RescVO> selectRescListByUsername(@Param(value = "username") String username);

    /**
     * 新增系统资源
     *
     * @param rescModel
     * @return
     */
    int insertReturnKey(RescModel rescModel);
}