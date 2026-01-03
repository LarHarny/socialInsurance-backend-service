package jp.asatex.quickapp.kyuyokeisan_backend_service.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 验证配置类
 * 用于启用 Spring Validation 功能
 */
@Configuration
public class ValidationConfig {

    /**
     * 创建验证器工厂 Bean
     * 用于支持 @Valid、@NotNull、@Min、@Max 等验证注解
     *
     * @return LocalValidatorFactoryBean 验证器工厂
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}

