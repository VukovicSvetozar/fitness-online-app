
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AvatarModalComponent } from 'src/app/auth/avatar-modal/avatar-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { RegistracijaZahtjev } from 'src/app/model/registracijaZahtjev';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BackendService } from 'src/app/services/backend.service';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  registracijaForm!: FormGroup;
  lozinkaPrikaz: boolean;
  avatarUrl: string;

  constructor(private formBuilder: FormBuilder,
    private dijalog: MatDialog, private backendService: BackendService,
    private snackBar: MatSnackBar, private router: Router, private loginService: LoginService) {
    this.lozinkaPrikaz = true;
    this.avatarUrl = 'assets/avatari/0.png';
  }

  ngOnInit(): void {
    this.registracijaForm = this.formBuilder.group({
      ime: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      prezime: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      grad: ['', [Validators.required, Validators.maxLength(30)]],
      brojTelefona: ['', [Validators.pattern('^[0-9]+$')]],
      email: ['', [Validators.required, Validators.maxLength(100), Validators.email]],
      korisnickoIme: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(3), Validators.maxLength(100)]],
      lozinka: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(8), Validators.maxLength(20)]],
      avatarUrl: [this.avatarUrl]
    });
  }

  promjeniVidljivostLozinke(input: any) {
    input.type = this.lozinkaPrikaz ? 'text' : 'password';
    this.lozinkaPrikaz = !this.lozinkaPrikaz;
  }

  otvoriAvatarDijalog(): void {
    const dijalogRef = this.dijalog.open(AvatarModalComponent, {
      width: '400px'
    });
    dijalogRef.afterClosed().subscribe((result: string) => {
      if (result) {
        this.avatarUrl = result;
      }
    });
  }

  provjeriDostupnostKorisnickogImena(): void {
    const korisnickoImeProvjera = this.registracijaForm.get('korisnickoIme');
    if (korisnickoImeProvjera && korisnickoImeProvjera.value) {
      this.backendService.provjeriDostupnostKorisnickogImena(korisnickoImeProvjera.value).subscribe((dostupan) => {
        if (!dostupan) {
          korisnickoImeProvjera.setErrors({ korisnickoImeZauzeto: true });
        }
      });
    }
  }

  provjeriDostupnostEmaila(): void {
    const emailProvjera = this.registracijaForm.get('email');
    if (emailProvjera && emailProvjera.value) {
      this.backendService.provjeriDostupnostEmaila(emailProvjera.value).subscribe((dostupan) => {
        if (!dostupan) {
          emailProvjera.setErrors({ emailZauzet: true });
        }
      });
    }
  }

  registracijaKorisnika(): void {
    if (this.registracijaForm.invalid) {
      return;
    }
    const zahtjev: RegistracijaZahtjev = {
      ime: this.registracijaForm.value.ime,
      prezime: this.registracijaForm.value.prezime,
      grad: this.registracijaForm.value.grad,
      email: this.registracijaForm.value.email,
      brojTelefona: this.registracijaForm.value.brojTelefona,
      korisnickoIme: this.registracijaForm.value.korisnickoIme,
      lozinka: this.registracijaForm.value.lozinka,
      avatarUrl: this.avatarUrl
    }
    this.backendService.registracija(zahtjev).subscribe({
      next: () => {
        this.snackBar.open('Dobili ste mail za potvrdu registracije.', 'Početna strana.', { duration: 7000 }).onAction().subscribe(() => {
          this.loginService.sacuvajKorisnickoIme(this.registracijaForm.value.korisnickoIme);
          this.router.navigate(['/../../home']);
        });
        setTimeout(() => {
          this.loginService.sacuvajKorisnickoIme(this.registracijaForm.value.korisnickoIme);
          this.router.navigate(['/../../home']);
        }, 7000);
        setTimeout(() => {
          this.loginService.sacuvajKorisnickoIme(this.registracijaForm.value.korisnickoIme);
          this.router.navigate(['/../../home']);
          this.registracijaForm.reset();
        }, 7000);
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom registracije!', 'Zatvori', { duration: 5000 });
      }
    });
  }

}
