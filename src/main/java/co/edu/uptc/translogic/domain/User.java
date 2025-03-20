package co.edu.uptc.translogic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "id")
public class User extends Person {

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleName rol;

    // Constructor vacío necesario para JPA
    public User() {}

    // Constructor con parámetros
    public User(String username, String passwordHash, RoleName rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public RoleName getRol() {
        return rol;
    }

    public void setRol(RoleName rol) {
        this.rol = rol;
    }

    public enum RoleName {
        ADMIN,
        DISPATCHER,
        DRIVER
    }
}
