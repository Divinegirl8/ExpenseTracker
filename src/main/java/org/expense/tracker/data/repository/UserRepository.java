package org.expense.tracker.data.repository;

import org.expense.tracker.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUserId(String userId);

    User findUserByUsername(String username);

}
