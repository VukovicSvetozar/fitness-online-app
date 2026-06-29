import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PredlogVjezbeOdgovor } from 'src/app/model/predlogVjezbeOdgovor';
import { BackendService } from 'src/app/services/backend.service';

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.css']
})
export class RecommendationComponent implements OnInit {

  predlozi: PredlogVjezbeOdgovor[] = [];

  constructor(private backendService: BackendService, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.vratiPredlogeVjezbi();
  }

  vratiPredlogeVjezbi(): void {
    this.backendService.vratiPredloge().subscribe({
      next: (odgovor) => {
        if (odgovor)
          this.predlozi = Array.from(odgovor);
      },
      error: (greska) => {
        this.snackBar.open('Došlo je do greške prilikom vraćanja predloga vježbi!' + greska, 'Zatvori', { duration: 5000 });
      }
    })
  }


}
