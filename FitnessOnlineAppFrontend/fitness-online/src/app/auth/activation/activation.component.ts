import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BackendService } from 'src/app/services/backend.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AktivacijaZahtjev } from 'src/app/model/aktivacijaZahtjev';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent implements OnInit {

  aktivacijaForm!: FormGroup;
  korisnickoIme: string = '';
  kljuc: string = '';

  constructor(private formBuilder: FormBuilder, private backendService: BackendService, private snackBar: MatSnackBar,
    private route: ActivatedRoute, private router: Router, private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.aktivacijaForm = this.formBuilder.group({});
    this.korisnickoIme = this.route.snapshot.queryParamMap.get('korisnickoIme')!;
    this.kljuc = this.route.snapshot.queryParamMap.get('kljuc')!;
  }

  aktivacijaKorisnika(): void {
    if (this.aktivacijaForm.invalid) {
      return;
    }
    const zahtjev: AktivacijaZahtjev = {
      korisnickoIme: this.korisnickoIme,
      kljuc: this.kljuc
    }

    this.backendService.aktivacija(zahtjev).subscribe({
      next: (odgovor) => {
        this.loginService.sacuvajPodatke(odgovor);
        this.snackBar.open('Nalog je uspješno aktiviran.', 'Početna strana.', { duration: 5000 }).onAction().subscribe(() => {
          this.router.navigate(['/../../home']);
        });
        setTimeout(() => {
          this.router.navigate(['/../../home']);
        }, 5000);
        this.aktivacijaForm.reset();
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom aktivacije naloga! ', 'Zatvori', { duration: 5000 });
      }
    });

  }

}
