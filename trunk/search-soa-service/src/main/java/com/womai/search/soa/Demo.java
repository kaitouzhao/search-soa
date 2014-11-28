package com.womai.search.soa;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 测试solr服务.
 * User: 赵立伟
 * Date: 2014/11/26
 * Time: 9:12
 */
public class Demo {
    private static ApplicationContext ctx;

    private String solrServer;
    private static SolrServer server;

    public void setSolrServer(String solrServer) {
        this.solrServer = solrServer;
    }

    private void init() {
        server = new HttpSolrServer(solrServer);
    }

    private void server() {
        init();
        fail(server);
    }

    private static final void fail(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "log4j.xml",
                        "applicationContext-dubbo.xml"
                }
        );
        Demo testDemo = ctx.getBean(Demo.class);
        testDemo.server();
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", 1);
        doc.addField("name", "Solr Input Document");
        doc.addField("manu", "this is SolrInputDocument content");
        try {
            UpdateResponse response = server.add(doc);
            fail(server.commit());
            fail(response);
            fail("query time：" + response.getQTime());
            fail("Elapsed Time：" + response.getElapsedTime());
            fail("status：" + response.getStatus());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
