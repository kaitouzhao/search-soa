package com.womai.search.soa.solr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * solr事务管理.
 * User: 赵立伟
 * Date: 2014/11/26
 * Time: 16:35
 */
public class SolrTransaction {

    private Log log = LogFactory.getLog(getClass());

    private SolrServerManager solrServerManager;

    public void setSolrServerManager(SolrServerManager solrServerManager) {
        this.solrServerManager = solrServerManager;
    }

    public void doBefore() {
        if (log.isDebugEnabled()) {
            log.debug("Solr Transaction start...");
        }
        if (SolrServerManager.getServer() == null) {
            if (log.isDebugEnabled()) {
                log.debug("Get Solr Server...");
            }
            solrServerManager.init();
        }
    }

    public void doAfter() {
        if (log.isDebugEnabled()) {
            log.debug("Solr Transaction end...");
        }
        solrServerManager.destroy();
    }
}
