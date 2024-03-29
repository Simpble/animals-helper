package com.olixie.animalshelper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Value("${props.upload-folder}")
    private String UPLOAD_FOLDER;

    @PostMapping("/upload")
    /*关于头像的优化：根据用户单独存储头像集合，每一个用户拥有独立的文件夹。*/
    public ResponseEntity<String> upload(MultipartFile photo) {
        //获取文件名
        String fileName = photo.getOriginalFilename();
        log.info("filename:{}", fileName);
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID() + suffixName;
        //指定本地文件夹存储图片，写到需要保存的目录下
        try {
            //将图片保存到static文件夹里
            photo.transferTo(new File(UPLOAD_FOLDER + fileName));
            //返回提示信息
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

//    @PostMapping("/upload")
//    public Result upload(@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request) {
//        if (file == null) {
//            return ResultUtil.error(0, "请选择要上传的图片");
//        }
//        if (file.getSize() > 1024 * 1024 * 10) {
//            return ResultUtil.error(0, "文件大小不能大于10M");
//        }
//        //获取文件后缀
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
//        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
//            return ResultUtil.error(0, "请选择jpg,jpeg,gif,png格式的图片");
//        }
//        String savePath = UPLOAD_FOLDER;
//        File savePathFile = new File(savePath);
//        if (!savePathFile.exists()) {
//            //若不存在该目录，则创建目录
//            savePathFile.mkdir();
//        }
//        //通过UUID生成唯一文件名
//        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
//        try {
//            //将文件保存指定目录
//            file.transferTo(new File(savePath + filename));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResultUtil.error(0, "保存文件异常");
//        }
//        //返回文件名称
//        return ResultUtil.success(ResultEnum.SUCCESS, filename, request);
//    }

}
