package com.solinftec.dev8api.service;

import com.solinftec.dev8api.config.StorageProperties;
import com.solinftec.dev8api.service.exceptions.StorageException;
import com.solinftec.dev8api.service.exceptions.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageService {

    private Path rootLocation;

    public StorageService() {
        StorageProperties storageProperties;
        storageProperties = new StorageProperties();
        rootLocation = Paths.get(storageProperties.getLocation());
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException exception) {
            throw new StorageException("Could not initialize storage.");
        }
    }

    public String store(MultipartFile file) {

        try {
            if (file == null) return "";
            if (file.isEmpty()) return "";

            Path destinationFile;
            destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            InputStream inputStream;
            inputStream = file.getInputStream();

            if (ImageIO.read(file.getInputStream()) == null) {
                throw new IOException("File is not a valid image.");
            }

            String fileName;
            fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            Files.copy(
                    inputStream,
                    this.rootLocation.resolve(fileName));

            return fileName;
        } catch (IOException exception) {
            throw new RuntimeException("Error for image replacement: " + exception.getMessage());
        }

    }

    public String replace(String fileNameOld, MultipartFile fileNew) {
        if (fileNameOld == null) return store(fileNew);
        if (fileNameOld.isEmpty()) return store(fileNew);
        if (fileNew == null) return fileNameOld;
        if (fileNew.isEmpty()) throw new StorageException("Failed to replace empty file.");

        delete(fileNameOld);
        return store(fileNew);
    }

    public Path load(String filename) {
        return this.rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {

        try {
            Path file;
            file = load(filename);
            Resource resource;
            resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public Stream<Path> loadAll() {

        try {
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation)).map(this.rootLocation::relativize);
        } catch (IOException exception) {
            throw new StorageException("Failed to read stored files");
        }

    }

    public void delete(String filename) {
        if (filename == null) return;
        if (filename.isEmpty()) return;

        try {
            Files.delete(this.rootLocation.resolve(filename));
        } catch (IOException exception) {
            throw new RuntimeException("Could not delete file: " + exception.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }
}
