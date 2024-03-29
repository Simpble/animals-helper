package com.olixie.animalshelper.service.impl;

import com.olixie.animalshelper.service.MailService;
import com.olixie.animalshelper.util.RedisUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public String sendMailAndGenerateCode(String mailAddress) {
        // 邮箱正则
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mailAddress);
        boolean isMatched = matcher.matches();
        // null或者不是邮箱
        if (StringUtils.isBlank(mailAddress) || !isMatched) {
            return null;
        }
        // 生成随机的六位验证码
        String code = generateCode(6);
        // 将code存入到Redis中,存储时限为五分钟
        redisUtil.set(mailAddress, code, 5L, TimeUnit.MINUTES);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("1748275908@qq.com");  // 你自己的邮箱地址
            message.setTo(mailAddress); //接受者的邮箱
            message.setSubject("欢迎注册【小宁宠】动物保护平台");
            message.setText("您的注册验证码是：" + "【" + code + "】, " + "验证码仅在五分钟内有效！");
            this.javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return code;
    }


    public static String generateCode(int len) {
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, len);
    }
}
