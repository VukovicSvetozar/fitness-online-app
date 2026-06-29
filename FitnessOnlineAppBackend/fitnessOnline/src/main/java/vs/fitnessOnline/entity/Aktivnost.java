package vs.fitnessOnline.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "aktivnost")
public class Aktivnost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "naziv", nullable = false)
	private String naziv;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "kategorija_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Kategorija kategorija;

	@Column(name = "intezitet", nullable = false)
	private String intezitet;

	@Column(name = "trajanje", nullable = false)
	private int trajanje;

	@Column(name = "broj_ponavljanja", nullable = false)
	private int brojPonavljanja;

	@Column(name = "datum", nullable = false)
	private String datum;
	
	@Column(name = "zavrseno", nullable = false)
	private boolean zavrseno;

	@ManyToOne
	@JoinColumn(name = "korisnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik korisnik;

}
