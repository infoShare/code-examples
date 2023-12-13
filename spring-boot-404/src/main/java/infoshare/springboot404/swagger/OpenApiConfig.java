package infoshare.springboot404.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.emptyList;

@Configuration
public class OpenApiConfig {

    protected static final String API_KEY_REF = "APIKey";

    private final String headerName;

    public OpenApiConfig(@Value("${config.auth.headerName:key}") String headerName) {
        this.headerName = headerName;
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(API_KEY_REF, securityScheme()))
                .info(info())
                .addSecurityItem(new SecurityRequirement().addList(API_KEY_REF, emptyList()));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(headerName);
    }

    private Info info() {
        return new Info()
                .title("Swagger")
                .description("Endpoints")
                .version("0.0.1-SNAPSHOT");
    }
}