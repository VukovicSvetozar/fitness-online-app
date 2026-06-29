import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FitnesProgramKomentarInfo } from 'src/app/model/fitnesProgramKomentarInfo';
import { FitnesProgramKomentarOdgovor } from 'src/app/model/fitnesProgramKomentarOdgovor';
import { FitnesProgramKomentarZahtjev } from 'src/app/model/fitnesProgramKomentarZahtjev';
import { BackendService } from 'src/app/services/backend.service';

@Component({
  selector: 'app-comment-modal',
  templateUrl: './comment-modal.component.html',
  styleUrls: ['./comment-modal.component.css']
})
export class CommentModalComponent implements OnInit {

  listaKomentara: FitnesProgramKomentarOdgovor[] = [];
  trenutnaStranica: number = 0;
  ukupnoElemenata: number = 0;
  brojStranica: number = 0;
  brojElemenata: number = 3;
  komentarForm!: FormGroup;

  constructor(private dialogRef: MatDialogRef<CommentModalComponent>, private backendService: BackendService,
    private paginatorIntl: MatPaginatorIntl, private formBuilder: FormBuilder, private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public komentarInfo: FitnesProgramKomentarInfo | null) { }

  ngOnInit(): void {
    this.ucitajSveKomentare(this.trenutnaStranica, this.brojElemenata);
    this.paginatorIntl.itemsPerPageLabel = 'Broj komentara po stranici:';
    this.paginatorIntl.nextPageLabel = 'Sledeća stranica';
    this.paginatorIntl.previousPageLabel = 'Prethodna stranica';
    this.komentarForm = this.formBuilder.group({
      tekst: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(300), Validators.pattern('^[a-zA-Z0-9_ .,!?]*$')]]
    });
  }

  ucitajSveKomentare(trenutnaStranica: number, brojElemenata: number) {
    if (this.komentarInfo != null) {
      this.backendService.vratiSveKomentare(this.komentarInfo.fitnesProgramId, trenutnaStranica, brojElemenata).subscribe({
        next: (odgovor) => {
          this.listaKomentara = odgovor.komentari;
          this.trenutnaStranica = odgovor.trenutnaStranica;
          this.ukupnoElemenata = odgovor.ukupnoElemenata;
          this.brojStranica = odgovor.brojStranica;
        }
      });
    }
  }

  promjenaStranice(event: PageEvent) {
    const novaStranica = event.pageIndex;
    const velicinaStranice = event.pageSize;
    this.ucitajSveKomentare(novaStranica, velicinaStranice);
  }

  dodajKomentar() {
    if (this.komentarForm.invalid) {
      return;
    }

    const komentarZahtjev: FitnesProgramKomentarZahtjev = {
      komentar: this.komentarForm.value.tekst,
      korisnickoIme: this.komentarInfo!.korisnickoIme,
      fitnesProgramId: this.komentarInfo!.fitnesProgramId
    }
    this.backendService.dodajKomentar(komentarZahtjev!).subscribe({
      next: () => {
        this.snackBar.open('Komentar je uspješno dodat!', 'Zatvori', { duration: 4000 });
        this.komentarForm.reset();
        this.ucitajSveKomentare(this.trenutnaStranica, this.brojElemenata);
      }
    });
  }

  zatvori() {
    this.dialogRef.close();
  }

}
