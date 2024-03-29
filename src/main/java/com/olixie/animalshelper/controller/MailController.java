package com.olixie.animalshelper.controller;

import com.olixie.animalshelper.service.MailService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mail")
@RestController
public class MailController {

    @Resource
    private MailService mailService;

    @GetMapping("/{email}")
    public ResponseEntity<String> sendMailAndGenerateCode(@PathVariable("email") String email){
        return ResponseEntity.ok(mailService.sendMailAndGenerateCode(email));
    }
}
