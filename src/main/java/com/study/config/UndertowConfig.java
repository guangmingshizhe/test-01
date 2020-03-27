package com.study.config;

import io.undertow.UndertowOptions;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 12073 on 2020/3/13.
 */
@Configuration
public class UndertowConfig {

    @Bean
    public UndertowEmbeddedServletContainerFactory undertowServletWebServerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        factory.addBuilderCustomizers((UndertowBuilderCustomizer) builder -> {
            builder.setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, true);
            builder.setWorkerThreads(200);
        });
        return factory;
    }
}
