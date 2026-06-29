export class MasaOdgovor {

  masa: number;
  korisnickoIme: string;
  datum: string;

  constructor(
    masa: number,
    korisnickoIme: string,
    datum: string
  ) {
    this.masa = masa;
    this.korisnickoIme = korisnickoIme;
    this.datum = datum;
  }

}
