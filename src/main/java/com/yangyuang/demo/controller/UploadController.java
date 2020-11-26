package com.yangyuang.demo.controller;

import com.yangyuang.demo.util.FTPUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class UploadController {

    @GetMapping("/test1")
    public String test1() {
        return "no";
    }

    @GetMapping("/test")
    public String test(MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();
            FTPUtil.sshSftp(bytes, file.getOriginalFilename());
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "no";
    }
}
