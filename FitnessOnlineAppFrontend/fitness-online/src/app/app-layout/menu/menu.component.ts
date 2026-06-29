import { ViewportRuler } from '@angular/cdk/scrolling';
import { Component, HostListener } from '@angular/core';
import { OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { PrijavaOdgovor } from 'src/app/model/prijavaOdgovor';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  veciEkran: boolean = true;
  korisnickoIme: string;
  avatarUrl: string = 'assets/avatari/0.png';
  logoUrl: string = 'assets/logo/logo.png';
  prijavljen: boolean;

  constructor(private viewportRuler: ViewportRuler, private loginService: LoginService,
    private router: Router, private snackBar: MatSnackBar) {
    const podaci: PrijavaOdgovor | null = this.loginService.vratiPodatke();
    if (podaci) {
      this.korisnickoIme = podaci.korisnickoIme;
      this.avatarUrl = podaci.avatarUrl;
      this.prijavljen = true;
    }
    else {
      this.korisnickoIme = 'gost';
      this.avatarUrl = 'assets/avatari/0.png';
      this.prijavljen = false;
    }
  }

  ngOnInit() {
    this.veciEkran = this.viewportRuler.getViewportSize().width >= 800;
  }

  @HostListener('window:resize')
  onWindowResize() {
    this.veciEkran = this.viewportRuler.getViewportSize().width >= 800;
  }

  odjava(): void {
    this.loginService.odjava();
    this.router.navigate(['../program/home'], { skipLocationChange: true });
    this.snackBar.open('Uspješno ste se odjavili sa sistema!', 'Zatvori', { duration: 5000 });
  }

}
