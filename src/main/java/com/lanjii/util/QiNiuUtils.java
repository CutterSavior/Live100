package com.lanjii.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 七牛云上传工具
 *
 * @author LiZheng
 * @date 2024-10-09
 */
@Slf4j
public class QiNiuUtils {


    public static void upload(InputStream inputStream, String fileName) {
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = "bGQw0wK1Sp-IBlMFJO8tms505rXfVS3Vdaqqiag3";
        String secretKey = "PGzZb631430-pM3nLBHt1u5I-N4c1UJ5vV6uG8ZX";
        String bucket = "question-2024";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(inputStream, fileName, upToken, null, null);

            DefaultPutRet putRet = JsonUtils.jsonToObj(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);

                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\EDY\\Desktop\\好好学习.txt"));
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        upload(byteInputStream, "好好学习.txt");

    }
}
