package vs.fitnessOnline.entity;

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
@Table(name = "masa_korisnika")
public class MasaKorisnika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private double masa;

	@Column(name = "datum", nullable = false)
	private String datum;

	@ManyToOne
	@JoinColumn(name = "korisnik_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik korisnik;

}
