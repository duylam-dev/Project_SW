package it3180.team19.walletapi.Configuration;

import it3180.team19.walletapi.Util.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("no_check_url")
    private String[] noCheckURL;

    @Bean
    LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor()).excludePathPatterns(noCheckURL);
    }
}
