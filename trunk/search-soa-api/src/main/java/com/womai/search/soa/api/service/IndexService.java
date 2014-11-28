package com.womai.search.soa.api.service;

import com.womai.common.framework.domain.RemoteResult;

import java.util.List;
import java.util.Map;

/**
 * 索引服务.
 * User: 赵立伟
 * Date: 2014/11/24
 * Time: 11:56
 */
public interface IndexService {

    /**
     * 添加索引
     *
     * @param paramsList 索引集合
     * @return 建立索引条数
     */
    public RemoteResult<Integer> addIndex(List<Map<String, String>> paramsList);

    /**
     * 删除索引
     *
     * @param ids 索引的主键
     * @return 删除条数
     */
    public RemoteResult<Integer> removeIndex(List<String> ids);
}
