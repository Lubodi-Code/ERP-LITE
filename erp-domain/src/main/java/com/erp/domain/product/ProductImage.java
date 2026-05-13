package com.erp.domain.product;

import java.util.regex.Pattern;

public record ProductImage(String imageUrl) {

    private static final Pattern URL_PATTERN =
            Pattern.compile("^https?://[^\\s/$.?#].[^\\s]*$");

    public ProductImage {
        if (imageUrl == null || imageUrl.isBlank())
            throw new IllegalArgumentException("ProductImage imageUrl must not be blank");
        if (!URL_PATTERN.matcher(imageUrl).matches())
            throw new IllegalArgumentException("ProductImage imageUrl is not a valid URL: " + imageUrl);
    }

    public static ProductImage of(String imageUrl) {
        return new ProductImage(imageUrl);
    }

    public String getFullUrl() {
        return imageUrl;
    }

    public String getFileName() {
        int lastSlash = imageUrl.lastIndexOf('/');
        return lastSlash >= 0 ? imageUrl.substring(lastSlash + 1) : imageUrl;
    }
}
