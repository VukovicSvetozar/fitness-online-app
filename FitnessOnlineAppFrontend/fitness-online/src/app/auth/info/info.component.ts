import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BackendService } from 'src/app/services/backend.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AvatarModalComponent } from '../avatar-modal/avatar-modal.component';
import { KorisnikInfo } from 'src/app/model/korisnikInfo';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {
  azuriranjeForm!: FormGroup;
  staraLozinkaPrikaz: boolean;
  novaLozinkaPrikaz: boolean;
  avatarUrl: string = 'assets/avatari/0.png';
  korisnickoIme: string;

  constructor(private formBuilder: FormBuilder, private loginService: LoginService,
    private dijalog: MatDialog, private backendService: BackendService,
    private snackBar: MatSnackBar, private router: Router) {
    this.staraLozinkaPrikaz = true;
    this.novaLozinkaPrikaz = true;
    this.korisnickoIme = this.loginService.vratiPodatke().korisnickoIme;
    this.avatarUrl = this.loginService.vratiPodatke().avatarUrl;
  }

  ngOnInit(): void {
    this.azuriranjeForm = this.formBuilder.group({
      ime: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      prezime: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      grad: ['', [Validators.required, Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.maxLength(100), Validators.email]],
      brojTelefona: ['', [Validators.pattern('^[0-9]*$')]],
      novaLozinka: ['', [Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(8), Validators.maxLength(20)]],
      staraLozinka: ['', [Validators.pattern('^[a-zA-Z0-9]*$'), Validators.minLength(8), Validators.maxLength(20)]],
      avatarUrl: [this.avatarUrl]
    });
    this.backendService.vratiPodatkeKorisnika(this.korisnickoIme).subscribe((korisnikInfo: KorisnikInfo) => {
      this.azuriranjeForm.patchValue(korisnikInfo);
    });
  }

  promjeniVidljivostStareLozinke(input: any) {
    input.type = this.staraLozinkaPrikaz ? 'text' : 'password';
    this.staraLozinkaPrikaz = !this.staraLozinkaPrikaz;
  }

  promjeniVidljivostNoveLozinke(input: any) {
    input.type = this.novaLozinkaPrikaz ? 'text' : 'password';
    this.novaLozinkaPrikaz = !this.novaLozinkaPrikaz;
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

  provjeriDostupnostEmaila(): void {
    const emailProvjera = this.azuriranjeForm.get('email');
    if (emailProvjera && emailProvjera.value) {
      this.backendService.provjeriDostupnostEmaila(emailProvjera.value).subscribe((dostupan) => {
        if (!dostupan) {
          emailProvjera.setErrors({ emailZauzet: true });
        }
      });
    }
  }

  azuriranjeKorisnika(): void {
    if (this.azuriranjeForm.invalid) {
      return;
    }
    const zahtjev: KorisnikInfo = {
      ime: this.azuriranjeForm.value.ime,
      prezime: this.azuriranjeForm.value.prezime,
      grad: this.azuriranjeForm.value.grad,
      email: this.azuriranjeForm.value.email,
      brojTelefona: this.azuriranjeForm.value.brojTelefona,
      korisnickoIme: this.korisnickoIme,
      staraLozinka: this.azuriranjeForm.value.staraLozinka,
      novaLozinka: this.azuriranjeForm.value.novaLozinka,
      avatarUrl: this.avatarUrl
    }
    this.loginService.azurirajAvatarUrl(this.avatarUrl);

    this.backendService.azurirajPodatkeKorisnika(zahtjev).subscribe({
      next: () => {
        this.snackBar.open('Podaci su uspješno ažurirani.', 'Početna stranica.', { duration: 4000 }).onAction().subscribe(() => {
          this.router.navigate(['/../../home']);
        });
        setTimeout(() => {
          this.router.navigate(['/../../home']);
        }, 4000);
        setTimeout(() => {
          this.router.navigate(['/../../home']);
          this.azuriranjeForm.reset();
        }, 4000);
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom ažuriranja podataka!', 'Zatvori', { duration: 4000 });
      }
    });

  }

}
