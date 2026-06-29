package dto;

import java.io.Serializable;
import java.util.Objects;

public class Kategorija implements Serializable {

	private static final long serialVersionUID = 1601319904834472559L;

	private Integer id;

	private String naziv;

	public Kategorija() {
		super();
	}

	public Kategorija(String naziv) {
		super();
		this.naziv = naziv;
	}

	public Kategorija(Integer id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kategorija other = (Kategorija) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Kategorija [id=" + id + ", naziv=" + naziv + "]";
	}

}