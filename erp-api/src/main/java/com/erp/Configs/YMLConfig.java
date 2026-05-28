package com.erp.Configs;

import com.erp.infrastructure.aws.AwsConfigModel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:aws.yml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties(AwsConfigModel.class)
public class YMLConfig {
}
