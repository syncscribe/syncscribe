package io.syncscribe.documentservice.components.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetBucketPolicyArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

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

    public String getBucketPolicy(String name) throws Exception {
        return minioClient.getBucketPolicy(
                GetBucketPolicyArgs.builder()
                        .bucket(name)
                        .build());
    }

    public List<Bucket> getAllBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    public Bucket getBucket(String name) throws Exception {
        return getAllBuckets().stream()
                .filter(b -> b.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bucket not found"));
    }

    public void deleteBucket(String name) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(name).build());
    }

    public boolean objectExists(String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean fileExists(String fileName) {
        try {
            var results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(BUCKET_NAME).prefix(fileName).recursive(false).build());
            for (var result : results) {
                var item = result.get();
                if (item.isDir() && fileName.equals(item.objectName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public List<Item> getAllObjectsByPrefix(String prefix, boolean recursive) throws Exception {
        List<Item> list = new ArrayList<>();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(BUCKET_NAME).prefix(prefix).recursive(recursive).build());
        if (objectsIterator != null) {
            for (Result<Item> o : objectsIterator) {
                Item item = o.get();
                list.add(item);
            }
        }
        return list;
    }

    public InputStream getObject(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .build());
    }

    public InputStream getObject(String objectName, long offset, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .offset(offset)
                        .length(length)
                        .build());
    }

    public Iterable<Result<Item>> listObjects(String prefix, boolean recursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(BUCKET_NAME)
                        .prefix(prefix)
                        .recursive(recursive)
                        .build());
    }

    public ObjectWriteResponse uploadFile(MultipartFile file, String objectName, String contentType) throws Exception {
        InputStream inputStream = file.getInputStream();
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .contentType(contentType)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    public ObjectWriteResponse uploadFile(String objectName, String fileName) throws Exception {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .filename(fileName)
                        .build());
    }

    public ObjectWriteResponse uploadFile(String objectName, InputStream inputStream)
            throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .build());
    }

    public ObjectWriteResponse createDir(String objectName) throws Exception {
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .stream(new ByteArrayInputStream(new byte[] {}), 0, -1)
                        .build());
    }

    public String getFileStatusInfo(String objectName) throws Exception {
        return minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(objectName)
                        .build())
                .toString();
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
