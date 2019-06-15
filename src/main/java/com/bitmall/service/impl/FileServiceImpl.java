package com.bitmall.service.impl;

import com.bitmall.common.Const;
import com.bitmall.dao.ProductMapper;
import com.bitmall.service.IFileService;
import com.bitmall.util.AliyunUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.FileURLMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author liuyuehe
 * @date 2019/6/15 20:10
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    @Autowired
    ProductMapper productMapper;

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public HashMap<String, String> upload(MultipartFile file, String path) {
        HashMap<String, String> hashMap = new HashMap<>();
        String filename = file.getOriginalFilename();
        // 获取xxx.jpg这样的文件格式  --> jpg
        String fileExtensionName = filename.substring(filename.lastIndexOf(".") + 1) ;
        String uploadFilename = UUID.randomUUID() + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}", filename, path, uploadFilename);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFilename);
        try {
            file.transferTo(targetFile);
            String uploadUrl = "https://"+ Const.BUCKETNAME+"."+Const.ENDPOINT+"/"+ AliyunUploadUtil.upload(targetFile);
            logger.info(uploadUrl);
            targetFile.deleteOnExit();
            hashMap.put("uri", targetFile.getName());
            hashMap.put("url", uploadUrl);
        } catch (IOException e) {
            logger.error("文件上传失败：" + e);
            // 在这儿返回null有用吗
            return null;
        }
        return hashMap;
    }
}
