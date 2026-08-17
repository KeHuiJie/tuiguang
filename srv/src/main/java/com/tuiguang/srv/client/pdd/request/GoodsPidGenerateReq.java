package com.tuiguang.srv.client.pdd.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 多多客生成推广位请求（pdd.ddk.oauth.goods.pid.generate）
 * 创建多多进宝推广位，需要 access_token（通过 authCode 换取）。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsPidGenerateReq {

    /** 授权码，用于换取 access_token */
    private String authCode;

    /** 要生成的推广位数量，默认为10，范围为:1~100 */
    private Integer number;

    /** 推广位名称，例如["名称1","名称2"] */
    private List<String> pIdNameList;

    /** 媒体id */
    private Long mediaId;
}
