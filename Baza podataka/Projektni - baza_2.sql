
--	Administrator
---------------------

CREATE TABLE fitnessonline.administrator (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ime VARCHAR(255) NOT NULL,
    prezime VARCHAR(255) NOT NULL,
    korisnicko_ime VARCHAR(50) NOT NULL UNIQUE,
    lozinka VARCHAR(255) NOT NULL
);

CREATE TABLE fitnessonline.savjetnik (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ime VARCHAR(255) NOT NULL,
    prezime VARCHAR(255) NOT NULL,
    korisnicko_ime VARCHAR(50) NOT NULL UNIQUE,
    lozinka VARCHAR(255) NOT NULL
);

INSERT INTO fitnessonline.administrator (id, ime, prezime, korisnicko_ime, lozinka)
VALUES (1, 'Ana', 'Kostic', 'dmnstrtra', '$2a$12$55HFaNdS0cZFvfdseeniuOBSP00C606yJms7oKGPr3G2FJyyTDQ4a');

--	dmnstrtra
--	svtzr65A

INSERT INTO fitnessonline.savjetnik (id, ime, prezime, korisnicko_ime, lozinka)
VALUES (2, 'Marija', 'Ivic', 'dmnstrtrs', '$2a$12$hAv3hgb9ckwkwP/9bO1z2eUlfMB7jaj.7LbN0DzJwBAqEKcGAXFtO');

--	$2a$12$hAv3hgb9ckwkwP/9bO1z2eUlfMB7jaj.7LbN0DzJwBAqEKcGAXFtO
--	dmnstrtrs
--	svtzr65S

------------------------

CREATE TABLE fitnessonline.korisnik (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ime VARCHAR(255) NOT NULL,
    prezime VARCHAR(255) NOT NULL,
    grad VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
	broj_telefona VARCHAR(255),
    korisnicko_ime VARCHAR(50) NOT NULL UNIQUE,
    lozinka VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(255),
    datum_pristupa DATE NOT NULL,
	kljuc VARCHAR(255) NOT NULL,
    uloga VARCHAR(255) NOT NULL
);

------------------------

CREATE TABLE fitnessonline.kategorija (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naziv VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE fitnessonline.specifikacija (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naziv VARCHAR(255) NOT NULL,
    kategorija_id INT,
    FOREIGN KEY (kategorija_id) REFERENCES kategorija(id),
    CONSTRAINT unique_specifikacija_naziv_kategorija UNIQUE (naziv, kategorija_id)
);

------------------------
