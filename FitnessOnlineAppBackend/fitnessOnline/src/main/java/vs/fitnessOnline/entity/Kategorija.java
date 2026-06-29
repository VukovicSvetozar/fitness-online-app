package vs.fitnessOnline.entity;

import java.util.*;

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
@Table(name = "kategorija")
public class Kategorija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String naziv;

	@OneToMany(mappedBy = "kategorija", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<Specifikacija> specifikacije = new HashSet<>();

}