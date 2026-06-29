import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { FitnesProgramDetaljiOdgovor } from 'src/app/model/fitnesProgramDetaljiOdgovor';
import { BackendService } from 'src/app/services/backend.service';
import { PurchaseModalComponent } from '../purchase-modal/purchase-modal.component';
import { CommentModalComponent } from '../comment-modal/comment-modal.component';
import { PrijavaOdgovor } from 'src/app/model/prijavaOdgovor';
import { LoginService } from 'src/app/services/login.service';
import { FitnesProgramKomentarInfo } from 'src/app/model/fitnesProgramKomentarInfo';
import { KupovinaInfo } from 'src/app/model/kupovinaInfo';
import { LiveModalComponent } from '../live-modal/live-modal.component';
import { OnlineModalComponent } from '../online-modal/online-modal.component';

@Component({
  selector: 'app-program-details',
  templateUrl: './program-details.component.html',
  styleUrls: ['./program-details.component.css']
})
export class ProgramDetailsComponent implements OnInit, OnDestroy {

  programId: string = "";
  odabraniProgram: FitnesProgramDetaljiOdgovor | null = null;
  avatarUrl: string = "";
  specificneOsobine: { [key: string]: string } = {};
  slikeProgramaUrl: string[] = [];
  slikeTrenutniIndeks: number = 0;
  slikeTimeoutId?: number;
  korisnickoIme: string;
  prijavljen: boolean;
  dozvoljenKomentar: boolean = false;
  dozvoljenaKupovina: boolean = false;
  dozvoljenoBrisanje: boolean = false;
  dozvoljenZavrsetak: boolean = false;
  dozvoljenoUcestvovanje: boolean = false;

  constructor(private route: ActivatedRoute, private backendService: BackendService, private snackBar: MatSnackBar,
    private dijalog: MatDialog, private loginService: LoginService, private router: Router) {
    const podaci: PrijavaOdgovor | null = this.loginService.vratiPodatke();
    if (podaci) {
      this.korisnickoIme = podaci.korisnickoIme;
      this.prijavljen = true;
    }
    else {
      this.korisnickoIme = 'gost';
      this.prijavljen = false;
    }
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.programId = params['id'];
    });
    this.dozvoljenKomentar = this.route.snapshot.paramMap.get('dozvoljenKomentar') === 'true';
    this.dozvoljenaKupovina = this.route.snapshot.paramMap.get('dozvoljenaKupovina') === 'true';
    this.dozvoljenoBrisanje = this.route.snapshot.paramMap.get('dozvoljenoBrisanje') === 'true';
    this.dozvoljenZavrsetak = this.route.snapshot.paramMap.get('dozvoljenZavrsetak') === 'true';
    this.dozvoljenoUcestvovanje = this.route.snapshot.paramMap.get('dozvoljenoUcestvovanje') === 'true';
    this.ucitajProgram();
    this.resetTimer();
  }

  ngOnDestroy() {
    window.clearTimeout(this.slikeTimeoutId);
  }

  ucitajProgram(): void {
    const programIdNumber = parseInt(this.programId, 10);
    this.backendService.vratiFitnesProgram(programIdNumber).subscribe({
      next: (odgovor) => {
        if (odgovor) {
          this.odabraniProgram = odgovor;
          this.avatarUrl = this.odabraniProgram!.avatarUrl;
          this.specificneOsobine = this.odabraniProgram!.specificneOsobine;
          this.slikeProgramaUrl = this.odabraniProgram!.slikeFitnesProgramaUrl;
        }
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom učitavanja detalja o fitnes programu!', 'Zatvori', { duration: 5000 });
      }
    })
  }

  resetTimer() {
    if (this.slikeTimeoutId) {
      window.clearTimeout(this.slikeTimeoutId);
    }
    this.slikeTimeoutId = window.setTimeout(() => this.sledecaSlika(), 4000);
  }

  prethodnaSlika(): void {
    const prviSlajd = this.slikeTrenutniIndeks === 0;
    const noviIndeks = prviSlajd
      ? this.slikeProgramaUrl.length - 1
      : this.slikeTrenutniIndeks - 1;
    this.resetTimer();
    this.slikeTrenutniIndeks = noviIndeks;
  }

  sledecaSlika(): void {
    const poslednjiSlajd = this.slikeTrenutniIndeks === this.slikeProgramaUrl.length - 1;
    const noviIndeks = poslednjiSlajd ? 0 : this.slikeTrenutniIndeks + 1;
    this.resetTimer();
    this.slikeTrenutniIndeks = noviIndeks;
  }

  idiNaSlajd(slajdIndeks: number): void {
    this.resetTimer();
    this.slikeTrenutniIndeks = slajdIndeks;
  }

  vratiTrenutniSlajdUrl() {
    return `url('${this.slikeProgramaUrl[this.slikeTrenutniIndeks]}')`;
  }

  otvoriKupovinaDijalog(): void {
    var kupovinaInfo: KupovinaInfo = new KupovinaInfo(this.korisnickoIme, Number(this.programId));
    const dijalogRef = this.dijalog.open(PurchaseModalComponent, {
      width: '400px',
      data: kupovinaInfo
    });
    dijalogRef.afterClosed().subscribe((result: string) => {
    });
  }

  otvoriKomentariDijalog(): void {
    var komentarInfo: FitnesProgramKomentarInfo = new FitnesProgramKomentarInfo(this.korisnickoIme, Number(this.programId));
    const dijalogRef = this.dijalog.open(CommentModalComponent, {
      width: '700px',
      data: komentarInfo
    });
    dijalogRef.afterClosed().subscribe((result: string) => {
    });
  }

  zavrsiProgram(): void {
    if (confirm("Da li ste sigurni?")) {
      this.backendService.zavrsiFitnesProgram(this.korisnickoIme, Number(this.programId)).subscribe({
        next: () => {
          this.snackBar.open('Uspješno ste završili fitnes program!', 'Zatvori', { duration: 4000 });
          this.router.navigate(['/../../personally']);
        },
        error: () => {
          this.snackBar.open('Došlo je do greške prilikom završavanja fitnes programa! ', 'Zatvori', { duration: 4000 });
        }
      })
    }
  }

  obrisiProgram(): void {
    if (confirm("Da li ste sigurni?")) {
      this.backendService.obrisiFitnesProgram(this.korisnickoIme, Number(this.programId)).subscribe({
        next: () => {
          this.snackBar.open('Uspješno ste obrisali fitnes program!', 'Zatvori', { duration: 4000 });
          this.router.navigate(['/../../personally']);
        },
        error: () => {
          this.snackBar.open('Došlo je do greške prilikom brisanja fitnes programa! ', 'Zatvori', { duration: 4000 });
        }
      })
    }
  }

  ucestvuj(): void {
    if (this.odabraniProgram?.tipLokacije == 'online') {
      this.otvoriDijalogZaOnline();
    } else {
      this.otvoriDijalogZaUzivo();
    }
  }

  otvoriDijalogZaUzivo(): void {
    this.dijalog.open(LiveModalComponent, {
      width: '600px',
      data: this.odabraniProgram
    });
  }

  otvoriDijalogZaOnline(): void {
    this.dijalog.open(OnlineModalComponent, {
      width: '600px',
      data: this.odabraniProgram
    });
  }

}
