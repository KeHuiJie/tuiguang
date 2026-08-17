package com.tuiguang.srv.client.pdd.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSearchReq {

    private String authCode;
    private String keyword;
    private Integer page;
    private Integer pageSize;

    private String pid;
    private String customParameters;

}
