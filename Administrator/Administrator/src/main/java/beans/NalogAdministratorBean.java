package beans;

import dao.NalogAdministratorDAO;
import dto.NalogAdministrator;

public class NalogAdministratorBean {

	private NalogAdministrator nalog;
	private boolean prijavljen = false;
	
	public NalogAdministrator getNalog() {
		return nalog;
	}

	public boolean prijava(String korisnickoIme, String lozinka) {
		if ((nalog = NalogAdministratorDAO.provjeraKredencijala(korisnickoIme, lozinka)) != null) {
			prijavljen = true;
			return true;
		}
		return false;
	}

	public void odjava() {
		nalog = null;
		prijavljen = false;
	}

	public boolean prijavljen() {
		return prijavljen;
	}
	
}
