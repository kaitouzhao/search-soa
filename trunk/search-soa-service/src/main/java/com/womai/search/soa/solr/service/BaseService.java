package com.womai.search.soa.solr.service;

import com.womai.common.framework.domain.RemoteResult;
import com.womai.search.soa.common.Constants;

/**
 * 远程service父类.
 * User: 赵立伟
 * Date: 2014/11/28
 * Time: 9:58
 */
public abstract class BaseService {
    /**
     * 成功返回
     *
     * @param result 返回数据
     * @return 远程调用返回对象
     */
    protected final RemoteResult success(Object result) {
        RemoteResult remoteResult = new RemoteResult(true);
        remoteResult.setT(result);
        remoteResult.setResultCode(Constants.RETURN_CODE_SUCCESS);
        return remoteResult;
    }

    /**
     * 失败返回
     *
     * @param failCode 失败代码
     * @return 远程调用返回对象
     */
    protected final RemoteResult fail(String failCode) {
        RemoteResult remoteResult = new RemoteResult(false);
        remoteResult.setResultCode(failCode);
        return remoteResult;
    }
}
