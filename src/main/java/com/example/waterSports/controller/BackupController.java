package com.example.waterSports.controller;

import com.example.waterSports.services.DatabaseBackupService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/backup/")
public class BackupController {

    @Autowired
    private DatabaseBackupService backupService;

    @GetMapping("/download/")
    public void backupAndDownload(HttpServletResponse response) throws IOException, InterruptedException {
        backupService.backupAndDownload(response);
    }

    @PostMapping("/upload/")
    public String importDatabase(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        backupService.importDatabase(file);
        return "redirect:/water/";
    }
}
