package com.springexample.adapters;

import com.springexample.domain.User;
import com.springexample.domain.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

public class RestUserRepository implements UserRepository {

    private String basePath;
    private RestOperations restOperations;

    public RestUserRepository(
        String basePath,
        RestOperations restOperations
    ) {
        this.basePath = basePath;
        this.restOperations = restOperations;
    }

    @Override
    public Optional<User> fetch(long userId) {
        ResponseEntity<RestUserResponse> response =
            restOperations.getForEntity(basePath + "/user/" + userId, RestUserResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.of(adaptUser(response.getBody()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> addUser(User user) {
        ResponseEntity<String> response = restOperations.postForEntity(
            basePath + "/addUser",
            new RestUserRequest(user.getName(), user.getPassword()),
            String.class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.of(1);
        } else {
            return Optional.empty();
        }
    }

    private User adaptUser(RestUserResponse response) {
        return new User(response.getUser().getUsername(), "");
    }
}
