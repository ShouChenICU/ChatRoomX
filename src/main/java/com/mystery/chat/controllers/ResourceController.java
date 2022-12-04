package com.mystery.chat.controllers;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;

/**
 * @author shouchen
 * @date 2022/12/4
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping
    public void getResource(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        File file=new File("login-bg.jpg");
//        InputStream inputStream = new FileInputStream(file);
//        response.setContentLength(inputStream.available());
//        inputStream.close();
        System.out.println(file.getCanonicalPath());

    }
}
