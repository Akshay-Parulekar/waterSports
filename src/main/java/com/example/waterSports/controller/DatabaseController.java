package com.example.waterSports.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/db/")
public class DatabaseController {

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private String dbName = "watersports";

    @GetMapping("/")
    public String home() {
        return "dbBackupRecovery";
    }

    /** -------------------------------------------------------
     * ✅ BACKUP DATABASE + PRINT FILE CONTENT
     * ------------------------------------------------------- */
    @GetMapping("/backup/")
    public int backupDatabase(HttpServletResponse response) {

        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(new Date());

        String backupFileName = "backup " + dbName + " " + timestamp + ".sql";
        File backupFile = new File(backupFileName);

        String command = String.format(
                "mysqldump -u%s -p%s %s",
                dbUsername, dbPassword, dbName
        );

        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
            pb.redirectOutput(backupFile);
            Process process = pb.start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {

                // ✅ PRINT ERROR returned by mysqldump
                String error = new String(process.getErrorStream().readAllBytes());
                System.out.println("===== MYSQLDUMP ERROR =====");
                System.out.println(error);
                System.out.println("===========================");

                throw new RuntimeException("Backup failed.");
            }

            // ✅ Print content of backup
        //    System.out.println(readFileContent(backupFile));

            // ✅ Send file
            response.setContentType("application/octet-stream");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + backupFile.getName() + "\"");

            FileCopyUtils.copy(new FileInputStream(backupFile), response.getOutputStream());
            response.flushBuffer();

            if (backupFile.exists()) backupFile.delete();
            return 1;

        } catch (Exception e) {
            if (backupFile.exists()) backupFile.delete();
            return 0;
        }
    }

    /** -------------------------------------------------------
     * ✅ RESTORE DATABASE + PRINT SQL FILE CONTENT
     * ------------------------------------------------------- */
    @PostMapping("/restore/")
    @ResponseBody
    public int restoreDatabase(@RequestParam("file") MultipartFile file) {

        try {
            ProcessBuilder restorePB = new ProcessBuilder(
                    "mysql",
                    "-u" + dbUsername,
                    "-p" + dbPassword,
                    dbName
            );

            Process restoreProcess = restorePB.start();

            try (OutputStream os = restoreProcess.getOutputStream()) {
                os.write(file.getBytes());
                os.flush();
            }

            int exit = restoreProcess.waitFor();

            if (exit != 0) {
            //    String error = new String(restoreProcess.getErrorStream().readAllBytes());
                return 0;
            }

            return 1;

        } catch (Exception e) {
            return 0;
        }
    }

    // ✅ Helper - Read entire file
    private String readFileContent(File file) throws IOException {
        return new String(FileCopyUtils.copyToByteArray(file));
    }
}
