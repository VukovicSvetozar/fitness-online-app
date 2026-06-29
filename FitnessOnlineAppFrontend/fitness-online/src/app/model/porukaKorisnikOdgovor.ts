export class PorukaKorisnikOdgovor {

  tekstPoruke: string;
  vrijemePoruke: string;
  korisnickoImePosiljalac: string;

  constructor(
    tekstPoruke: string,
    vrijemePoruke: string,
    korisnickoImePosiljalac: string
  ) {
    this.tekstPoruke = tekstPoruke;
    this.vrijemePoruke = vrijemePoruke;
    this.korisnickoImePosiljalac = korisnickoImePosiljalac;
  }

}
