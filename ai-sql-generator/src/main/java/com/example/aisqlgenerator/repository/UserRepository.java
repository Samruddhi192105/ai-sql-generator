package com.example.aisqlgenerator.repository;

import com.example.aisqlgenerator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(
            String username,
            String email,
            String password) {

        String sql = """
                INSERT INTO users
                (username, email, password)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                username,
                email,
                password
        );
    }

    public Optional<User> findByEmail(String email) {

        String sql = """
                SELECT id,
                       username,
                       email,
                       password
                FROM users
                WHERE email = ?
                """;

        return jdbcTemplate.query(
                sql,
                rs -> {
                    if (rs.next()) {
                        return Optional.of(
                                new User(
                                        rs.getLong("id"),
                                        rs.getString("username"),
                                        rs.getString("email"),
                                        rs.getString("password")
                                )
                        );
                    }

                    return Optional.empty();
                },
                email
        );
    }
}