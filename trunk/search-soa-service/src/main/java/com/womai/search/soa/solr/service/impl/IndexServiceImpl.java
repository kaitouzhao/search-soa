package com.womai.search.soa.solr.service.impl;

import com.womai.common.framework.domain.RemoteResult;
import com.womai.search.soa.api.service.IndexService;
import com.womai.search.soa.common.Constants;
import com.womai.search.soa.solr.SolrServerManager;
import com.womai.search.soa.solr.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 索引服务实现.
 * User: 赵立伟
 * Date: 2014/11/24
 * Time: 11:58
 */
public class IndexServiceImpl extends BaseService implements IndexService {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public RemoteResult<Integer> addIndex(List<Map<String, String>> paramsList) {
        if (paramsList == null || paramsList.isEmpty()) {
            return fail(Constants.RETURN_CODE_ILLEGAL_PARAMS);
        }
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        SolrInputDocument doc;
        int successCount = 0;
        for (Map<String, String> params : paramsList) {
            if (!params.containsKey("id")) {
                continue;
            }
            doc = new SolrInputDocument();
            for (Map.Entry<String, String> item : params.entrySet()) {
                doc.addField(item.getKey(), item.getValue());
            }
            docs.add(doc);
            successCount++;
        }
        if (docs.isEmpty()) {
            return fail(Constants.RETURN_CODE_ADD_INDEX_FAILED);
        }
        try {
            SolrServer server = SolrServerManager.getServer();
            server.add(docs);
            UpdateResponse response = server.commit(true, true);
            if (response == null || !Constants.SOLR_SERVER_RESPONSE.equals(response.getStatus())) {
                return fail(Constants.RETURN_CODE_ADD_INDEX_FAILED);
            }
        } catch (SolrServerException e) {
            log.error("Add solr index failed.", e);
            return fail(Constants.RETURN_CODE_UNKNOWN_EXCEPTION);
        } catch (IOException e) {
            log.error("Add solr index failed.", e);
            return fail(Constants.RETURN_CODE_UNKNOWN_EXCEPTION);
        }
        return success(successCount);
    }

    @Override
    public RemoteResult<Integer> removeIndex(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return fail(Constants.RETURN_CODE_ILLEGAL_PARAMS);
        }
        try {
            SolrServer server = SolrServerManager.getServer();
            server.deleteById(ids);
            UpdateResponse response = server.commit(true, true);
            if (response == null || !Constants.SOLR_SERVER_RESPONSE.equals(response.getStatus())) {
                return fail(Constants.RETURN_CODE_ADD_INDEX_FAILED);
            }
        } catch (SolrServerException e) {
            log.error("Remove solr index failed.", e);
            return fail(Constants.RETURN_CODE_UNKNOWN_EXCEPTION);
        } catch (IOException e) {
            log.error("Remove solr index failed.", e);
            return fail(Constants.RETURN_CODE_UNKNOWN_EXCEPTION);
        }
        return success(ids.size());
    }
}
