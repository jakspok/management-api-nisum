package com.clients.api.rest.management.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import java.time.LocalDate;

@Tag(name = "Represents a client response with personal details and associated phones.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
public class ClientResponse {

    private long id;

    private String name;

    private LocalDate created;

    private LocalDate modified;

    private LocalDate lastLogin;

    private String  token;

    private Boolean isActive;

    private String message;


}
