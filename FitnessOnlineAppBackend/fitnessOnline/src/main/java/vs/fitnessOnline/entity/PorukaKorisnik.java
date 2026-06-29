package vs.fitnessOnline.entity;

import org.hibernate.annotations.*;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "poruka_korisnik")
public class PorukaKorisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String tekstPoruke;

	@Column(name = "vrijeme_poruke", nullable = false)
	private String vrijemePoruke;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "posiljalac_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik posiljalac;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "primalac_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik primalac;

}