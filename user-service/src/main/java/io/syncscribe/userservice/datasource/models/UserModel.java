package io.syncscribe.userservice.datasource.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    private String guid;
    private String fullName;
    private String email;
    private String avatarPath;
}
