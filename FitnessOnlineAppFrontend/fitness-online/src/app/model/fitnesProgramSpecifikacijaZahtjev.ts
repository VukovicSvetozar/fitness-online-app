export class FitnesProgramSpecifikacijaZahtjev {

  vrijednost: string;
  fitnesProgramId: number;
  specifikacijaId: number;

  constructor(
    vrijednost: string,
    fitnesProgramId: number,
    specifikacijaId: number
  ) {
    this.vrijednost = vrijednost;
    this.fitnesProgramId = fitnesProgramId;
    this.specifikacijaId = specifikacijaId;
  }

}
