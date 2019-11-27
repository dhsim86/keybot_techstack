package com.dongho.keybot.config;

import com.dongho.keybot.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Validated
@TestConfiguration
@ConfigurationProperties(prefix = "fixtures")
public class FixtureConfig {

    @NotEmpty
    private String test;

    @Valid
    private List<User> userList;

}

