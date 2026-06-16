package co.ke.test.users.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerSecurity {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("USER PROFILE API")
                        .description("User Profile Management API for managing users and their multiple addresses.\n\n" +
                                     "Features:\n" +
                                     "- Create, Read, Update, and Delete users\n" +
                                     "- Manage multiple addresses per user\n" +
                                     "- Set primary address for each user\n" +
                                     "- Filter users by status and role\n" +
                                     "- Search users by name or email\n\n" +
                                     "This API supports the React frontend application for user and address management.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev@test.co.ke")
                                .url("http://localhost:8080/api"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}