import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { KategorijaOdgovor } from 'src/app/model/kategorijaOdgovor';
import { BackendService } from 'src/app/services/backend.service';
import { FeatureModalComponent } from '../feature-modal/feature-modal.component';

@Component({
  selector: 'app-category-modal',
  templateUrl: './category-modal.component.html',
  styleUrls: ['./category-modal.component.css']
})
export class CategoryModalComponent implements OnInit {

  odabranaKategorija: KategorijaOdgovor | null = null;
  listaKategorija: KategorijaOdgovor[] = [];

  constructor(private dialogRef: MatDialogRef<CategoryModalComponent>, private backendService: BackendService,
    private snackBar: MatSnackBar, private dijalog: MatDialog) { }

  ngOnInit(): void {
    this.vratiSveKategorije();
  }

  odaberiKategoriju(odabranaKategorija: KategorijaOdgovor) {
    this.odabranaKategorija = odabranaKategorija;
  }

  odustani() {
    this.odabranaKategorija = null;
    this.dialogRef.close();
    this.snackBar.open('Odustali ste od dodavanja fitnes programa!', 'Zatvori', { duration: 4000 });
  }

  nastavi() {
    this.dialogRef.close(this.odabranaKategorija);
    this.otvoriSvojstvaDijalog();
  }

  vratiSveKategorije(): void {
    this.backendService.vratiSveKategorije().subscribe({
      next: (odgovor) => {
        if (odgovor)
          this.listaKategorija = Array.from(odgovor);
      },
      error: () => {
        this.snackBar.open('Došlo je do greške prilikom odabira kategorije!', 'Zatvori', { duration: 4000 });
      }
    })
  }

  otvoriSvojstvaDijalog(): void {
    this.dijalog.open(FeatureModalComponent, {
      width: '600px',
      data: this.odabranaKategorija
    });
  }

}
