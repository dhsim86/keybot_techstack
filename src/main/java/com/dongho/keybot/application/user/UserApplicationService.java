package com.dongho.keybot.application.user;


import com.dongho.keybot.domain.user.User;
import com.dongho.keybot.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class UserApplicationService {

    @Autowired
    private UserRepository userRepository;

    public Flux<User> findAllUser() {
        log.info("before findAll");

        Flux<User> userFlux = Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.elastic())
                .log();

        log.info("after findAll");

        return userFlux;
    }

}
