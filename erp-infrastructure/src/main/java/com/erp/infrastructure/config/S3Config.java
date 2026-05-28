package com.erp.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(S3Properties.class)
public class S3Config {

    private final S3Properties s3Properties;

    @Bean
    public S3Client s3Client() {
        log.info("Configuring S3 Bucket");

        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                s3Properties.accessKey(),
                s3Properties.secretKey()
        );

        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(s3Properties.forcePathStyle())
                .build();

        var builder = S3Client.builder()
                .region(Region.of(s3Properties.region()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(s3Configuration);

        if (s3Properties.hasCustomEndpoint()) {
            builder.endpointOverride(URI.create(s3Properties.endpoint()));
        }

        return builder.build();
    }
}
