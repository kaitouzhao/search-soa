package com.womai.search.soa;

import com.womai.common.framework.domain.PageModel;
import com.womai.common.framework.domain.RemoteResult;
import com.womai.search.soa.api.service.IndexService;
import com.womai.search.soa.api.service.QueryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试soa.
 * User: 赵立伟
 * Date: 2014/11/26
 * Time: 16:52
 */
public class Demo1 {

    private static ApplicationContext ctx;
    private static IndexService indexService;
    private static QueryService queryService;

    public static void main(String[] args) {
        init();
//        testAddIndex();
        testQuery();
//        testRemoveIndex();
    }

    private static void init() {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "log4j.xml",
                        "applicationContext-aop.xml",
                        "applicationContext-dubbo.xml"
                }
        );
        indexService = (IndexService) ctx.getBean("indexService");
        queryService = (QueryService) ctx.getBean("queryService");
    }

    private static void testAddIndex() {
        List<Map<String, String>> paramsList = new ArrayList<Map<String, String>>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "2");
        params.put("name", "Solr Input Documents 2");
        params.put("manu", "this is SolrInputDocuments 2 content");
        paramsList.add(params);
        params = new HashMap<String, String>();
        params.put("id", "3");
        params.put("name", "Solr Input Documents 3");
        params.put("manu", "this is SolrInputDocuments 3 content");
        paramsList.add(params);
        RemoteResult<Integer> remoteResult = indexService.addIndex(paramsList);
        System.out.println(remoteResult.getResultCode());
        System.out.println(remoteResult.getT());
    }

    private static void testQuery() {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("name", "Solr");
        condition.put("manu", "SolrInputDocument");
        RemoteResult remoteResult = queryService.queryByCondition(condition, 2, 1);
        System.out.println(remoteResult.getResultCode());
        PageModel t = (PageModel) remoteResult.getT();
        System.out.println(t.getTotalCount());
        System.out.println(t.getTotalPageNum());
        List datas = t.getDatas();
        for (int i = 0; i < datas.size(); i++) {
            System.out.println(datas.get(i).toString());
        }
    }

    private static void testRemoveIndex() {
        List<String> ids = new ArrayList<String>();
        ids.add("2");
        ids.add("3");
        RemoteResult<Integer> remoteResult = indexService.removeIndex(ids);
        System.out.println(remoteResult.getResultCode());
        System.out.println(remoteResult.getT());
    }
}
