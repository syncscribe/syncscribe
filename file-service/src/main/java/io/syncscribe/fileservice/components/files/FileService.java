package io.syncscribe.fileservice.components.files;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.fileservice.components.AppConfig;
import io.syncscribe.fileservice.components.security.EncryptionUtils;
import io.syncscribe.fileservice.contracts.IllegalActionException;
import io.syncscribe.fileservice.datasource.models.FileModel;
import io.syncscribe.fileservice.datasource.models.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class FileService {
    private static final String FILE_NOT_FOUND_MSG = "File not found";
    private final FileRepository fileRepository;
    private final ShareLinkService shareLinkService;
    private final AppConfig appConfig;

    public FileService(
            FileRepository fileRepository,
            ShareLinkService shareLinkService, AppConfig appConfig) {
        this.fileRepository = fileRepository;
        this.shareLinkService = shareLinkService;
        this.appConfig = appConfig;
    }

    @Transactional
    public void softDeleteFile(String id) throws FileNotFoundException {
        var file = fileRepository.findById(id).orElseThrow(() -> new FileNotFoundException(FILE_NOT_FOUND_MSG));
        var userId = OAuthContext.getUser().id();
        if (!file.getOwnerId().equals(userId)) {
            throw new IllegalActionException("You are not the owner of this file");
        }
        file.setMarkForDelete(true);
        fileRepository.save(file);
    }

    public FileModel getFile(String id, String password) throws Exception {
        var doc = fileRepository.findById(id).orElseThrow(() -> new FileNotFoundException(FILE_NOT_FOUND_MSG));
        var user = OAuthContext.getUser();
        if (doc.getOwnerId().equals(user.id())) {
            return doc;
        }
        var shareLink = shareLinkService.getShareLinkByDocument(id).orElseThrow(() -> new IllegalActionException("You are not allowed to access this file"));
        if ((shareLink.getExpiredAt() == null || shareLink.getExpiredAt().isAfter(OffsetDateTime.now()))
                && EncryptionUtils.decrypt(shareLink.getPassword(), appConfig.getSecretKey()).equals(password)) {
            return doc;
        }
        throw new IllegalActionException("You are not allowed to access this file");
    }

    public List<FileModel> listFiles(int page, int size) {
        var pageResult = fileRepository.findAll(PageRequest.of(page, size));
        return pageResult.getContent();
    }

    public void save(FileModel file) {
        fileRepository.save(file);
    }
}
