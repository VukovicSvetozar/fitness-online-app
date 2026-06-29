package vs.fitnessOnline.entity;

import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "fitnes_program")
public class FitnesProgram {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "naziv", nullable = false)
	private String naziv;

	@Column(name = "opis", nullable = false)
	private String opis;

	@Column(nullable = false)
	private double cijena;

	@Column(name = "nivo_tezine", nullable = false)
	private String nivoTezine;

	@Column(name = "trajanje", nullable = false)
	private int trajanje;

	@Column(name = "vrijeme_objave", nullable = false)
	private String vrijemeObjave;

	@Column(name = "status", nullable = false)
	private String status;

	@ManyToOne(optional = false)
	@JoinColumn(name = "kategorija_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Kategorija kategorija;

	@ManyToOne
	@JoinColumn(name = "instruktor_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik instruktor;

	@Column(name = "lokacija_tip", nullable = false)
	private String tipLokacije;

	@Column(name = "lokacija_adresa", nullable = true)
	private String adresaLokacije;

	@Column(name = "lokacija_url", nullable = true)
	private String urlLokacije;
	
	@OneToMany(mappedBy = "fitnesProgram")
	private Set<Ucestvovanje> ucestvovanje;
	
}
