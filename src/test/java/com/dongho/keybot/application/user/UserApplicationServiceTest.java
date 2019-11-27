package com.dongho.keybot.application.user;

import com.dongho.keybot.config.FixtureConfig;
import com.dongho.keybot.domain.user.User;
import com.dongho.keybot.domain.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(classes = {FixtureConfig.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserApplicationServiceTest {

    @Autowired
    private FixtureConfig fixtureConfig;

    @Autowired
    private UserApplicationService userApplicationService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findAllUserTest() {
        Flux<User> userFlux;

        given: {
            Mockito.when(userRepository.findAll()).thenReturn(fixtureConfig.getUserList());
        }

        when: {
            userFlux = userApplicationService.findAllUser();
        }

        then: {
            StepVerifier.create(userFlux)
                    .expectNext(fixtureConfig.getUserList().toArray(new User[]{}))
                    .verifyComplete();
        }

    }

}