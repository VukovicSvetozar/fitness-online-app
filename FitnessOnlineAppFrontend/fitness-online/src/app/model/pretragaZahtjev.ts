export class PretragaZahtjev {

  kategorijaId: number;
  korisnickoIme: string;
  naziv: string;
  tipLokacije: string;
  minCijena: number;
  maxCijena: number;
  datumOd: string;
  datumDo: string;
  nivoTezine: string;
  minTrajanje: number;
  maxTrajanje: number;

  constructor(
    kategorijaId: number,
    korisnickoIme: string,
    naziv: string,
    tipLokacije: string,
    minCijena: number,
    maxCijena: number,
    datumOd: string,
    datumDo: string,
    nivoTezine: string,
    minTrajanje: number,
    maxTrajanje: number
  ) {
    this.kategorijaId = kategorijaId;
    this.korisnickoIme = korisnickoIme;
    this.naziv = naziv;
    this.tipLokacije = tipLokacije;
    this.minCijena = minCijena;
    this.maxCijena = maxCijena;
    this.datumOd = datumOd
    this.datumDo = datumDo;
    this.nivoTezine = nivoTezine;
    this.minTrajanje = minTrajanje;
    this.maxTrajanje = maxTrajanje;
  }

}
