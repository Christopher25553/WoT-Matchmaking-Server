package org.cteichert.server.repository;

import org.cteichert.server.bean.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> getByUserName(String userName);
}
