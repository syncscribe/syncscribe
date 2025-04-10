package io.syncscribe.emailservice.datasource.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmailRepository extends CrudRepository<EmailModel, String> {
}
