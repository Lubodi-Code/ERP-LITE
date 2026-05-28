package com.erp.infrastructure.aws.adapters;

import com.erp.domain.ports.ImageStorageService;
import com.erp.domain.product.ProductImage;
import com.erp.domain.shared.FileUploadException;
import com.erp.infrastructure.aws.AwsConfigModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class AwsImageStorageService implements ImageStorageService {

    private final S3Client s3Client;
    private final AwsConfigModel awsConfig;

    @Override
    public byte[] download(ProductImage image) {
        try {
            final String key = getKeyFromUrl(image.getFullUrl());
            final GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(awsConfig.bucketName())
                    .key(key)
                    .build();
            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
            byte[] bytes = response.asByteArray();
            log.info("Image downloaded successfully, size: {} bytes", bytes.length);
            return bytes;
        } catch (S3Exception e) {
            log.error("Error downloading image", e);
            throw new FileUploadException("Error downloading image", e);
        } catch (Exception e) {
            log.error("Unexpected error downloading image", e);
            throw new FileUploadException("Unexpected error downloading image", e);
        }
    }

    @Override
    public void delete(ProductImage image) {
        try {
            final String key = getKeyFromUrl(image.getFullUrl());
            final DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(awsConfig.bucketName())
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
            log.info("Image deleted successfully, key: {}", key);
        } catch (S3Exception e) {
            log.error("Error deleting image", e);
            throw new FileUploadException("Error deleting image", e);
        } catch (Exception e) {
            log.error("Unexpected error deleting image", e);
            throw new FileUploadException("Unexpected error deleting image", e);
        }
    }

    @Override
    public ProductImage upload(ProductImage image, String imageName, byte[] imageData) {
        try {
            final String key = "products/" + imageName;
            final PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(awsConfig.bucketName())
                    .key(key)
                    .contentType(determineContentType(imageName))
                    .contentLength((long) imageData.length)
                    .build();
            s3Client.putObject(putRequest, RequestBody.fromBytes(imageData));
            final String url = buildUrl(key);
            log.info("Image uploaded successfully to S3, URL: {}", url);
            return new ProductImage(url);
        } catch (S3Exception e) {
            log.error("Error uploading image", e);
            throw new FileUploadException("Error uploading image", e);
        } catch (Exception e) {
            log.error("Unexpected error uploading image", e);
            throw new FileUploadException("Unexpected error uploading image", e);
        }
    }

    private String getKeyFromUrl(String url) {
        String prefix = awsConfig.bucketName() + "/";
        int idx = url.indexOf(prefix);
        return idx >= 0 ? url.substring(idx + prefix.length()) : url;
    }

    private String buildUrl(String key) {
        return awsConfig.getBucketUrl() + "/" + key;
    }

    private String determineContentType(String filename) {
        String ext = filename.contains(".")
                ? filename.substring(filename.lastIndexOf('.') + 1).toLowerCase()
                : "";
        return switch (ext) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png"         -> "image/png";
            case "webp"        -> "image/webp";
            default            -> "application/octet-stream";
        };
    }
}
