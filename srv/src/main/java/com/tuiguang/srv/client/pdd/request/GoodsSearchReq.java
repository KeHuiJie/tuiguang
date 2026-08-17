package com.tuiguang.srv.client.pdd.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSearchReq {

    private String authCode = "53355a4e293a4aa3b324bde512e55028c1a8cae2";
    private String keyword;
    private Integer page;
    private Integer pageSize;
}
