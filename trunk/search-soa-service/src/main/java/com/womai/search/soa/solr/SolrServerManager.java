package com.womai.search.soa.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * solr服务管理层.
 * User: 赵立伟
 * Date: 2014/11/26
 * Time: 15:59
 */
public class SolrServerManager {

    private static SolrServer server;

    private String solrServer;

    public void setSolrServer(String solrServer) {
        this.solrServer = solrServer;
    }

    public void init() {
        if (server == null) {
            server = new HttpSolrServer(solrServer);
        }
    }

    public void destroy() {
        server = null;
    }

    public static SolrServer getServer() {
        return server;
    }
}
