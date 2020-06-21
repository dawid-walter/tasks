package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@Getter
public class AdminConfig {
    private String copyright = "Copyright " + LocalDate.now().getYear();

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;

    @Value("${company.name}")
    private String companyName;

    @Value("${company.address}")
    private String companyAddress;
}
