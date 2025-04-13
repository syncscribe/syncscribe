package io.syncscribe.fileservice.components.storage;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import io.syncscribe.common.auth.OAuthContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class MinioService {
    private static final String BUCKET_NAME = "ss-docs";
    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) throws Exception {
        this.minioClient = minioClient;
        createBucket();
    }

    public boolean bucketExists(String name) throws Exception {
        return minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(name)
                        .build());
    }

    public InputStream getFile(String fileName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(OAuthContext.getUser().id() + "/" + fileName)
                        .build());
    }

    public String uploadFile(String fileName, InputStream inputStream) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(OAuthContext.getUser().id() + "/" + fileName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
        return OAuthContext.getUser().id() + "/" + fileName;
    }

    public ObjectWriteResponse copyFile(String objectName, String srcBucketName,
                                        String srcObjectName) throws Exception {
        return minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(BUCKET_NAME).object(objectName).build())
                        .bucket(srcBucketName)
                        .object(srcObjectName)
                        .build());
    }

    public void removeFile(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .build());
    }

    public void removeFiles(List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
            try {
                removeFile(s);
            } catch (Exception e) {
                log.error("Error delete files", e);
            }
        });
    }

    public String getPresignedObjectUrl(String objectName, Integer expires) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .expiry(expires)
                .bucket(BUCKET_NAME)
                .object(objectName)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    public String getPresignedObjectUrl(String objectName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(BUCKET_NAME)
                .object(objectName)
                .method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(args);
    }

    public String getUtf8ByURLDecoder(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

    private void createBucket() throws Exception {
        if (!bucketExists(BUCKET_NAME)) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(BUCKET_NAME)
                            .build());
        }
    }

}
