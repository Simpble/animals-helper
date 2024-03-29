package com.olixie.animalshelper.config;

import com.olixie.animalshelper.jwt.jwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${props.upload-folder}")
    private String UPLOAD_FOLDER;

    @Value("${props.editor-folder}")
    private String EDITOR_FOLDER;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/editor/**")
                .excludePathPatterns("/pet")
                .excludePathPatterns("/new/getNews")   //以上两条为首页需要进行的操作
                .excludePathPatterns("/mail/**") //验证码相关的请求
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/password-login")
                .excludePathPatterns("/user/email-login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/editor/**").addResourceLocations("file:" + EDITOR_FOLDER);
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + UPLOAD_FOLDER);
    }
}
