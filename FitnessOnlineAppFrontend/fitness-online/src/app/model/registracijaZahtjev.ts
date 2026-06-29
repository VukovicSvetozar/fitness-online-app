export class RegistracijaZahtjev {

  ime: string;
  prezime: string;
  grad: string;
  email: string;
  brojTelefona: string;
  korisnickoIme: string;
  lozinka: string;
  avatarUrl: string | null;

  constructor(
    ime: string,
    prezime: string,
    grad: string,
    email: string,
    brojTelefona: string,
    korisnickoIme: string,
    lozinka: string,
    avatarUrl?: string
  ) {
    this.ime = ime;
    this.prezime = prezime;
    this.grad = grad;
    this.email = email;
    this.brojTelefona = brojTelefona;
    this.korisnickoIme = korisnickoIme;
    this.lozinka = lozinka;
    this.avatarUrl = avatarUrl || null;
  }

}
