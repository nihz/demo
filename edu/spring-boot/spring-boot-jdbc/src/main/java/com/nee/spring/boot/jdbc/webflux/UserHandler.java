package com.nee.spring.boot.jdbc.webflux;

import com.nee.spring.boot.jdbc.domain.User;
import com.nee.spring.boot.jdbc.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Mono<ServerResponse> save(ServerRequest serverRequest) {

        Mono<User> userMono = serverRequest.bodyToMono(User.class);

        Mono<Boolean> booleanMono = userMono.map(userRepository::save);

        return ServerResponse.ok().body(booleanMono, Boolean.class);

    }
}
