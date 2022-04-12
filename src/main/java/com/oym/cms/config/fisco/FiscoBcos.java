package com.oym.cms.config.fisco;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;

/**
 * @author 14396
 */
@Data
@Component
@Slf4j
public class FiscoBcos {

    BcosSDK bcosSDK;

    public void init() {
        ConfigProperty configProperty = loadProperty();
        ConfigOption configOption ;
        try {
            configOption = new ConfigOption(configProperty);
        } catch (ConfigException e) {
            log.error("init error:" + e.toString());
            return ;
        }
        bcosSDK = new BcosSDK(configOption);
    }

    public ConfigProperty loadProperty() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);
        String configFile = "/application.yml";
        try (InputStream inputStream = this.getClass().getResourceAsStream(configFile)) {
            return yaml.loadAs(inputStream, ConfigProperty.class);
        } catch (Exception e) {
            log.error("load property: ", e);
        }
        return null;
    }
}