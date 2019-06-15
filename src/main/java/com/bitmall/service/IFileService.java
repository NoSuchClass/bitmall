package com.bitmall.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface IFileService {
    HashMap<String, String> upload(MultipartFile file, String path);
}
