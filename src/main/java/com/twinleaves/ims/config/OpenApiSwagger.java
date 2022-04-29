package com.twinleaves.ims.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(info = @Info(
            title = "Inventory Management System [IMS]"
            , version = "1.0"
            , description = "Inventory management system API endpoints"
            , termsOfService = "https://twinleaves.co/terms-of-service"
            , contact = @Contact(email = "info@twinleaves.co")
            , license = @License(url = "https://twinleaves.co/api-license")
        )
)
public class OpenApiSwagger {
}
