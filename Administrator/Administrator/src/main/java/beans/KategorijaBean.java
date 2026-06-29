package beans;

import java.util.*;

import dao.KategorijaDAO;
import dto.Kategorija;

public class KategorijaBean {

	private Kategorija kategorija = new Kategorija();

	public Kategorija getKategorija() {
		return kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public Set<Kategorija> vratiSveKategorije() {
		return KategorijaDAO.odaberiSve();
	}

	public Kategorija vratiKategorijuPoId(String idKategorija) {
		Kategorija kategorija = null;
		try {
			Integer id = Integer.parseInt(idKategorija);
			kategorija = KategorijaDAO.vratiPoId(id);
		} catch (NumberFormatException e) {
			kategorija = null;
		}
		return kategorija;
	}

	public boolean provjeriNazivKategorije(String nazivKategorije) {
		return KategorijaDAO.provjeriNaziv(nazivKategorije);
	}

	public boolean dodajKategoriju() {
		return KategorijaDAO.dodaj(kategorija);
	}

	public boolean urediKategoriju() {
		return KategorijaDAO.uredi(kategorija);
	}

	public boolean obrisiKategoriju() {
		return KategorijaDAO.obrisi(kategorija);
	}

}
