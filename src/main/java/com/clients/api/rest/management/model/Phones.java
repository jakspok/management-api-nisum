package com.clients.api.rest.management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;


@Tag(name = "Phones")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = "PHONES_REG_TBL_PHO")
public class Phones implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Column(name = "number", nullable = false)
	private String number;

	@Column(name = "cityCode")
	private String cityCode;

	@Column(name = "countryCode")
	private String countryCode;
}
