package com.clients.api.rest.management.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ApiModel(description = "Represents a client response with personal details and associated phones.")
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
