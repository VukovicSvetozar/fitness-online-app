export class KorisnikInfo {

  ime: string;
  prezime: string;
  grad: string;
  email: string;
  brojTelefona: string;
  korisnickoIme: string;
  staraLozinka: string;
  novaLozinka: string;
  avatarUrl: string | null;

  constructor(
    ime: string,
    prezime: string,
    grad: string,
    email: string,
    brojTelefona: string,
    korisnickoIme: string,
    staraLozinka: string,
    novaLozinka: string,
    avatarUrl?: string
  ) {
    this.ime = ime;
    this.prezime = prezime;
    this.grad = grad;
    this.email = email;
    this.brojTelefona = brojTelefona;
    this.korisnickoIme = korisnickoIme;
    this.staraLozinka = staraLozinka;
    this.novaLozinka = novaLozinka;
    this.avatarUrl = avatarUrl || null;
  }

}
