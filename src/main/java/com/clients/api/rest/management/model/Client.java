package com.clients.api.rest.management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Represents a client with personal details and associated phones.")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = "CLIENT_REG_TBL")
public class Client implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", nullable = false)
	@NotNull(message = "Name is required")
	@Size(max = 100, message = "Name cannot exceed 100 characters")
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	@NotNull(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@Column(name = "password", nullable = false)
	@NotNull(message = "Password is required")
	private String password;

	@Column(name = "token")
	private String token;

	@JsonManagedReference
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Phones> phones = new ArrayList<>();

	@Column(name = "dataAdded")
	private LocalDate dataAdded;

	@Column(name = "startDate")
	private LocalDate startDate;

	@Column(name = "endDate")
	private LocalDate endDate;

}
