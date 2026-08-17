package com.tuiguang.srv.client.pdd;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuiguang.srv.client.pdd.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.tuiguang.srv.config.PddDemoConfig;

@Component
@Slf4j
public class PddDemoClientImpl implements PddDemoClient {

    private final PddDemoConfig config;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PddDemoClientImpl(PddDemoConfig config) {
        this.config = config;
    }

    public String createAccessToken(AuthTokenReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("code", req.getAuthCode());
            log.info("[拼多多]获取access_token,req[{}]", params);
            String resp = routerCall("pdd.pop.auth.token.create", params);
            log.info("[拼多多]获取access_token,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]获取access_token,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    public String goodsSearch(GoodsSearchReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("keyword", req.getKeyword());
            params.put("page", String.valueOf(req.getPage() == null ? 1 : req.getPage()));
            params.put("page_size", String.valueOf(req.getPageSize() == null ? 20 : req.getPageSize()));
            params.put("pid",req.getPid());
            params.put("custom_parameters",req.getCustomParameters());
            log.info("[拼多多]商品搜索,req[{}]", params);
            String resp = routerCall("pdd.ddk.goods.search", params, getAccessToken(req.getAuthCode()));
            log.info("[拼多多]商品搜索,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]商品搜索,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    public String goodsDetail(GoodsDetailReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("goods_sign", req.getGoodsSign());
            params.put("goods_sign_list", "[\"" + escapeJson(req.getGoodsSign()) + "\"]");
            log.info("[拼多多]商品详情,req[{}]", params);
            String resp = routerCall("pdd.ddk.goods.detail", params, getAccessToken(req.getAuthCode()));
            log.info("[拼多多]商品详情,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]商品详情,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    public String generatePromotionUrl(PromotionUrlReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("p_id", req.getPid());
            params.put("goods_sign_list", "[\"" + escapeJson(req.getGoodsSign()) + "\"]");
            log.info("[拼多多]生成推广链接,req[{}]", params);
            String resp = routerCall("pdd.ddk.goods.promotion.url.generate", params, getAccessToken(req.getAuthCode()));
            log.info("[拼多多]生成推广链接,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]生成推广链接,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    public String generateGoodsPid(GoodsPidGenerateReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("number", String.valueOf(req.getNumber() == null ? 10 : req.getNumber()));
            if (req.getPIdNameList() != null && !req.getPIdNameList().isEmpty()) {
                params.put("p_id_name_list", objectMapper.writeValueAsString(req.getPIdNameList()));
            }
            if (req.getMediaId() != null) {
                params.put("media_id", String.valueOf(req.getMediaId()));
            }
            log.info("[拼多多]生成推广位,req[{}]", params);
            String resp = routerCall("pdd.ddk.oauth.goods.pid.generate", params, getAccessToken(req.getAuthCode()));
            log.info("[拼多多]生成推广位,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]生成推广位,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    public String generatePromUrl(PromUrlGenerateReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            if (hasText(req.getPid())) {
                params.put("p_id", req.getPid());
            }
            if (req.getGoodsSignList() != null && !req.getGoodsSignList().isEmpty()) {
                params.put("goods_sign_list", objectMapper.writeValueAsString(req.getGoodsSignList()));
            }
            if (StringUtils.hasText(req.getCustomParameters())) {
                params.put("custom_parameters", req.getCustomParameters());
            }
            if (req.getGenerateAuthorityUrl()) {
                params.put("generate_authority_url", req.getGenerateAuthorityUrl());
            }
            log.info("[拼多多]生成商品推广链接,req[{}]", params);
            String resp = routerCall("pdd.ddk.oauth.goods.prom.url.generate", params, getAccessToken(req.getAuthCode()));
            log.info("[拼多多]生成商品推广链接,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]生成商品推广链接,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    private String routerCall(String type, Map<String, Object> bizParams) {
        return routerCall(type, bizParams, null);
    }

    private String routerCall(String type, Map<String, Object> bizParams, String accessToken) {
        if (!config.hasRequiredCredentials()) {
            return "PDD credentials are missing. Please set pdd.client-id and pdd.client-secret.";
        }
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("client_id", config.getClientId());
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        params.put("data_type", "JSON");
        params.put("version", config.getVersion());
        params.put("type", type);
        if (hasText(accessToken)) {
            params.put("access_token", accessToken);
        }
        if (bizParams != null) {
            params.putAll(bizParams);
        }
        params.put("sign", sign(params, config.getClientSecret()));

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            form.add(entry.getKey(), entry.getValue());
        }
        return restTemplate.postForObject(config.getHost(), form, String.class);
    }

    public String memberAuthorityQuery(MemberAuthorityQueryReq req) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("pid", req.getPid());
            if (hasText(req.getCustomParameters())) {
                params.put("custom_parameters", req.getCustomParameters());
            }
            log.info("[拼多多]会员权益查询,req[{}]", params);
            String resp = routerCall("pdd.ddk.member.authority.query", params);
            log.info("[拼多多]会员权益查询,resp[{}]", resp);
            return resp;
        } catch (Exception e) {
            log.warn("[拼多多]会员权益查询,系统异常,错误原因[{}]", e.getMessage(), e);
            return null;
        }
    }

    private String getAccessToken(String authCode) throws Exception {
        return "e7eb92d247d04137bdbe32dda510fc3a27991031";
//        String resp = createAccessToken(new AuthTokenReq(authCode));
//        JsonNode root = objectMapper.readTree(resp);
//        JsonNode tokenNode = root.findValue("access_token");
//        if (tokenNode == null || !hasText(tokenNode.asText())) {
//            throw new IllegalStateException("获取access_token失败: " + resp);
//        }
//        return tokenNode.asText();
    }

    private static String sign(Map<String, Object> params, String clientSecret) {
        StringBuilder raw = new StringBuilder(clientSecret);
        for (Map.Entry<String, Object> entry : new TreeMap<String, Object>(params).entrySet()) {
            if (!"sign".equals(entry.getKey()) && entry.getValue() != null) {
                raw.append(entry.getKey()).append(entry.getValue());
            }
        }
        raw.append(clientSecret);
        return md5Upper(raw.toString());
    }

    private static String md5Upper(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                String s = Integer.toHexString(b & 0xff).toUpperCase();
                if (s.length() == 1) { hex.append('0'); }
                hex.append(s);
            }
            return hex.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create PDD sign", e);
        }
    }

    private static String escapeJson(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static boolean hasText(String value) {
        return value != null && value.trim().length() > 0;
    }
}
