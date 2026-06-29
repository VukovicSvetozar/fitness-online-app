import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { PrijavaOdgovor } from 'src/app/model/prijavaOdgovor';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor() {
  }

  public odjava() {
    window.localStorage.clear();
  }

  public sacuvajPodatke(podaci: PrijavaOdgovor) {
    window.localStorage.removeItem(environment.KORISNIK);
    window.localStorage.setItem(environment.KORISNIK, JSON.stringify(podaci));
  }

  public vratiPodatke(): any {
    const podaci = window.localStorage.getItem(environment.KORISNIK);
    if (podaci) {
      return JSON.parse(podaci);
    }
    return null;
  }

  public azurirajKorisnickoIme(novoKorisnickoIme: string) {
    const podaci = this.vratiPodatke();
    if (podaci) {
      podaci.korisnickoIme = novoKorisnickoIme;
      window.localStorage.setItem(environment.KORISNIK, JSON.stringify(podaci));
    }
  }

  public sacuvajKorisnickoIme(korisnickoIme: string) {
    window.localStorage.removeItem(environment.KORISNICKO_IME);
    window.localStorage.setItem(environment.KORISNICKO_IME, korisnickoIme);
  }

  public vratiKorisnickoIme(): string | null {
    return window.localStorage.getItem(environment.KORISNICKO_IME);
  }

  public azurirajAvatarUrl(noviAvatarUrl: string) {
    const podaci = this.vratiPodatke();
    if (podaci) {
      podaci.avatarUrl = noviAvatarUrl;
      window.localStorage.setItem(environment.KORISNIK, JSON.stringify(podaci));
    }
  }

}
