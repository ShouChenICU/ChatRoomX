package com.mystery.chat.configures;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * @author shouchen
 * @date 2022/12/1
 */
@Configuration
public class FastJsonConfigure {

    @Bean
    public HttpMessageConverter<?> httpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(StandardCharsets.UTF_8);
        converter.setFastJsonConfig(config);
        return converter;
    }
}
