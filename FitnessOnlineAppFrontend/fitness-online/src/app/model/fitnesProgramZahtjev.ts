export class FitnesProgramZahtjev {

  naziv: string;
  opis: string;
  cijena: number;
  nivoTezine: string;
  trajanje: number;
  kategorijaId: number;
  korisnickoIme: string;
  tipLokacije: string;
  adresaLokacije: string;
  urlLokacije: string;

  constructor(
    naziv: string,
    opis: string,
    cijena: number,
    nivoTezine: string,
    trajanje: number,
    kategorijaId: number,
    korisnickoIme: string,
    tipLokacije: string,
    adresaLokacije: string,
    urlLokacije: string
  ) {
    this.naziv = naziv;
    this.opis = opis;
    this.cijena = cijena;
    this.nivoTezine = nivoTezine;
    this.trajanje = trajanje;
    this.kategorijaId = kategorijaId;
    this.korisnickoIme = korisnickoIme;
    this.tipLokacije = tipLokacije;
    this.adresaLokacije = adresaLokacije;
    this.urlLokacije = urlLokacije;
  }

}
