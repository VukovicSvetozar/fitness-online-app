import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PorukaKorisnikZahtjev } from 'src/app/model/porukaKorisnikZahtjev';
import { PorukaKorisnikOdgovor } from 'src/app/model/porukaKorisnikOdgovor';
import { BackendService } from 'src/app/services/backend.service';
import { LoginService } from 'src/app/services/login.service';
import { PorukaSavjetnikZahtjev } from 'src/app/model/porukaSavjetnikZahtjev';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  savjetnikForm!: FormGroup;
  korisniciForm!: FormGroup;
  korisnickoIme: string = 'gost';
  savjetnici: string[] = [];
  korisnici: string[] = [];
  porukeKorisnika: PorukaKorisnikOdgovor[] = [];

  constructor(private formBuilder: FormBuilder, private snackBar: MatSnackBar, private router: Router,
    private loginService: LoginService, private backendService: BackendService) {
  }

  ngOnInit(): void {
    this.korisnickoIme = this.loginService.vratiPodatke().korisnickoIme;
    this.savjetnikForm = this.formBuilder.group({
      odabraniSavjetnik: ['', Validators.required],
      naslov: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      tekst: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(300)]]
    });
    this.korisniciForm = this.formBuilder.group({
      odabraniKorisnik: ['', Validators.required],
      tekstPoruke: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(80)]]
    });
    this.vratiImenaSavjetnika();
    this.vratiKorisnickaImena();
  }

  vratiImenaSavjetnika(): void {
    this.backendService.vratiImenaSavjetnika().subscribe({
      next: (odgovor) => {
        this.savjetnici = Array.from(odgovor);
      }
    });
  }

  vratiKorisnickaImena(): void {
    this.backendService.vratiKorisnickaImena(this.korisnickoIme).subscribe({
      next: (odgovor) => {
        this.korisnici = Array.from(odgovor);
      }
    });
  }

  posaljiPorukuSavjetniku(): void {
    if (this.savjetnikForm.invalid) {
      return;
    }

    const zahtjev: PorukaSavjetnikZahtjev = {
      naslovPoruke: this.savjetnikForm.value.naslov,
      tekstPoruke: this.savjetnikForm.value.tekst,
      korisnickoIme: this.korisnickoIme,
      imeSavjetnika: this.savjetnikForm.value.odabraniSavjetnik
    }

    this.backendService.posaljiPorukuSavjetniku(zahtjev).subscribe({
      next: () => {
        this.snackBar.open('Poruka je uspješno poslata.', 'Početna strana.', { duration: 4000 }).onAction().subscribe(() => {
          this.router.navigate(['/../../home']);
        });
        setTimeout(() => {
          this.router.navigate(['/../../home']);
        }, 4000);
        setTimeout(() => {
          this.router.navigate(['/../../home']);
          this.savjetnikForm.reset();
        }, 4000);
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom slanja poruke! ' + greska.error.poruka, 'Zatvori', { duration: 4000 });
      }
    });
  }

  posaljiPorukuKorisniku() {
    if (this.korisniciForm.valid) {
      const zahtjev: PorukaKorisnikZahtjev = {
        tekstPoruke: this.korisniciForm.value.tekstPoruke,
        korisnickoImePosiljalac: this.korisnickoIme,
        korisnickoImePrimalac: this.korisniciForm.value.odabraniKorisnik
      }
      this.backendService.posaljiPorukuKorisniku(zahtjev).subscribe({
        next: () => {
          this.korisniciForm.get('tekstPoruke')!.reset();
          this.vratiPorukeKorisnika();
        },
        error: (greska) => {
          this.snackBar.open('Došlo je do greške prilikom slanja poruke! ' + greska.error.poruka, 'Zatvori', { duration: 4000 });
        }
      });
    }
  }

  vratiPorukeKorisnika(): void {
    const korisnickoImePrviUcesnik = this.korisnickoIme;
    const korisnickoImeDrugiUcesnik = this.korisniciForm.value.odabraniKorisnik;
    this.porukeKorisnika = [];  
    this.backendService.vratiPorukeKorisnika(korisnickoImePrviUcesnik, korisnickoImeDrugiUcesnik).subscribe({
      next: (odgovor) => {
        if (Array.isArray(odgovor) && odgovor.length > 0) {
          this.porukeKorisnika = odgovor.sort((a, b) => a.vrijemePoruke.localeCompare(b.vrijemePoruke));
        }
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom vraćanja poruka! ' + greska.error.poruka, 'Zatvori', { duration: 4000 });
      }
    });
  }

}
