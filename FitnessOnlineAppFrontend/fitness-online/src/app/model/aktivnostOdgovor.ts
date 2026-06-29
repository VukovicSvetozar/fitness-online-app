export class AktivnostOdgovor {
  id: number;
  naziv: string;
  nazivKategorije: string;
  intezitet: string;
  trajanje: number;
  brojPonavljanja: number;
  korisnickoIme: string;
  datum: string;
  zavrseno: boolean;

  constructor(
    id: number,
    naziv: string,
    nazivKategorije: string,
    intezitet: string,
    trajanje: number,
    brojPonavljanja: number,
    korisnickoIme: string,
    datum: string,
    zavrseno: boolean
  ) {
    this.id = id;
    this.naziv = naziv;
    this.nazivKategorije = nazivKategorije;
    this.intezitet = intezitet;
    this.trajanje = trajanje;
    this.brojPonavljanja = brojPonavljanja;
    this.korisnickoIme = korisnickoIme;
    this.datum = datum;
    this.zavrseno = zavrseno;
  }

}
