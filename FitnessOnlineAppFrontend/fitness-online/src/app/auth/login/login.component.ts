import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BackendService } from 'src/app/services/backend.service';
import { Router } from '@angular/router';
import { PrijavaZahtjev } from 'src/app/model/prijavaZahtjev';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  prijavaForm!: FormGroup;
  lozinkaPrikaz: boolean;

  constructor(private formBuilder: FormBuilder, private loginService: LoginService,
    private backendService: BackendService, private snackBar: MatSnackBar, private router: Router) {
    this.lozinkaPrikaz = true;
  }

  ngOnInit(): void {
    this.prijavaForm = this.formBuilder.group({
      korisnickoIme: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(3), Validators.maxLength(100)]],
      lozinka: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(8), Validators.maxLength(20)]]
    });
  }

  promjeniVidljivostLozinke(input: any) {
    input.type = this.lozinkaPrikaz ? 'text' : 'password';
    this.lozinkaPrikaz = !this.lozinkaPrikaz;
  }

  prijavaKorisnika(): void {
    if (this.prijavaForm.invalid) {
      return;
    }
    const zahtjev: PrijavaZahtjev = {
      korisnickoIme: this.prijavaForm.value.korisnickoIme,
      lozinka: this.prijavaForm.value.lozinka
    }
    this.backendService.prijava(zahtjev).subscribe({
      next: (odgovor) => {
        if (odgovor) {
          this.loginService.sacuvajPodatke(odgovor);
          this.prijavaForm.reset();
          this.router.navigate(['/../../home']);
          this.snackBar.open('Uspješno ste prijavljeni.', 'Početna strana.', { duration: 5000 }).onAction().subscribe(() => {
          });
          setTimeout(() => {
            this.router.navigate(['/../../home']);
          }, 5000);
        } else {
          this.snackBar.open('Nalog nije verifikovan. Dobili ste mail za potvrdu registracije.', 'Početna strana.', { duration: 5000 }).onAction().subscribe(() => {
            this.loginService.sacuvajKorisnickoIme(this.prijavaForm.value.korisnickoIme);
            this.router.navigate(['/../../home']);
          });
          setTimeout(() => {
            this.router.navigate(['/../../home']);
            this.loginService.sacuvajKorisnickoIme(this.prijavaForm.value.korisnickoIme);
          }, 5000);
          setTimeout(() => {
            this.router.navigate(['/../../home']);
            this.loginService.sacuvajKorisnickoIme(this.prijavaForm.value.korisnickoIme);
            this.prijavaForm.reset();
          }, 5000);
        }
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom prijave! ' + greska.error.poruka, 'Zatvori', { duration: 4000 });
      }
    })

  }

}
