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
@Table(name = "fitnes_program_komentar")
public class FitnesProgramKomentar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String komentar;

	@Column(nullable = false)
	private String vrijeme;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "korisnik_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Korisnik korisnik;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "fitnes_program_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private FitnesProgram fitnesProgram;

}
