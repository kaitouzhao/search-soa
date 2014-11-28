package com.womai.search.soa.solr.service.impl;

import com.womai.common.framework.domain.PageModel;
import com.womai.common.framework.domain.RemoteResult;
import com.womai.search.soa.api.service.QueryService;
import com.womai.search.soa.common.Constants;
import com.womai.search.soa.solr.SolrServerManager;
import com.womai.search.soa.solr.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询服务实现.
 * User: 赵立伟
 * Date: 2014/11/24
 * Time: 11:58
 */
public class QueryServiceImpl extends BaseService implements QueryService {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public RemoteResult<PageModel> queryByCondition(Map<String, String> condition, int pageNum, int pageSize) {
        if (pageNum < 0 || pageSize <= 0) {
            return fail(Constants.RETURN_CODE_ILLEGAL_PARAMS);
        }
        int thisPageNum = pageNum;
        int thisPageSize = pageSize;
        SolrQuery params = new SolrQuery();
        if (condition != null && !condition.isEmpty()) {
            for (Map.Entry<String, String> item : condition.entrySet()) {
                params.setQuery(item.getKey() + ':' + item.getValue());
            }
        }
        try {
            SolrServer server = SolrServerManager.getServer();
            QueryResponse response = server.query(params);
            if (response == null || !Constants.SOLR_SERVER_RESPONSE.equals(response.getStatus())) {
                return fail(Constants.RETURN_CODE_QUERY_FAILED);
            }
            long totalCount = response.getResults().getNumFound();
            long totalPageNum = totalCount / thisPageSize + (totalCount % thisPageSize > 0 ? 1 : 0);
            if (thisPageNum > totalPageNum) {
                thisPageNum = Long.valueOf(totalPageNum - 1).intValue();
            }
            int start = thisPageNum * thisPageSize;
            response = server.query(params.setStart(start).setRows(thisPageSize));
            if (response == null || !Constants.SOLR_SERVER_RESPONSE.equals(response.getStatus())) {
                return fail(Constants.RETURN_CODE_QUERY_FAILED);
            }
            SolrDocumentList solrDocuments = response.getResults();
            List datas = new ArrayList<Map<String, String>>();
            for (int i = 0; i < solrDocuments.size(); i++) {
                SolrDocument doc = solrDocuments.get(i);
                datas.add(doc);
            }
            PageModel<List> pageModel = new PageModel<List>(thisPageSize, thisPageNum, Long.valueOf(totalPageNum).intValue(), Long.valueOf(totalCount).intValue(), datas);
            return success(pageModel);
        } catch (SolrServerException e) {
            log.error("Query failed.", e);
            return fail(Constants.RETURN_CODE_UNKNOWN_EXCEPTION);
        }
    }

}
