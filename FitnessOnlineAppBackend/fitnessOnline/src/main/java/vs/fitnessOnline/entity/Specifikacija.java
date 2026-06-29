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
@Table(name = "specifikacija",
uniqueConstraints = {@UniqueConstraint(columnNames = {"naziv", "kategorija_id"})})
public class Specifikacija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@EqualsAndHashCode.Exclude
	@Column(nullable = false)
	private String naziv;

	@EqualsAndHashCode.Exclude
	@ManyToOne(optional = false)
	@JoinColumn(name = "kategorija_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Kategorija kategorija;

}