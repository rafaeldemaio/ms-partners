package delivery.ze.partners.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Setter
@Configuration
@ConfigurationProperties(prefix = "ze.openapi")
public class OpenApiConfiguration {

    @NotNull
    private String title;
    @NotNull
    private String desc;
    @NotNull
    private String version;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                    .title(title)
                    .description(desc)
                    .version(version)
                 );
    }
}
