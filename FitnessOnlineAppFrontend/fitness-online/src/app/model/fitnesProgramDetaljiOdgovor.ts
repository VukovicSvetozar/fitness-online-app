export class FitnesProgramDetaljiOdgovor {

  avatarUrl: string;
  korisnickoIme: string;
  datumPristupa: string;
  grad: string;
  email: string;
  brojTelefona: string;
  vrijemeObjave: string;
  naziv: string;
  opis: string;
  cijena: number;
  nivoTezine: string;
  trajanje: number;
  status: string;
  tipLokacije: string;
  adresaLokacije: string;
  urlLokacije: string;
  nazivKategorije: string;
  specificneOsobine: { [key: string]: string };
  slikeFitnesProgramaUrl: string[];

  constructor(
    avatarUrl: string,
    korisnickoIme: string,
    datumPristupa: string,
    grad: string,
    email: string,
    brojTelefona: string,
    vrijemeObjave: string,
    naziv: string,
    opis: string,
    cijena: number,
    nivoTezine: string,
    trajanje: number,
    status: string,
    tipLokacije: string,
    adresaLokacije: string,
    urlLokacije: string,
    nazivKategorije: string,
    specificneOsobine: { [key: string]: string },
    slikeFitnesProgramaUrl: string[]) {
    this.avatarUrl = avatarUrl;
    this.korisnickoIme = korisnickoIme;
    this.datumPristupa = datumPristupa;
    this.grad = grad;
    this.email = email;
    this.brojTelefona = brojTelefona;
    this.vrijemeObjave = vrijemeObjave;
    this.naziv = naziv;
    this.opis = opis;
    this.cijena = cijena;
    this.nivoTezine = nivoTezine;
    this.trajanje = trajanje;
    this.status = status;
    this.tipLokacije = tipLokacije;
    this.adresaLokacije = adresaLokacije;
    this.urlLokacije = urlLokacije;
    this.nazivKategorije = nazivKategorije;
    this.specificneOsobine = specificneOsobine;
    this.slikeFitnesProgramaUrl = slikeFitnesProgramaUrl;
  }

}
