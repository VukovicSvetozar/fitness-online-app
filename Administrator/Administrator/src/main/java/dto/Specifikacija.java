package dto;

import java.io.Serializable;
import java.util.Objects;

public class Specifikacija implements Serializable {

	private static final long serialVersionUID = 8508263703186762839L;

	private Integer id;

	private String naziv;

	private Integer idKategorija;

	public Specifikacija() {
		super();
	}

	public Specifikacija(Integer idKategorija) {
		super();
		this.idKategorija = idKategorija;
	}

	public Specifikacija(String naziv, Integer idKategorija) {
		super();
		this.naziv = naziv;
		this.idKategorija = idKategorija;
	}

	public Specifikacija(Integer id, String naziv, Integer idKategorija) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.idKategorija = idKategorija;
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

	public Integer getIdKategorija() {
		return idKategorija;
	}

	public void setIdKategorija(Integer idKategorija) {
		this.idKategorija = idKategorija;
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
		Specifikacija other = (Specifikacija) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Specifikacija [id=" + id + ", naziv=" + naziv + ", idKategorija=" + idKategorija + "]";
	}

}