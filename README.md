# 🏋️ Fitness Online Aplikacija

Sistem predstavlja platformu za online fitnes koja korisnicima omogućava pretraživanje programa, vođenje dnevnika aktivnosti i dobijanje prijedloga vježbi.

---

## 🛠️ Tehnologije

| Sloj                  | Tehnologija                              |
|-----------------------|------------------------------------------|
| Backend               | Java · Spring Boot · RESTful API         |
| Frontend              | Angular · TypeScript · HTML · CSS        |
| Baza podataka         | MySQL                                    |
| Administratorska app  | Java · JSP MVC (Model 2)                 |
| Aplikacija savjetnika | Java · JSP                               |

---

## 📁 Struktura projekta

```
fitness-online-app/
├── Administrator/            # Administratorska aplikacija (JSP)
├── Baza podataka/            # SQL skripte za bazu podataka
├── FitnessOnlineAppBackend/  # Backend (Java Spring Boot, RESTful API)
├── FitnessOnlineAppFrontend/ # Frontend (Angular, TypeScript)
├── Savjetnik/                # Aplikacija za savjetnike (JSP)
└── Tekst projekta/           # Dokumentacija projekta
```

---

## 👥 Uloge korisnika

| Uloga              | Opis                                                                                      |
|--------------------|-------------------------------------------------------------------------------------------|
| **Gost**           | Može pretraživati programe i pregledati detalje, ali ne može učestvovati                  |
| **Korisnik**       | Registrovani korisnik sa svim funkcionalnostima — učestvovanje, dnevnik, poruke i sl.    |
| **Savjetnik**      | Pregledava i odgovara na poruke korisnika putem posebne JSP aplikacije                   |
| **Administrator**  | Upravlja kategorijama, korisnicima i statistikom putem JSP aplikacije                    |

---

## ✨ Funkcionalnosti

### Fitnes programi
- Pretraživanje i filtriranje programa po kategoriji (kardio, snaga, fleksibilnost, HIIT), težini, cijeni, trajanju i lokaciji (online, teretana, park)
- Prikaz programa kao kartica (naziv, slika, cijena) sa straničenjem (pagination / virtual scroll)
- Detaljna stranica programa sa svim informacijama, instruktorom i kontaktom
- Komentari i pitanja vezana za program vidljivi svim korisnicima
- Svaki registrovani korisnik može kreirati, pregledati i obrisati svoje programe

### Registracija i prijava
- Forma za registraciju: ime, prezime, grad, korisničko ime, lozinka, avatar (opciono), e-mail
- Aktivacija naloga putem linka koji se šalje na e-mail
- Opcioni CAPTCHA servis za zaštitu od botova
- Izmjena ličnih podataka (osim korisničkog imena)

### Učestvovanje u programima
- Odabir načina plaćanja: kartica, PayPal ili lično na lokaciji
- Za online programe — prikaz YouTube videa; za programe uživo — odlazak na lokaciju
- Pregled istorije učestvovanja i kupovine

### Dnevnik aktivnosti
- Unos vrste vježbe, trajanja, intenziteta i rezultata
- Grafički prikaz napretka (npr. gubitak kilograma) kroz određeni period
- Preuzimanje dnevnika kao PDF dokument

### Komunikacija
- Slanje poruka savjetnicima za izbor programa
- Razmjena privatnih poruka između korisnika

### Dodatne funkcionalnosti
- RSS feed sa najnovijim fitnes vijestima (`feeds.feedburner.com/AceFitFacts`)
- Dnevni prijedlog 10 vježbi sa instrukcijama putem eksternog API-ja (`api-ninjas.com/api/exercises`)
- Pretplata na kategoriju — jednom dnevno korisnik prima mejl sa novim programima te kategorije

### Administratorska aplikacija (JSP)
- Upravljanje kategorijama fitnes programa (CRUD)
- Upravljanje korisnicima (CRUD)
- Pregled logova backend aplikacije (statistika)

### Aplikacija za savjetnike (JSP)
- Pregled primljenih poruka (nepročitane / pročitane)
- Odgovaranje na poruke slanjem e-maila (sa mogućnošću slanja dokumenta ili slike)
- Pretraga poruka po sadržaju

---

## 🚀 Pokretanje projekta

### Preduslovi

- Java 17+
- Node.js 18+ i npm
- Angular CLI (`npm install -g @angular/cli`)
- SQL server (MySQL ili PostgreSQL)

---

### 1. Baza podataka

Pokrenuti SQL skripte iz foldera `Baza podataka/`:

```bash
mysql -u root -p < skripta.sql
```

---

### 2. Backend (Spring Boot)

```bash
cd FitnessOnlineAppBackend/fitnessOnline
./mvnw spring-boot:run
```

API je dostupan na `http://localhost:8080`.

> Podesiti konekciju na bazu u `src/main/resources/application.properties`.

---

### 3. Frontend (Angular)

```bash
cd FitnessOnlineAppFrontend
npm install
ng serve
```

Aplikacija je dostupna na `http://localhost:4200`.

---

### 4. Administratorska aplikacija i aplikacija za savjetnike

Otvoriti projekte `Administrator/` i `Savjetnik/` u Java IDE-u (npr. IntelliJ IDEA ili Eclipse) i pokrenuti kao web aplikacije na odgovarajućem serveru (npr. Apache Tomcat).

---

## 📄 Dokumentacija

Kompletan opis projekta, model podataka i model aplikacije nalaze se u folderu `Tekst projekta/`.
