package com.clients.api.rest.management.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table (name = "USER_TOKENS")
@Tag(name = "Represents a client with personal details and associated phones.")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserToken {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

}

