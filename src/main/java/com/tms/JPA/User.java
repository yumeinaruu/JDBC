package com.tms.JPA;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "userSeqGen")
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "user_password", nullable = false)
    private String userPassword;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;
    @Column(name = "changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changed;
    @Column(name = "age")
    private Integer age;
}
