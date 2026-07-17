package com.erp.infrastructure.storage;

import com.erp.domain.shared.FileUploadException;
import com.erp.infrastructure.config.S3Properties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class S3ServiceAdapter {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @PostConstruct
    void validateBucketOnStartup() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder()
                    .bucket(s3Properties.bucketName())
                    .build());
            log.info("S3 bucket '{}' is reachable", s3Properties.bucketName());
        } catch (NoSuchBucketException e) {
            throw new IllegalStateException(
                    "S3 bucket '%s' does not exist. Create it before starting the application."
                            .formatted(s3Properties.bucketName()), e);
        } catch (S3Exception e) {
            log.warn("Could not verify S3 bucket '{}' on startup: {}", s3Properties.bucketName(), e.getMessage());
        }
    }

    public String uploadObject(String key, byte[] content) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(s3Properties.bucketName())
                    .key(key)
                    .build();
            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(content));
            log.debug("Uploaded S3 object: key={}, eTag={}", key, response.eTag());
            String url = buildObjectUrl(key);
            log.info("S3 upload successful: {}", url);
            return url;
        } catch (S3Exception e) {
            log.error("S3 upload failed for key '{}': {}", key, e.getMessage(), e);
            throw new FileUploadException("Failed to upload file '%s' to S3".formatted(key), e);
        }
    }

    public byte[] downloadObject(String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(s3Properties.bucketName())
                    .key(key)
                    .build();
            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
            log.debug("Downloaded S3 object: key={}, bytes={}", key, response.asByteArray().length);
            return response.asByteArray();
        } catch (S3Exception e) {
            log.error("S3 download failed for key '{}': {}", key, e.getMessage(), e);
            throw new FileUploadException("Failed to download file '%s' from S3".formatted(key), e);
        }
    }

    public void deleteObject(String key) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(s3Properties.bucketName())
                    .key(key)
                    .build());
            log.info("Deleted S3 object: key={}", key);
        } catch (S3Exception e) {
            log.error("S3 delete failed for key '{}': {}", key, e.getMessage(), e);
            throw new FileUploadException("Failed to delete file '%s' from S3".formatted(key), e);
        }
    }

    public List<String> listBuckets() {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            List<String> names = response.buckets().stream()
                    .map(b -> b.name())
                    .toList();
            log.debug("Listed {} S3 buckets", names.size());
            return names;
        } catch (S3Exception e) {
            log.error("S3 listBuckets failed: {}", e.getMessage(), e);
            throw new FileUploadException("Failed to list S3 buckets", e);
        }
    }

    private String buildObjectUrl(String key) {
        if (s3Properties.forcePathStyle()) {
            return "%s/%s/%s".formatted(s3Properties.endpoint(), s3Properties.bucketName(), key);
        }
        return "https://%s.s3.%s.amazonaws.com/%s"
                .formatted(s3Properties.bucketName(), s3Properties.region(), key);
    }

    private String extractKeyFromUrl(String imageUrl) {
        String prefix = s3Properties.bucketName() + "/";
        int idx = imageUrl.indexOf(prefix);
        return idx < 0 ? imageUrl : imageUrl.substring(idx + prefix.length());
    }
}
