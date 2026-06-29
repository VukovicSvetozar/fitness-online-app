export class AktivnostZahtjev {
  naziv: string;
  kategorijaId: number;
  intezitet: string;
  trajanje: number;
  brojPonavljanja: number;
  korisnickoIme: string;

  constructor(
    naziv: string,
    kategorijaId: number,
    intezitet: string,
    trajanje: number,
    brojPonavljanja: number,
    korisnickoIme: string
  ) {
    this.naziv = naziv;
    this.kategorijaId = kategorijaId;
    this.intezitet = intezitet;
    this.trajanje = trajanje;
    this.brojPonavljanja = brojPonavljanja;
    this.korisnickoIme = korisnickoIme;
  }

}
