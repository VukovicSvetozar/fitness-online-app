package vs.fitnessOnline.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import vs.fitnessOnline.dto.OdgovorSavjetnika;

@RequiredArgsConstructor
@Service
public class MailService {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Value("${application.auth.url.base.registration}")
	private String baseURL;

	@Value("${spring.mail.username}")
	private String mailUsername;

	public boolean posaljiLinkZaPotvrduRegistracije(String email, String korisnickoIme, String kljuc) {
		boolean poslato = false;
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(from);
		smm.setTo(email);
		String naslov = "Fitness online - aktivacija naloga";
		smm.setSubject(naslov);

		String linkZaPotvrdu = baseURL + "?korisnickoIme=" + korisnickoIme + "&kljuc=" + kljuc;
		String tekst = "Kliknite na link ispod kako biste potvrdili registraciju. \n\n";
		tekst += "Link: " + linkZaPotvrdu;

		smm.setText(tekst);
		try {
			javaMailSender.send(smm);
			poslato = true;
		} catch (MailException ex) {
			poslato = false;
		}
		return poslato;
	}

	public boolean posaljiInformacijeODanasnjimProgramima(String email, String poruka) {
		boolean poslato = false;
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(from);
		smm.setTo(email);
		String naslov = "Fitness online - dnevna lista programa";
		smm.setSubject(naslov);

		String tekst = "Lista dnevnih fitnes programa. \n\n";
		tekst += poruka;

		smm.setText(tekst);
		try {
			javaMailSender.send(smm);
			poslato = true;
		} catch (MailException ex) {
			poslato = false;
		}
		return poslato;
	}

	public boolean posaljiOdgovorSavjetnika(OdgovorSavjetnika odgovor) {
		boolean poslato = false;
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh;
		try {
			mmh = new MimeMessageHelper(mm, true);
			mmh.setFrom(mailUsername);
			mmh.setTo(odgovor.getEmail());
			mmh.setSubject(odgovor.getNaslov());
			mmh.setText(odgovor.getTekst());
			if (odgovor.getPrilog() != null && !odgovor.getPrilog().isEmpty())
				mmh.addAttachment(odgovor.getPrilog().getOriginalFilename(), odgovor.getPrilog());
			javaMailSender.send(mm);
			poslato = true;
		} catch (MessagingException e) {
			poslato = false;
		}
		return poslato;
	}

}
