package com.tuiguang.srv.client.pdd.demo;

import com.tuiguang.srv.SrvApplication;
import com.tuiguang.srv.client.pdd.PddDemoClient;
import com.tuiguang.srv.client.pdd.request.AuthTokenReq;
import com.tuiguang.srv.client.pdd.request.GoodsSearchReq;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class PddDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SrvApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        try {
            PddDemoClient client = context.getBean(PddDemoClient.class);
            String authCode = args.length > 0 ? args[0] : "53355a4e293a4aa3b324bde512e55028c1a8cae2";
            String keyword = args.length > 1 ? args[1] : "手机";
            System.out.println(client.goodsSearch(new GoodsSearchReq(authCode, keyword, 1, 20)));

            // Easy-to-edit examples:
            // System.out.println(client.goodsDetail(new GoodsDetailReq(authCode, "your_goods_sign")));
            // System.out.println(client.generatePromotionUrl(new PromotionUrlReq(authCode, "your_goods_sign", "your_pid")));
        } finally {
            context.close();
        }
    }
}
