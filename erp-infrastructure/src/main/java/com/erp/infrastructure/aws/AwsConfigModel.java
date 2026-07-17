package com.erp.infrastructure.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "aws.s3")
public record AwsConfigModel(
        @NotBlank String bucketName,
        @NotBlank String region,
        @NotBlank String endpoint,
        @NotBlank String accessKey,
        @NotBlank String secretKey,
        boolean forcePathStyle
) {
    public String getBucketUrl() {
        return String.format("%s/%s", endpoint, bucketName);
    }
}
