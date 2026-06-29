package beans;

import java.util.*;

import dao.SavjetnikDAO;
import dto.Savjetnik;

public class SavjetnikBean {

	private Savjetnik savjetnik = new Savjetnik();

	public Savjetnik getSavjetnik() {
		return savjetnik;
	}

	public void setSavjetnik(Savjetnik savjetnik) {
		this.savjetnik = savjetnik;
	}

	public Set<Savjetnik> vratiSveSavjetnike() {
		return SavjetnikDAO.odaberiSve();
	}

	public Savjetnik vratiSavjetnikaPoId(String idSavjetnik) {
		Savjetnik savjetnik = null;
		try {
			Integer id = Integer.parseInt(idSavjetnik);
			savjetnik = SavjetnikDAO.vratiPoId(id);
		} catch (NumberFormatException e) {
			savjetnik = null;
		}
		return savjetnik;
	}

	public boolean provjeriDostupnostKorisnickogImena(String korisnickoIme) {
		return SavjetnikDAO.provjeriDostupnostKorisnickogImena(korisnickoIme);
	}

	public boolean dodajSavjetnika() {
		return SavjetnikDAO.dodaj(savjetnik);
	}

	public boolean urediSavjetnika() {
		return SavjetnikDAO.uredi(savjetnik);
	}

	public boolean obrisiSavjetnika() {
		return SavjetnikDAO.obrisi(savjetnik);
	}

}
