export class PorukaSavjetnikZahtjev {

  naslovPoruke: string;
  tekstPoruke: string;
  korisnickoIme: string;
  imeSavjetnika: string;

  constructor(
    naslovPoruke: string,
    tekstPoruke: string,
    korisnickoIme: string,
    imeSavjetnika: string
  ) {
    this.naslovPoruke = naslovPoruke;
    this.tekstPoruke = tekstPoruke;
    this.korisnickoIme = korisnickoIme;
    this.imeSavjetnika = imeSavjetnika;
  }

}
