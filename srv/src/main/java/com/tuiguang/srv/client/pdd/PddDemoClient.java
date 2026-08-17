package com.tuiguang.srv.client.pdd;

import com.tuiguang.srv.client.pdd.request.*;

public interface PddDemoClient {

    /**
     * 获取 access_token（pdd.pop.auth.token.create）
     * 用 code 换取 access_token / refresh_token
     *
     * @param req 包含授权 code
     * @return 响应 JSON 字符串（access_token、refresh_token、过期时间等）
     */
    String createAccessToken(AuthTokenReq req);

    /**
     * 多多进宝商品搜索（pdd.ddk.goods.search）
     * 按关键词搜索推广商品列表
     *
     * @param req 关键词、页码、每页数量等
     * @return 响应 JSON 字符串（goods_list 商品列表）
     */
    String goodsSearch(GoodsSearchReq req);

    /**
     * 多多进宝商品详情查询（pdd.ddk.goods.detail）
     * 按 goods_sign 查询单个商品详情
     *
     * @param req 商品签名 goods_sign
     * @return 响应 JSON 字符串（商品详情 goods_detail）
     */
    String goodsDetail(GoodsDetailReq req);

    /**
     * 多多进宝商品推广链接生成（pdd.ddk.goods.promotion.url.generate）
     * 为单个商品生成普通推广链接
     *
     * @param req 授权码、商品签名、推广位 pid
     * @return 响应 JSON 字符串（goods_promotion_url_list 推广链接列表）
     */
    String generatePromotionUrl(PromotionUrlReq req);

    /**
     * 多多进宝生成推广位（pdd.ddk.oauth.goods.pid.generate，需 access_token）
     * 批量生成推广位 pid
     *
     * @param req 生成数量 number（1~100，默认 10）、推广位名称列表 pIdNameList、媒体 id mediaId
     * @return 响应 JSON 字符串（p_id_list 推广位列表：p_id、p_id_name）
     */
    String generateGoodsPid(GoodsPidGenerateReq req);

    /**
     * 查询是否绑定备案（pdd.ddk.member.authority.query，无需 access_token）
     * 通过推广位 pid 和自定义参数查询用户是否已绑定备案（会员权限）
     *
     * @param req 推广位 pid、自定义参数 customParameters（JSON 字符串，如 {"uid":"11111"}，最长 64 字节）
     * @return 响应 JSON 字符串（authority_query_response.bind：1-已绑定，0-未绑定）
     */
    String memberAuthorityQuery(MemberAuthorityQueryReq req);

    /**
     * 多多进宝渠道推广链接生成（pdd.ddk.oauth.goods.prom.url.generate，需 access_token）
     * 为商品生成渠道推广链接，可选生成短链、schema 链接、小程序链接、授权链接等
     *
     * @param req 授权码、推广位 pid、商品签名列表 goodsSignList、自定义参数、
     *            各生成开关（短链/微信小程序/schema/备案授权链接）、multi_group、search_id
     * @return 响应 JSON 字符串（goods_promotion_url_list：url、short_url、schema_url、小程序信息等）
     */
    String generatePromUrl(PromUrlGenerateReq req);
}
