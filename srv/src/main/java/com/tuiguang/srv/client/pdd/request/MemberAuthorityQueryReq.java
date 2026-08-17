package com.tuiguang.srv.client.pdd.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 多多客会员权益查询请求（pdd.ddk.member.authority.query）
 * 通过推广位id和自定义参数查询用户是否已绑定备案。
 * 该接口不需要 access_token。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuthorityQueryReq {

    /** 推广位id */
    private String pid;

    /**
     * 自定义参数，为链接打上自定义标签；最长限制64个字节。
     * 格式为: {"uid":"11111","sid":"22222"}，其中 uid 用户唯一标识，
     * 可自行加密后传入，每个用户仅且对应一个标识，必填；
     * sid 上下文信息标识，例如sessionId等，非必填。
     * 该json字符串中也可以加入其他自定义的key。
     */
    private String customParameters;
}
