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

    private static Path ROOT_LOCATION;

    public StorageService() {
        StorageProperties storageProperties;
        storageProperties = new StorageProperties();
        ROOT_LOCATION = Paths.get(storageProperties.getLocation());
    }

    public void init() {
        try {
            Files.createDirectories(ROOT_LOCATION);
        } catch (IOException exception) {
            throw new StorageException("Could not initialize storage.");
        }
    }

    public String store(MultipartFile file) {

        try {
            if (file == null) return "";
            if (file.isEmpty()) return "";

            Path destinationFile;
            destinationFile = this.ROOT_LOCATION.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.getParent().equals(this.ROOT_LOCATION.toAbsolutePath())) {
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
                    this.ROOT_LOCATION.resolve(fileName));

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
        return this.ROOT_LOCATION.resolve(filename);
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
            return Files.walk(this.ROOT_LOCATION, 1).filter(path -> !path.equals(this.ROOT_LOCATION)).map(this.ROOT_LOCATION::relativize);
        } catch (IOException exception) {
            throw new StorageException("Failed to read stored files");
        }

    }

    public void delete(String filename) {
        if (filename == null) return;
        if (filename.isEmpty()) return;

        try {
            Files.delete(this.ROOT_LOCATION.resolve(filename));
        } catch (IOException exception) {
            throw new RuntimeException("Could not delete file: " + exception.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.ROOT_LOCATION.toFile());
    }
}
