package com.tuiguang.srv.client.pdd.demo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tuiguang.srv.SrvApplication;
import com.tuiguang.srv.client.pdd.PddDemoClient;
import com.tuiguang.srv.client.pdd.request.*;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Handler;

public class PddDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SrvApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        try {
            String pid = "44685803_317539089";
            PddDemoClient client = context.getBean(PddDemoClient.class);
            String authCode = args.length > 0 ? args[0] : "31a02896bcc048afba6b742c99e15d7084c2bd51";
            String keyword = args.length > 1 ? args[1] : "榴莲月饼";
            HashMap<String, Object> customParameters = new HashMap<>();
            customParameters.put("uid","123123");
            System.out.println(client.goodsSearch(new GoodsSearchReq(authCode, keyword, 1, 20, pid, JSON.toJSONString(customParameters))));
//            System.out.println(client.generateGoodsPid(new GoodsPidGenerateReq(authCode, 1, java.util.Arrays.asList("推广位c"), null)));
//            System.out.println(client.generatePromUrl(initPromUrlGenerateReq(pid)));
//            System.out.println(client.memberAuthorityQuery(new MemberAuthorityQueryReq(pid, JSON.toJSONString(customParameters))));

        } finally {
            context.close();
        }
    }

    private static PromUrlGenerateReq initPromUrlGenerateReq(String pid, HashMap<String,Object> customParameters) {
        PromUrlGenerateReq promUrlGenerateReq = new PromUrlGenerateReq();
        promUrlGenerateReq.setPid(pid);
        promUrlGenerateReq.setGenerateAuthorityUrl(true);
        promUrlGenerateReq.setCustomParameters(JSON.toJSONString(customParameters));
        promUrlGenerateReq.setGoodsSignList(Arrays.asList("c9r2omogKFFAc7WBwvbZU1ikIb16_J3CTa8HNN"));
        return promUrlGenerateReq;
    }
}
