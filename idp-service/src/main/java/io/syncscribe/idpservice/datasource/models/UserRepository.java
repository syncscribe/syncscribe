package io.syncscribe.idpservice.datasource.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> getUserByUsername(String username);
    Optional<UserModel> getUserByEmail(String email);
}
