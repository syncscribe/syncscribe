package io.syncscribe.fileservice.contracts;

import java.util.List;

public record ListFileDirectoryResponse(
        List<Directory> directories, List<File> files) {
}
