package com.tuiguang.srv.client.pdd.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 多多进宝推广链接生成请求（pdd.ddk.oauth.goods.prom.url.generate）
 * 通过商品 goodsSign 批量生成推广链接，需要 access_token（通过 authCode 换取）。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromUrlGenerateReq {

    /** 授权码，用于换取 access_token */
    private String authCode;

    /** 推广位id，例如 "60005_612"（会包装为 p_id_list，长度最大为1） */
    private String pid;

    /** 商品goodsSign列表，例如 ["c9r2omogKFFAc7WBwvbZU1ikIb16_J3CTa8HNN"] */
    private List<String> goodsSignList;

    /**
     * 自定义参数，为链接打上自定义标签；最长限制64个字节。
     * 格式为: {"uid":"11111","sid":"22222"}，其中 uid 用户唯一标识，必填；sid 非必填。
     */
    private String customParameters;

    /** 是否生成短链接，true-是，false-否，默认false */
    private Boolean generateShortUrl;

    /** 是否生成拼多多福利券微信小程序推广信息 */
    private Boolean generateWeApp;

    /** 是否返回 schema URL */
    private Boolean generateSchemaUrl;

    /** 是否生成带授权备案的商品链接 */
    private Boolean generateAuthorityUrl;

    /** 单人团/多人团标志。true-多人团，false-单人团 */
    private Boolean multiGroup;

    /** 搜索id，来自搜索/推荐接口，传入可提高收益 */
    private String searchId;
}
