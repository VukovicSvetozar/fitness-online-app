package vs.fitnessOnline.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ucestvovanje")
public class Ucestvovanje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, name = "vrijeme")
	private String vrijeme;

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@MapsId
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "placanje_id")
	private Placanje placanje;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "fitnes_program_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private FitnesProgram fitnesProgram;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "prodavac_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik prodavac;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "kupac_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik kupac;

}
