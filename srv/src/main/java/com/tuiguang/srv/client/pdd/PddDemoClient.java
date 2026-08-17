package com.tuiguang.srv.client.pdd;

import com.tuiguang.srv.client.pdd.request.AuthTokenReq;
import com.tuiguang.srv.client.pdd.request.GoodsDetailReq;
import com.tuiguang.srv.client.pdd.request.GoodsSearchReq;
import com.tuiguang.srv.client.pdd.request.PromotionUrlReq;

public interface PddDemoClient {

    String createAccessToken(AuthTokenReq req);

    String goodsSearch(GoodsSearchReq req);

    String goodsDetail(GoodsDetailReq req);

    String generatePromotionUrl(PromotionUrlReq req);
}
