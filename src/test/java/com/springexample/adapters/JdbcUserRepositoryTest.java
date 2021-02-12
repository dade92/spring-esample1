package com.springexample.adapters;

import com.springexample.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JdbcUserRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserRepository jdbcUserRepository;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .build()
        );
        jdbcUserRepository = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    public void fetch() {
        insertUser(666L, "Davide", "XXX");

        Optional<User> user = jdbcUserRepository.fetch(666L);

        assertThat(user, is(Optional.of(new User("Davide", "XXX"))));
    }

    @Test
    public void addUser() {
        jdbcUserRepository.addUser(new User("Davide", "XXX"));

        User user = findUser("Davide");

        assertThat(Optional.of(user), is(Optional.of(new User("Davide", "XXX"))));
    }

    private User findUser(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE USERNAME=?",
            Collections.singletonList(username).toArray(),
            (resultSet, i) -> new User(
                resultSet.getString("USERNAME"),
                resultSet.getString("PASSWORD")
            )
        );
    }

    private void insertUser(long id, String username, String password) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?)", id, username, password);
    }
}