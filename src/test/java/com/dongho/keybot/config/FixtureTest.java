package com.dongho.keybot.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {FixtureConfig.class})
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class FixtureTest {

    @Autowired
    private FixtureConfig config;

    @Test
    public void testProperty() {

        given: {

        }

        when: {

        }

        then: {
            assertThat(config.getTest()).isEqualTo("test");
            assertThat(config.getUserList()).isNotNull().isNotEmpty().hasSize(2);
        }
    }

}
