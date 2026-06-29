export class FitnesProgramKomentarOdgovor {

    id: number;
    komentar: string;
    vrijeme: string;
    avatarUrl: string;
    korisnickoIme: string;
    fitnesProgramId: number;


    constructor(
        id: number,
        komentar: string,
        vrijeme: string,
        avatarUrl: string,
        korisnickoIme: string,
        fitnesProgramId: number) {
        this.id = id;
        this.komentar = komentar;
        this.vrijeme = vrijeme;
        this.avatarUrl = avatarUrl;
        this.korisnickoIme = korisnickoIme;
        this.fitnesProgramId = fitnesProgramId;
    }

}
