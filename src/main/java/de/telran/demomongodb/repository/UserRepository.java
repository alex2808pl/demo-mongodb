package de.telran.demomongodb.repository;

import de.telran.demomongodb.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
}