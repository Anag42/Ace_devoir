package com.cigma.ace.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "password_resets")
@Getter
@Setter
public class PasswordReset {
    @Id
    @Email
    private String email;
    @NotNull
    private String token;
    @CreationTimestamp
    private Date createdAt;
}
