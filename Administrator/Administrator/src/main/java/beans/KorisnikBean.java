package beans;

import java.util.*;

import dao.KorisnikDAO;
import dto.Korisnik;

public class KorisnikBean {

	private Korisnik korisnik = new Korisnik();

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Set<Korisnik> vratiSveKorisnike() {
		return KorisnikDAO.odaberiSve();
	}

	public Korisnik vratiKorisnikaPoId(String idKorisnik) {
		Korisnik korisnik = null;
		try {
			Integer id = Integer.parseInt(idKorisnik);
			korisnik = KorisnikDAO.vratiPoId(id);
		} catch (NumberFormatException e) {
			korisnik = null;
		}
		return korisnik;
	}

	public boolean provjeriDostupnostKorisnickogImena(String korisnickoIme) {
		return KorisnikDAO.provjeriDostupnostKorisnickogImena(korisnickoIme);
	}

	public boolean provjeriDostupnostEmaila(String email) {
		return KorisnikDAO.provjeriDostupnostEmaila(email);
	}

	public boolean dodajKorisnika() {
		return KorisnikDAO.dodaj(korisnik);
	}

	public boolean urediKorisnika() {
		return KorisnikDAO.uredi(korisnik);
	}

	public boolean obrisiKorisnika() {
		return KorisnikDAO.obrisi(korisnik);
	}

}
