package com.erp.domain.ports.services;

import com.erp.domain.product.ProductImage;

public interface ImageServicePort {

    ProductImage upload(ProductImage image, String imageName, byte[] imageData);

    void delete(ProductImage image);

    byte[] download(ProductImage image);
}
