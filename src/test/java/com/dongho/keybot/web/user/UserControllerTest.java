package com.dongho.keybot.web.user;


import com.dongho.keybot.application.user.UserApplicationService;
import com.dongho.keybot.config.FixtureConfig;
import com.dongho.keybot.domain.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(UserController.class)
@Import(FixtureConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private FixtureConfig fixtureConfig;

    @MockBean
    private UserApplicationService userApplicationService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getUsersTest() {
        WebTestClient.ResponseSpec spec;

        given: {
            Mockito.when(userApplicationService.findAllUser()).thenReturn(Flux.fromIterable(fixtureConfig.getUserList()));
        }

        when: {
            spec = webTestClient.get().uri(uriBuilder -> uriBuilder.path("/users").build()).exchange();
        }

        then: {
            spec.expectStatus().isOk();
            spec.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
            spec.expectBodyList(User.class).hasSize(2)
                    .contains(fixtureConfig.getUserList().toArray(new User[]{}));
        }
    }

}

