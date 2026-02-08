package com.devsu.ing.deiberv.ms.cliente.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class OpenApiProperties {

  private Project project;

  @Getter
  @Setter
  public static class Project {
    private String name;
    private String description;
    private String version;
    private String contactName;
    private String contactEmail;
  }

}
