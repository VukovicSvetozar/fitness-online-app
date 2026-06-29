export class AktivnostInfo {
  ukupno: number;
  brojZavrsenih: number;
  korisnickoIme: string;
  datum: string;

  constructor(
    ukupno: number,
    brojZavrsenih: number,
    korisnickoIme: string,
    datum: string
  ) {
    this.ukupno = ukupno;
    this.brojZavrsenih = brojZavrsenih;
    this.korisnickoIme = korisnickoIme;
    this.datum = datum;
  }

}
