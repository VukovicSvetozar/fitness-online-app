package beans;

import java.util.Set;

import dao.SpecifikacijaDAO;
import dto.Specifikacija;

public class SpecifikacijaBean {

	private Specifikacija specifikacija = new Specifikacija();

	public Specifikacija getSpecifikacija() {
		return specifikacija;
	}

	public void setSpecifikacija(Specifikacija specifikacija) {
		this.specifikacija = specifikacija;
	}

	public Set<Specifikacija> vratiSveSpecifikacijeKategorije() {
		return SpecifikacijaDAO.odaberiSveIzKategorije(specifikacija);
	}

	public Specifikacija vratiSpecifikacijuPoId(String idSpecifikacija) {
		Specifikacija specifikacija = null;
		try {
			Integer id = Integer.parseInt(idSpecifikacija);
			specifikacija = SpecifikacijaDAO.vratiPoId(id);
		} catch (NumberFormatException e) {
			specifikacija = null;
		}
		return specifikacija;
	}

	public boolean dodajSpecifikaciju() {
		return SpecifikacijaDAO.dodaj(specifikacija);
	}

	public boolean obrisiSpecifikaciju() {
		return SpecifikacijaDAO.obrisi(specifikacija);
	}

}
