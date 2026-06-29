export class FitnesProgramKarticaOdgovor {

  id: number;
  naziv: string;
  cijena: number;
  nazivKategorije: string;
  posterUrl: string;

  constructor(
    id: number,
    naziv: string,
    cijena: number,
    nazivKategorije: string,
    posterUrl: string
  ) {
    this.id = id;
    this.naziv = naziv;
    this.cijena = cijena;
    this.nazivKategorije = nazivKategorije;
    this.posterUrl = posterUrl;
  }

}
