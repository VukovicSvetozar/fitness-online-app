export class FitnesProgramKomentarZahtjev {

    komentar: string;
    korisnickoIme: string;
    fitnesProgramId: number;

    constructor(
        komentar: string,
        korisnickoIme: string,
        fitnesProgramId: number) {
        this.komentar = komentar;
        this.korisnickoIme = korisnickoIme;
        this.fitnesProgramId = fitnesProgramId;
    }

}
