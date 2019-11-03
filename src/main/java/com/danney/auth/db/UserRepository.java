package com.danney.auth.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Query(value="{}", fields="{ password: 0 }")
    List<User> findAllQuery();

    @Query(value="{_id : ?0}", fields="{ password: 0 }")
    Optional<User> findByIdQuery(String id);
}
