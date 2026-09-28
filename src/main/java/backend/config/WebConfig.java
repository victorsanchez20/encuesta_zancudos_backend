package backend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String API_PATTERN = "/api/**";

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${app.cors.allowed-methods}")
    private String[] allowedMethods;

    @Value("${app.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${app.cors.exposed-headers:}")
    private String[] exposedHeaders;

    @Value("${app.cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Value("${app.cors.max-age:3600}")
    private long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration mapping = registry.addMapping(API_PATTERN)
                .allowedMethods(allowedMethods)
                .allowedHeaders(allowedHeaders)
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);

        List<String> origins = Arrays.stream(allowedOrigins)
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toList();

        if (origins.contains("*")) {
            mapping.allowedOriginPatterns("*");
        } else {
            mapping.allowedOrigins(origins.toArray(String[]::new));
        }

        String[] exposed = Arrays.stream(exposedHeaders)
                .map(String::trim)
                .filter(header -> !header.isEmpty())
                .toArray(String[]::new);

        if (exposed.length > 0) {
            mapping.exposedHeaders(exposed);
        }
    }
}
