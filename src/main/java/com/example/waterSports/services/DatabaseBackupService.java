package com.example.waterSports.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DatabaseBackupService {

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "manager";
    private static final String DB_NAME = "watersports";
    private static final String BACKUP_PATH = System.getProperty("user.home") + "/Downloads/";

    public void backupAndDownload(HttpServletResponse response) throws IOException, InterruptedException {
        String backupFileName = DB_NAME;
        String backupFilePath = BACKUP_PATH + backupFileName + "_backup.sql";

        // Create the backup
        String command = "mysqldump -u" + DB_USERNAME + " -p" + DB_PASSWORD + " " + DB_NAME + " > " + backupFilePath;
        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Backup completed successfully.");

            // Download the backup file
            downloadBackup(backupFilePath, response);
        } else {
            System.out.println("Backup failed. Exit code: " + exitCode);
        }
    }

    private void downloadBackup(String backupFilePath, HttpServletResponse response) throws IOException {
        String downloadFileName = DB_NAME + "_backup.sql";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);

        Files.copy(Paths.get(backupFilePath), response.getOutputStream());
        response.flushBuffer();

        System.out.println("Backup downloaded successfully.");
    }

    public void importDatabase(MultipartFile sqlFile) throws IOException, InterruptedException {
        String uploadedFilePath = BACKUP_PATH + sqlFile.getOriginalFilename();
        Path path = Paths.get(uploadedFilePath);
        sqlFile.transferTo(path);

        String command = "mysql -u" + DB_USERNAME + " -p" + DB_PASSWORD + " " + DB_NAME + " < " + uploadedFilePath;
        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Database import completed successfully.");
        } else {
            System.out.println("Database import failed. Exit code: " + exitCode);
        }

        Files.deleteIfExists(path);
    }
}
