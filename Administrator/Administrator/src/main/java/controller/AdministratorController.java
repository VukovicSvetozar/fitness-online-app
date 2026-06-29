package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.Gson;

import beans.NalogAdministratorBean;
import beans.SavjetnikBean;
import beans.KategorijaBean;
import beans.KorisnikBean;
import beans.SpecifikacijaBean;
import dto.Kategorija;
import dto.Korisnik;
import dto.Savjetnik;
import dto.Specifikacija;
import dto.UlogaKorisnik;

@WebServlet("/Administrator")
public class AdministratorController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AdministratorController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String adresa = "/WEB-INF/pages/index.jsp";
		String akcija = request.getParameter("akcija");
		HttpSession sesija = request.getSession();
		boolean preusmjeri = true;

		NalogAdministratorBean nalogBean = (NalogAdministratorBean) sesija.getAttribute("nalogBean");
		SavjetnikBean savjetnikBean = (SavjetnikBean) sesija.getAttribute("savjetnikBean");
		KategorijaBean kategorijaBean = (KategorijaBean) sesija.getAttribute("kategorijaBean");
		KorisnikBean korisnikBean = (KorisnikBean) sesija.getAttribute("korisnikBean");
		SpecifikacijaBean specifikacijaBean = (SpecifikacijaBean) sesija.getAttribute("specifikacijaBean");

		if (nalogBean == null || !nalogBean.prijavljen())
			akcija = "prijava";
		else if (akcija == null)
			akcija = "greska";

		switch (akcija) {
		case "prijava":
			String korisnickoIme = request.getParameter("korisnickoIme");
			String lozinka = request.getParameter("lozinka");
			nalogBean = new NalogAdministratorBean();
			if (nalogBean.prijava(korisnickoIme, lozinka)) {
				sesija.setAttribute("nalogBean", nalogBean);
				kategorijaBean = new KategorijaBean();
				sesija.setAttribute("kategorijaBean", kategorijaBean);
				korisnikBean = new KorisnikBean();
				sesija.setAttribute("korisnikBean", korisnikBean);
				savjetnikBean = new SavjetnikBean();
				sesija.setAttribute("savjetnikBean", savjetnikBean);
				specifikacijaBean = new SpecifikacijaBean();
				sesija.setAttribute("specifikacijaBean", specifikacijaBean);
				adresa = "/WEB-INF/pages/kategorije.jsp";
				akcija = "kategorije";
			} else {
				adresa = "/WEB-INF/pages/index.jsp";
				akcija = "nijePrijavljen";
			}
			break;
		case "nijePronadjeno":
			adresa = "/WEB-INF/pages/kategorije.jsp";
			akcija = "kategorije";
			break;
		case "kategorije":
			adresa = "/WEB-INF/pages/kategorije.jsp";
			break;
		case "savjetnici":
			adresa = "/WEB-INF/pages/savjetnici.jsp";
			break;
		case "korisnici":
			adresa = "/WEB-INF/pages/korisnici.jsp";
			break;
		case "statistika":
			adresa = "/WEB-INF/pages/statistika.jsp";
			break;
		case "provjeraNazivaKategorije":
			preusmjeri = false;
			String nazivKategorije = request.getParameter("nazivKategorije");
			boolean dostupan = provjeraNazivaKategorije(nazivKategorije);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outProvjeri = response.getWriter();
			outProvjeri.print("{\"dostupno\": " + dostupan + "}");
			outProvjeri.flush();
			break;
		case "dodajKategoriju":
			preusmjeri = false;
			boolean dodato = false;
			if (request.getParameter("nazivKategorije") != null) {
				try {
					if (provjeraNazivaKategorije(request.getParameter("nazivKategorije"))) {
						Kategorija kategorija = new Kategorija(request.getParameter("nazivKategorije"));
						kategorijaBean.setKategorija(kategorija);
						if (kategorijaBean.dodajKategoriju()) {
							dodato = true;
						}
					}
				} catch (Exception e) {
					request.setAttribute("greška", "Došlo je do greške: " + e.getMessage());
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outDodaj = response.getWriter();
			outDodaj.print("{\"dodato\": " + dodato + "}");
			outDodaj.flush();
			break;
		case "urediKategoriju":
			preusmjeri = false;
			boolean promjenjeno = false;
			if (request.getParameter("idKategorija") != null && request.getParameter("nazivKategorije") != null) {
				try {
					if (provjeraNazivaKategorije(request.getParameter("nazivKategorije"))) {
						Kategorija kategorija = new Kategorija(Integer.parseInt(request.getParameter("idKategorija")),
								request.getParameter("nazivKategorije"));
						kategorijaBean.setKategorija(kategorija);
						if (kategorijaBean.urediKategoriju()) {
							promjenjeno = true;
						}
					}
				} catch (Exception e) {
					request.setAttribute("greška", "Došlo je do greške: " + e.getMessage());
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outPromjeni = response.getWriter();
			outPromjeni.print("{\"promjenjeno\": " + promjenjeno + "}");
			outPromjeni.flush();
			break;
		case "obrisiKategoriju":
			preusmjeri = false;
			boolean obrisano = false;
			if (request.getParameter("idKategorije") != null) {
				Kategorija kategorija = kategorijaBean.vratiKategorijuPoId(request.getParameter("idKategorije"));
				if (kategorija != null) {
					kategorijaBean.setKategorija(kategorija);
					obrisano = kategorijaBean.obrisiKategoriju();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outObrisi = response.getWriter();
			outObrisi.print("{\"obrisano\": " + obrisano + "}");
			outObrisi.flush();
			break;
		case "specifikacije":
			preusmjeri = false;
			Set<Specifikacija> specifikacijeSet = new HashSet<>();
			if (request.getParameter("idKategorija") != null) {
				try {
					Specifikacija specifikacija = new Specifikacija(
							Integer.parseInt(request.getParameter("idKategorija")));
					specifikacijaBean.setSpecifikacija(specifikacija);
					specifikacijeSet = specifikacijaBean.vratiSveSpecifikacijeKategorije();
				} catch (Exception e) {
					request.setAttribute("greška", "Doslo je do greške: " + e.getMessage());
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outSpecifikacije = response.getWriter();
			Gson gson = new Gson();
			String specifikacije = gson.toJson(specifikacijeSet);
			outSpecifikacije.print(specifikacije);
			outSpecifikacije.flush();
			break;
		case "dodajSpecifikaciju":
			preusmjeri = false;
			boolean dodataSpecifikacija = false;
			try {
				Specifikacija specifikacija = new Specifikacija(request.getParameter("nazivSpecifikacije"),
						Integer.parseInt(request.getParameter("idKategorija")));
				specifikacijaBean.setSpecifikacija(specifikacija);
				if (specifikacijaBean.dodajSpecifikaciju()) {
					dodataSpecifikacija = true;
				}
			} catch (Exception e) {
				request.setAttribute("greška", "Došlo je do greške: " + e.getMessage());
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outDodajSpecifikaciju = response.getWriter();
			outDodajSpecifikaciju.print("{\"dodato\": " + dodataSpecifikacija + "}");
			outDodajSpecifikaciju.flush();
			break;
		case "obrisiSpecifikaciju":
			preusmjeri = false;
			boolean obrisanaSpecifikacija = false;
			if (request.getParameter("idSpecifikacija") != null) {
				Specifikacija specifikacija = specifikacijaBean
						.vratiSpecifikacijuPoId(request.getParameter("idSpecifikacija"));
				if (specifikacija != null) {
					specifikacijaBean.setSpecifikacija(specifikacija);
					obrisanaSpecifikacija = specifikacijaBean.obrisiSpecifikaciju();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outObrisiSpecifikaciju = response.getWriter();
			outObrisiSpecifikaciju.print("{\"obrisanaSpecifikacija\": " + obrisanaSpecifikacija + "}");
			outObrisiSpecifikaciju.flush();
			break;
		case "provjeraKorisnickogImena":
			preusmjeri = false;
			String korisnickoImeProvjera = request.getParameter("korisnickoIme");
			boolean dostupnoKorisnickoIme = provjeraKorisnickogImena(korisnickoImeProvjera);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outProvjeriIme = response.getWriter();
			outProvjeriIme.print("{\"dostupno\": " + dostupnoKorisnickoIme + "}");
			outProvjeriIme.flush();
			break;
		case "provjeraEmaila":
			preusmjeri = false;
			String emailProvjera = request.getParameter("email");
			boolean dostupanEmail = provjeraEmaila(emailProvjera);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outProvjeriEmail = response.getWriter();
			outProvjeriEmail.print("{\"dostupno\": " + dostupanEmail + "}");
			outProvjeriEmail.flush();
			break;
		case "dodajKorisnika":
			preusmjeri = false;
			boolean dodatKorisnik = false;
			try {
				if (provjeraNazivaKategorije(request.getParameter("korisnickoIme"))
						&& provjeraEmaila(request.getParameter("emailKorisnika"))) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String lozinkaKorisnika = request.getParameter("lozinkaKorisnika");
					String kodovanaLozinka = passwordEncoder.encode(lozinkaKorisnika);
					String datumPristupaKorisnika = LocalDate.now().toString();
					String kljucKorisnika = kreirajKljuc();
					String avatarKorisnika = "assets/avatari/0.png";
					Integer pretplata = 0;
					Korisnik korisnik = new Korisnik(request.getParameter("imeKorisnika"),
							request.getParameter("prezimeKorisnika"), request.getParameter("gradKorisnika"),
							request.getParameter("emailKorisnika"), request.getParameter("brojTelefonaKorisnika"),
							request.getParameter("korisnickoIme"), kodovanaLozinka, avatarKorisnika,
							datumPristupaKorisnika, kljucKorisnika, UlogaKorisnik.VERIFIKOVANI.name(), pretplata);
					korisnikBean.setKorisnik(korisnik);
					if (korisnikBean.dodajKorisnika()) {
						dodatKorisnik = true;
					}
				}
			} catch (Exception e) {
				request.setAttribute("greška", "Došlo je do greške: " + e.getMessage());
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outDodajKorisnika = response.getWriter();
			outDodajKorisnika.print("{\"dodato\": " + dodatKorisnik + "}");
			outDodajKorisnika.flush();
			break;
		case "urediKorisnika":
			preusmjeri = false;
			boolean uredjenKorisnik = false;
			try {
				Korisnik korisnik = korisnikBean.vratiKorisnikaPoId(request.getParameter("idKorisnik"));
				String lozinkaKorisnikaUredi = korisnik.getLozinka();
				if (!"".equals(request.getParameter("lozinkaKorisnika"))) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String lozinkaKorisnika = request.getParameter("lozinkaKorisnika");
					lozinkaKorisnikaUredi = passwordEncoder.encode(lozinkaKorisnika);
				}
				Korisnik azuriraniKorisnik = new Korisnik(Integer.parseInt(request.getParameter("idKorisnik")),
						request.getParameter("imeKorisnika"), request.getParameter("prezimeKorisnika"),
						request.getParameter("gradKorisnika"), request.getParameter("emailKorisnika"),
						request.getParameter("brojTelefonaKorisnika"), request.getParameter("korisnickoIme"),
						lozinkaKorisnikaUredi, korisnik.getAvatarUrl(), korisnik.getDatumPristupa(),
						korisnik.getKljuc(), korisnik.getUloga(), korisnik.getPretplata());
				korisnikBean.setKorisnik(azuriraniKorisnik);
				if (korisnikBean.urediKorisnika()) {
					uredjenKorisnik = true;
				}
			} catch (Exception e) {
				request.setAttribute("greška", "Došlo je do greške: " + e.getMessage());
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outUrediKorisnika = response.getWriter();
			outUrediKorisnika.print("{\"promjenjeno\": " + uredjenKorisnik + "}");
			outUrediKorisnika.flush();
			break;
		case "prikaziKorisnika":
			preusmjeri = false;
			Korisnik korisnikPrikaz = korisnikBean.vratiKorisnikaPoId(request.getParameter("idKorisnik"));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outPrikaziKorisnika = response.getWriter();
			Gson gsonKorisnik = new Gson();
			String korisnikPrikazS = gsonKorisnik.toJson(korisnikPrikaz);
			outPrikaziKorisnika.print(korisnikPrikazS);
			outPrikaziKorisnika.flush();
			break;
		case "obrisiKorisnika":
			preusmjeri = false;
			boolean obrisanKorisnik = false;
			if (request.getParameter("idKorisnik") != null) {
				Korisnik korisnik = korisnikBean.vratiKorisnikaPoId(request.getParameter("idKorisnik"));
				if (korisnik != null) {
					korisnikBean.setKorisnik(korisnik);
					obrisanKorisnik = korisnikBean.obrisiKorisnika();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outObrisiKorisnika = response.getWriter();
			outObrisiKorisnika.print("{\"obrisano\": " + obrisanKorisnik + "}");
			outObrisiKorisnika.flush();
			break;

		case "dodajSavjetnika":
			preusmjeri = false;
			boolean dodatSavjetnik = false;
			try {
				if (provjeraKorisnickogImenaSavjetnika(request.getParameter("korisnickoImeSavjetnika"))) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String lozinkaSavjetnika = request.getParameter("lozinkaSavjetnika");
					String kodovanaLozinka = passwordEncoder.encode(lozinkaSavjetnika);
					Savjetnik savjetnik = new Savjetnik(request.getParameter("imeSavjetnika"),
							request.getParameter("prezimeSavjetnika"), request.getParameter("korisnickoImeSavjetnika"),
							kodovanaLozinka);
					savjetnikBean.setSavjetnik(savjetnik);
					if (savjetnikBean.dodajSavjetnika()) {
						dodatSavjetnik = true;
					}
				}
			} catch (Exception e) {
				request.setAttribute("greska", "Doslo je do greske: " + e.getMessage());
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outDodajSavjetnika = response.getWriter();
			outDodajSavjetnika.print("{\"dodato\": " + dodatSavjetnik + "}");
			outDodajSavjetnika.flush();
			break;
		case "provjeraKorisnickogImenaSavjetnika":
			preusmjeri = false;
			String korisnickoImeSavjetnikaProvjera = request.getParameter("korisnickoImeSavjetnika");
			boolean dostupnoKorisnickoImeSavjetnika = provjeraKorisnickogImenaSavjetnika(
					korisnickoImeSavjetnikaProvjera);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outProvjeriImeSavjetnika = response.getWriter();
			outProvjeriImeSavjetnika.print("{\"dostupno\": " + dostupnoKorisnickoImeSavjetnika + "}");
			outProvjeriImeSavjetnika.flush();
			break;
		case "urediSavjetnika":
			preusmjeri = false;
			boolean uredjenSavjetnik = false;
			try {
				Savjetnik savjetnik = savjetnikBean.vratiSavjetnikaPoId(request.getParameter("idSavjetnik"));
				String lozinkaSavjetnikaUredi = savjetnik.getLozinka();
				if (!"".equals(request.getParameter("lozinkaSavjetnika"))) {
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					String lozinkaSavjetnika = request.getParameter("lozinkaSavjetnika");
					lozinkaSavjetnikaUredi = passwordEncoder.encode(lozinkaSavjetnika);
				}
				Savjetnik azuriraniSavjetnik = new Savjetnik(Integer.parseInt(request.getParameter("idSavjetnik")),
						request.getParameter("imeSavjetnika"), request.getParameter("prezimeSavjetnika"),
						request.getParameter("korisnickoImeSavjetnika"), lozinkaSavjetnikaUredi);
				savjetnikBean.setSavjetnik(azuriraniSavjetnik);
				if (savjetnikBean.urediSavjetnika()) {
					uredjenSavjetnik = true;
				}
			} catch (Exception e) {
				request.setAttribute("greska", "Doslo je do greske: " + e.getMessage());
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outUrediSavjetnika = response.getWriter();
			outUrediSavjetnika.print("{\"promjenjeno\": " + uredjenSavjetnik + "}");
			outUrediSavjetnika.flush();
			break;
		case "prikaziSavjetnika":
			preusmjeri = false;
			Savjetnik savjetnikPrikaz = savjetnikBean.vratiSavjetnikaPoId(request.getParameter("idSavjetnik"));
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outPrikaziSavjetnika = response.getWriter();
			Gson gsonSavjetnik = new Gson();
			String savjetnikPrikazS = gsonSavjetnik.toJson(savjetnikPrikaz);
			outPrikaziSavjetnika.print(savjetnikPrikazS);
			outPrikaziSavjetnika.flush();
			break;
		case "obrisiSavjetnika":
			preusmjeri = false;
			boolean obrisanSavjetnik = false;
			if (request.getParameter("idSavjetnik") != null) {
				Savjetnik savjetnik = savjetnikBean.vratiSavjetnikaPoId(request.getParameter("idSavjetnik"));
				if (savjetnik != null) {
					savjetnikBean.setSavjetnik(savjetnik);
					obrisanSavjetnik = savjetnikBean.obrisiSavjetnika();
				}
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter outObrisiSavjetnika = response.getWriter();
			outObrisiSavjetnika.print("{\"obrisano\": " + obrisanSavjetnik + "}");
			outObrisiSavjetnika.flush();
			break;
		case "odjava":
			nalogBean.odjava();
			sesija.invalidate();
			adresa = "/WEB-INF/pages/index.jsp";
			break;
		default:
			adresa = "/WEB-INF/pages/nijePronadjeno.jsp";
		}

		if (preusmjeri) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(adresa);
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean provjeraNazivaKategorije(String nazivKategorije) {
		KategorijaBean kategorijaBean = new KategorijaBean();
		if (kategorijaBean.provjeriNazivKategorije(nazivKategorije))
			return false;
		else
			return true;
	}

	private boolean provjeraKorisnickogImenaSavjetnika(String korisnickoIme) {
		SavjetnikBean savjetnikBean = new SavjetnikBean();
		if (savjetnikBean.provjeriDostupnostKorisnickogImena(korisnickoIme))
			return false;
		else
			return true;
	}

	private boolean provjeraKorisnickogImena(String korisnickoIme) {
		KorisnikBean korisnikBean = new KorisnikBean();
		if (korisnikBean.provjeriDostupnostKorisnickogImena(korisnickoIme))
			return false;
		else
			return true;
	}

	private boolean provjeraEmaila(String email) {
		KorisnikBean korisnikBean = new KorisnikBean();
		if (korisnikBean.provjeriDostupnostEmaila(email))
			return false;
		else
			return true;
	}

	private String kreirajKljuc() {
		String dozvoljeniKarakteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder kljuc = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			int slucajanIndeks = random.nextInt(dozvoljeniKarakteri.length());
			kljuc.append(dozvoljeniKarakteri.charAt(slucajanIndeks));
		}
		return kljuc.toString();
	}

}
