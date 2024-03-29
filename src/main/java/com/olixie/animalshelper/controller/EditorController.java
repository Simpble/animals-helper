package com.olixie.animalshelper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/editor")
@Slf4j
public class EditorController {

    @Value("${props.editor-folder}")
    private String editor_path;

    @PostMapping("/saveContent")
    public ResponseEntity<Boolean> saveContent(@RequestBody String content) {
        log.info(content);
        return null;
    }


    @PostMapping("/upload")
    /**
     * @param file 接受到的图片
     * @return 返回的为图片保存地址
     * */
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            //获取文件名
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                return null;
            }
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //重新生成文件名
            fileName = UUID.randomUUID() + suffixName;
            File newPicture = new File(editor_path + fileName);
            if (!newPicture.exists()) {
                new File(editor_path).mkdirs();
                try {
                    newPicture.createNewFile();
                    file.transferTo(newPicture);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
