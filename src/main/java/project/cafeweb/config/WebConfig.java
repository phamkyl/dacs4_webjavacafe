package project.cafeweb.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        addStaticResourceHandler(registry, "/css/**", "classpath:/static/css/");
        addStaticResourceHandler(registry, "/js/**", "classpath:/static/js/");
        addStaticResourceHandler(registry, "/img/**", "classpath:/static/img/");
        addStaticResourceHandler(registry, "/vendor/**", "classpath:/static/vendor/");
        addStaticResourceHandler(registry, "/assets/**", "classpath:/static/assets/");
    }

    private void addStaticResourceHandler(ResourceHandlerRegistry registry, String pattern, String location) {
        registry.addResourceHandler(pattern)
                .addResourceLocations(location)
                .setCacheControl(CacheControl.noCache())
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addResolver(new PathResourceResolver());
    }
	    
	
}
