package com.tuiguang.srv.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class PddDemoConfig {

    @Value("${pdd.client-id:de78e789550342468e2229ca20b3c3a3}")
    private String clientId = "de78e789550342468e2229ca20b3c3a3";

    @Value("${pdd.client-secret:73193ee64bba8e72230fe7ad36db01298349566e}")
    private String clientSecret = "73193ee64bba8e72230fe7ad36db01298349566e";

    @Value("${pdd.host:https://gw-api.pinduoduo.com/api/router}")
    private String host = "https://gw-api.pinduoduo.com/api/router";

    @Value("${pdd.version:V1}")
    private String version = "V1";

    public boolean hasRequiredCredentials() { return hasText(clientId) && hasText(clientSecret); }
    private boolean hasText(String value) { return value != null && value.trim().length() > 0; }
}
