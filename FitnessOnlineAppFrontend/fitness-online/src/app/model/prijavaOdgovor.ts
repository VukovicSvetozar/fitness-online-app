export class PrijavaOdgovor {

    korisnickoIme: string;
    avatarUrl: string;
    jwtToken: string;
    refreshJwtToken: string;

    constructor(korisnickoIme: string,
        avatarUrl: string,
        jwtToken: string,
        refreshJwtToken: string) {
        this.korisnickoIme = korisnickoIme;
        this.avatarUrl = avatarUrl;
        this.jwtToken = jwtToken;
        this.refreshJwtToken = refreshJwtToken;
    }

}
