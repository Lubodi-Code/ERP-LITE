package com.erp.infrastructure.adapters;

import com.erp.domain.ports.ImageStorageService;
import com.erp.domain.product.ProductImage;
import com.erp.domain.shared.FileUploadException;
import com.erp.infrastructure.config.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class S3ImageStorageService implements ImageStorageService {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Override
    public ProductImage upload(ProductImage image, String imageName, byte[] imageData) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(s3Properties.bucketName())
                    .key(imageName)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(imageData));
            log.info("S3 upload successful: key={}, url={}", imageName, image.getFullUrl());
            return image;
        } catch (S3Exception e) {
            log.error("S3 upload failed for key '{}': {}", imageName, e.getMessage(), e);
            throw new FileUploadException("Failed to upload '%s' to S3".formatted(imageName), e);
        }
    }

    @Override
    public void delete(ProductImage image) {
        String key = image.getFileName();
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(s3Properties.bucketName())
                    .key(key)
                    .build());
            log.info("Deleted S3 object: key={}", key);
        } catch (S3Exception e) {
            log.error("S3 delete failed for key '{}': {}", key, e.getMessage(), e);
            throw new FileUploadException("Failed to delete '%s' from S3".formatted(key), e);
        }
    }

    @Override
    public byte[] download(ProductImage image) {
        String key = image.getFileName();
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
            throw new FileUploadException("Failed to download '%s' from S3".formatted(key), e);
        }
    }
}
