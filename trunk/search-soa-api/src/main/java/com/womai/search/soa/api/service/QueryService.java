package com.womai.search.soa.api.service;

import com.womai.common.framework.domain.PageModel;
import com.womai.common.framework.domain.RemoteResult;

import java.util.Map;

/**
 * 查询服务.
 * User: 赵立伟
 * Date: 2014/11/24
 * Time: 11:57
 */
public interface QueryService {
    /**
     * 根据条件查询
     *
     * @param condition 查询条件集合
     * @param pageNum   当前页码
     * @param pageSize  每页条数
     * @return 查询结果
     */
    public RemoteResult<PageModel> queryByCondition(Map<String, String> condition, int pageNum, int pageSize);
}
