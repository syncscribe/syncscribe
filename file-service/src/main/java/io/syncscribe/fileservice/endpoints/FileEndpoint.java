package io.syncscribe.fileservice.endpoints;

import io.syncscribe.fileservice.components.files.FileService;
import io.syncscribe.fileservice.components.storage.MinioService;
import io.syncscribe.fileservice.contracts.GetFileResponse;
import io.syncscribe.fileservice.datasource.models.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
public class FileEndpoint {
    private final FileService fileService;
    private final MinioService minioService;

    public FileEndpoint(FileService fileService, MinioService minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @GetMapping(params = {"page", "size"})
    public List<GetFileResponse> list(@RequestParam int page, @RequestParam int size) {
        return fileService.listFiles(page, size).stream()
                .map(doc -> new GetFileResponse(doc.getId(), doc.getFileName()))
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            fileService.softDeleteFile(id);
        } catch (FileNotFoundException e) {
            log.error("Error soft deleting file", e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetFileResponse> get(@PathVariable String id, @RequestParam String password) {
        try {
            var doc = fileService.getFile(id, password);
            var response = new GetFileResponse(doc.getId(), doc.getFileName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get document", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id, @RequestParam String password) {
        try {
            var doc = fileService.getFile(id, password);
            var data = minioService.getFile(doc.getUrl());
            var mimeType = doc.getMimeType().isBlank() ? "application/octet-stream" : doc.getMimeType();
            return ResponseEntity
                    .ok()
                    .contentLength(doc.getSizeInBytes())
                    .header("Content-type", mimeType)
                    .header("Content-disposition", "attachment; filename=\"" + doc.getFileName() + "\"")
                    .body(new InputStreamResource(data));
        } catch (Exception e) {
            log.error("Failed to get document", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> upload(@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        try {
            var tika = new Tika();
            var inputStream = file.getInputStream();
            var fileName = file.getOriginalFilename();
            var mimeType = tika.detect(inputStream, file.getOriginalFilename());
            var fileModel = FileModel.newFile(fileName, file.getSize(), mimeType);
            var url = minioService.uploadFile(fileModel.getId(), inputStream);
            fileModel.setUrl(url);
            fileService.save(fileModel);
            return ResponseEntity.ok(fileModel.getId());
        } catch (Exception e) {
            log.error("Failed to upload file", e);
            return ResponseEntity.badRequest().build();
        }
    }
}