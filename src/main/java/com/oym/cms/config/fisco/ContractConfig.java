package com.oym.cms.config.fisco;

import com.google.common.collect.Lists;
import com.oym.cms.contract.CertificateIQ;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: Mr_OO
 * @Date: 2022/4/5 12:51
 */
@Slf4j
@Data
@Component
public class ContractConfig {

    @Resource
    BcosConfig bcosConfig;

    @Bean
    public BcosSDK bcosSDK(ConfigProperty configProperty) throws ConfigException {
        val configOption = new ConfigOption(configProperty, CryptoType.ECDSA_TYPE);
        return new BcosSDK(configOption);
    }

    @Bean
    public Client client(BcosSDK bcosSDK) {
        return bcosSDK.getClient(1);
    }

    @Bean
    public CertificateIQ certificateIQ(Client client) {
        val cryptoSuite = client.getCryptoSuite();
        val cryptoKeyPair = cryptoSuite.getCryptoKeyPair();
        cryptoSuite.setCryptoKeyPair(cryptoKeyPair);
        return CertificateIQ.load("", client, cryptoKeyPair);
    }

    @Bean
    public ConfigProperty configProperty() {
        val configProperty = new ConfigProperty();
        configProperty.setCryptoMaterial(bcosConfig.cryptoMaterial);
        configProperty.setAccount(bcosConfig.account);
        configProperty.setNetwork(new HashMap<>() {{
            put("peers", bcosConfig.network.get("peers"));
        }} );
        configProperty.setAmop(Lists.newArrayList());
        configProperty.setThreadPool(bcosConfig.threadPool);
        return configProperty;
    }
}
