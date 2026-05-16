package com.erp.domain.ports;

public interface ImageStorageService {

    String upload(String fileName, byte[] content);

    void delete(String imageUrl);

    byte[] download(String imageUrl);
}
