package com.erp.domain.ports;

import com.erp.domain.product.ProductImage;

public interface ImageStorageService {

    ProductImage upload(ProductImage image, String imageName, byte[] imageData);

    void delete(ProductImage image);

    byte[] download(ProductImage image);
}
