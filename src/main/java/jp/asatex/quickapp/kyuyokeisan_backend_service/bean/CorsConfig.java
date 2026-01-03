package jp.asatex.quickapp.kyuyokeisan_backend_service.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * CORS（跨域资源共享）配置类
 * 允许所有源访问所有端点
 */
@Configuration
public class CorsConfig {

    /**
     * 配置 CORS WebFilter
     * 允许所有源、所有方法、所有请求头
     *
     * @return CorsWebFilter CORS 过滤器
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        // 允许所有源访问
        corsConfiguration.addAllowedOriginPattern("*");
        
        // 允许所有 HTTP 方法
        corsConfiguration.addAllowedMethod("*");
        
        // 允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        
        // 允许发送凭证（cookies、authorization headers 等）
        corsConfiguration.setAllowCredentials(true);
        
        // 预检请求的缓存时间（秒）
        corsConfiguration.setMaxAge(3600L);
        
        // 配置所有路径都应用此 CORS 策略
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        
        return new CorsWebFilter(source);
    }
}

