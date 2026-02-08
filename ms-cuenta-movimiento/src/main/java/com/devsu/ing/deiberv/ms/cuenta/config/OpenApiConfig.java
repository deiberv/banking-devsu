package com.devsu.ing.deiberv.ms.cuenta.config;


import com.devsu.ing.deiberv.ms.cuenta.config.properties.OpenApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

  private final OpenApiProperties openApiProperties;

  public OpenAPI customOpenAPI() {

    return new OpenAPI()
      .info(getApiInfo());
  }

  @Bean
  public ApiResponse apiResponse() {
    return new ApiResponse();
  }

  private Info getApiInfo() {
    OpenApiProperties.Project project = openApiProperties.getProject();
    return new Info()
      .title(project.getName())
      .description(project.getDescription())
      .version(project.getVersion())
      .contact(new io.swagger.v3.oas.models.info.Contact()
        .name(project.getContactName())
        .email(project.getContactEmail()));
  }

}
