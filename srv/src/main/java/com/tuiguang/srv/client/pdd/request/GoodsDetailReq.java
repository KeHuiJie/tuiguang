package com.tuiguang.srv.client.pdd.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailReq {

    private String authCode;
    private String goodsSign;
}
