package uz.mutalov.onlinemarket.config;

import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
                .addResourceLocations("classpath:/static/index.html")
                .setCachePeriod(0);

        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new PushStateResourceResolver());
    }

    private static class PushStateResourceResolver implements ResourceResolver {
        private final Resource index = new ClassPathResource("static/index.html");
        private final List<String> handledExtension = Arrays.asList("html", "js", "json", "css", "png", "svg", "eot", "ttf", "ico");
        private final List<String> ignoredPaths = Collections.singletonList("api");

        @Override
        public Resource resolveResource(HttpServletRequest request, @NonNull String requestPath, @NonNull List<? extends Resource> locations, @NonNull ResourceResolverChain chain) {
            return resolve(requestPath, locations);
        }

        @Override
        public String resolveUrlPath(@NonNull String resourcePath, @NonNull List<? extends Resource> locations, @NonNull ResourceResolverChain chain) {
            Resource resolve = resolve(resourcePath, locations);
            if (resolve == null) {
                return null;
            }
            try {
                return resolve.getURL().toString();
            } catch (IOException e) {
                return resolve.getFilename();
            }
        }

        private Resource resolve(String requestPath, List<? extends Resource> locations) {
            if (isIgnored(requestPath)) {
                return null;
            }
            if (isHandled(requestPath)) {
                return locations.stream().map(location -> createRelative(location, requestPath))
                        .filter(resource -> resource != null && resource.exists())
                        .findFirst()
                        .orElseThrow();
            }
            return index;
        }

        private Resource createRelative(Resource resource, String requestPath) {
            try {
                return resource.createRelative(requestPath);
            } catch (IOException e) {
                return null;
            }
        }

        private boolean isIgnored(String path) {
            return ignoredPaths.contains(path);
        }

        private boolean isHandled(String path) {
            String extension = FilenameUtils.getExtension(path);
            return handledExtension.stream().anyMatch(ext -> ext.equals(extension));
        }
    }

}
