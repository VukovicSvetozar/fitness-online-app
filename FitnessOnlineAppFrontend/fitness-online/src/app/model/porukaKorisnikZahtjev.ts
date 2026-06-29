export class PorukaKorisnikZahtjev {

  tekstPoruke: string;
  korisnickoImePosiljalac: string;
  korisnickoImePrimalac: string;

  constructor(
    tekstPoruke: string,
    korisnickoImePosiljalac: string,
    korisnickoImePrimalac: string
  ) {
    this.tekstPoruke = tekstPoruke;
    this.korisnickoImePosiljalac = korisnickoImePosiljalac;
    this.korisnickoImePrimalac = korisnickoImePrimalac;
  }

}
