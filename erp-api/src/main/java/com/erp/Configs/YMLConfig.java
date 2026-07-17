package com.erp.Configs;

import com.erp.infrastructure.aws.AwsConfigModel;
import com.erp.infrastructure.rest.config.JsonPlaceholderConfigModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:aws.yml", factory = YamlPropertySourceFactory.class)
@PropertySource(value = "classpath:json-placeholder/json-placeholder.yml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties({AwsConfigModel.class, JsonPlaceholderConfigModel.class})
public class YMLConfig {
}
