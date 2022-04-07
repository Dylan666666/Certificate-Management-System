package com.oym.cms.config.fisco;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr_OO
 * @Date: 2022/4/5 12:49
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "contract")
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
public class BcosConfig {
    protected Map<String, Object> cryptoMaterial;
    public Map<String, List<String>> network;
    public Map<String, Object> account;
    public Map<String, Object> threadPool;
}
