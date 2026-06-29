import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BackendService } from 'src/app/services/backend.service';

@Component({
  selector: 'app-image-modal',
  templateUrl: './image-modal.component.html',
  styleUrls: ['./image-modal.component.css']
})
export class ImageModalComponent {

  constructor(private dialogRef: MatDialogRef<ImageModalComponent>, private backendService: BackendService,
    private snackBar: MatSnackBar, private router: Router, @Inject(MAT_DIALOG_DATA) public fitnesProgramId: number | null) { }

  ngOnInit(): void {
  }

  odabraneSlike: File[] = [];

  preglediSlika: string[] = [];
  selektovaniPoster: string | null = null;
  omoguciKraj: boolean = false;

  odaberiSlike(event: any): void {
    this.selektovaniPoster = null;
    const noveOdabraneSlike = event.target.files;
    if (noveOdabraneSlike && noveOdabraneSlike[0]) {
      const brojSlika = noveOdabraneSlike.length;
      for (let i = 0; i < brojSlika; i++) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          const podatakURL = e.target.result;
          if (!this.preglediSlika.includes(podatakURL)) {
            this.preglediSlika.push(podatakURL);
            if (!this.odabraneSlike.includes(noveOdabraneSlike[i])) {
              this.odabraneSlike.push(noveOdabraneSlike[i]);
            }
          }
        };
        reader.readAsDataURL(noveOdabraneSlike[i]);
      }
    }
  }
 
  selektujPoster(poster: string): void {
    this.selektovaniPoster = poster;
  }

  dodajSlike(): void {
    if (this.odabraneSlike) {
      console.log("odabraneSlike",this.odabraneSlike);
      for (let i = 0; i < this.odabraneSlike.length; i++) {
        const isPoster = this.selektovaniPoster === this.preglediSlika[i];
        this.ucitaj(this.odabraneSlike[i], isPoster);
      }
      this.selektovaniPoster = null;
      this.omoguciKraj = true;
    }
  }

  ucitaj(file: File, isPoster: boolean): void {
    if (file) {
      this.backendService.dodajSlike(file, this.fitnesProgramId!, isPoster).subscribe({
        next: () => {
          this.snackBar.open("Slike su dodate!", 'Zatvori', { duration: 5000 });
        },
        error: () => {
          this.snackBar.open('Došlo je do greške tokom dodavanja slike!', 'Zatvori', { duration: 10000 });
        }
      });
    }
  }

  zavrsi() {
    this.dialogRef.close();
    this.router.navigate(['/../../home']);
  }

}
