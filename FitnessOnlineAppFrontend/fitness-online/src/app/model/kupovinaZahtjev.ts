export class KupovinaZahtjev {

    brojKartice: string;
    payPalRacun: string;
    licno: boolean;
    fitnesProgramId: number;
    korisnickoImeKupca: string;

    constructor(
        brojKartice: string,
        payPalRacun: string,
        licno: boolean,
        fitnesProgramId: number,
        korisnickoImeKupca: string) {
        this.brojKartice = brojKartice;
        this.payPalRacun = payPalRacun;
        this.licno = licno;
        this.fitnesProgramId = fitnesProgramId;
        this.korisnickoImeKupca = korisnickoImeKupca;
    }

}
