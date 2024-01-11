package com.app.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dtos.FileModel;

@Service
public class FileServiceImplements implements FileService {

    @Override
    public FileModel uploadVideo(String path, MultipartFile file) throws IOException {
        FileModel filemodel = new FileModel();
        String fileName = file.getOriginalFilename();

        String filePath = path + File.separator + fileName;

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        Path destinationPath = Paths.get(filePath);

        // Check if the file already exists
        if (Files.exists(destinationPath)) {
            // Handle the situation where the file already exists (e.g., generate a new filename)
            fileName = generateNewFileName(fileName);
            filePath = path + File.separator + fileName;
            destinationPath = Paths.get(filePath);
        }

        Files.copy(file.getInputStream(), destinationPath);
        filemodel.setVideoFileName(fileName);
        return filemodel;
    }

    @Override
    public InputStream getVideoFile(String path, String finalName, int id) throws FileNotFoundException {
        String fullPath = path + File.separator + finalName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }

    // You can implement a method to generate a new filename if needed
    private String generateNewFileName(String originalFileName) {
        // Implement your logic to generate a new unique filename
        // For example, you can append a timestamp or a random string
        return "new_" + System.currentTimeMillis() + "_" + originalFileName;
    }
}
