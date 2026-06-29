package vs.fitnessOnline.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "korisnik")
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(nullable = false)
	private String ime;

	@Column(nullable = false)
	private String prezime;

	@Column(nullable = false)
	private String grad;

	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name = "broj_telefona", nullable = true)
	private String brojTelefona;

	@Column(name = "korisnicko_ime", nullable = false, unique = true)
	private String korisnickoIme;

	@Column(nullable = false)
	private String lozinka;

	@Column(name = "avatar_url", nullable = true)
	private String avatarUrl;

	@Column(name = "datum_pristupa", nullable = false)
	private String datumPristupa;

	@Column(nullable = false)
	private String kljuc;

	@Column(nullable = false)
	private String uloga;
	
	@Column(name = "pretplata", nullable = false)
	private Integer pretplata;

}
