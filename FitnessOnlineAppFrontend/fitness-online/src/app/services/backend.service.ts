import { HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders, HttpParams, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { AktivacijaZahtjev } from 'src/app/model/aktivacijaZahtjev';
import { KorisnikInfo } from 'src/app/model/korisnikInfo';
import { PrijavaOdgovor } from 'src/app/model/prijavaOdgovor';
import { PrijavaZahtjev } from 'src/app/model/prijavaZahtjev';
import { RegistracijaZahtjev } from 'src/app/model/registracijaZahtjev';
import { environment } from 'src/environments/environment';
import { KategorijaOdgovor } from '../model/kategorijaOdgovor';
import { SpecifikacijaOdgovor } from '../model/specifikacijaOdgovor';
import { FitnesProgramZahtjev } from '../model/fitnesProgramZahtjev';
import { FitnesProgramSpecifikacijaZahtjev } from '../model/fitnesProgramSpecifikacijaZahtjev';
import { SlikaInfo } from '../model/slikaInfo';
import { FitnesProgramDetaljiOdgovor } from '../model/fitnesProgramDetaljiOdgovor';
import { PretragaZahtjev } from '../model/pretragaZahtjev';
import { FitnesProgramKomentarZahtjev } from '../model/fitnesProgramKomentarZahtjev';
import { KupovinaZahtjev } from '../model/kupovinaZahtjev';
import { FitnesProgramStatistikaOdgovor } from '../model/fitnesProgramStatistikaOdgovor';
import { PorukaKorisnikOdgovor } from '../model/porukaKorisnikOdgovor';
import { PorukaKorisnikZahtjev } from '../model/porukaKorisnikZahtjev';
import { PorukaSavjetnikZahtjev } from '../model/porukaSavjetnikZahtjev';
import { VijestiOdgovor } from '../model/vijestiOdgovor';
import { AktivnostZahtjev } from '../model/aktivnostZahtjev';
import { AktivnostOdgovor } from '../model/aktivnostOdgovor';
import { MasaZahtjev } from '../model/masaZahtjev';
import { MasaOdgovor } from '../model/masaOdgovor';
import { PredlogVjezbeOdgovor } from '../model/predlogVjezbeOdgovor';
import { AktivnostInfo } from '../model/aktivnostInfo';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) {
  }

  // Autentifikacija i autorizacija

  public provjeriDostupnostKorisnickogImena(korisnickoIme: string): Observable<boolean> {
    const url = environment.API_URL + `autentifikacija/dostupnost/korisnicko-ime/${korisnickoIme}`;
    return this.http.get<boolean>(url);
  }

  public provjeriDostupnostEmaila(email: string): Observable<boolean> {
    const url = environment.API_URL + `autentifikacija/dostupnost/email/${email}`;
    return this.http.get<boolean>(url);
  }

  public registracija(zahtjev: RegistracijaZahtjev) {
    return this.http.post(environment.API_URL + 'autentifikacija/registracija', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public aktivacija(zahtjev: AktivacijaZahtjev): Observable<PrijavaOdgovor> {
    return this.http.post<PrijavaOdgovor>(environment.API_URL + 'autentifikacija/aktivacija', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public prijava(zahtjev: PrijavaZahtjev): Observable<PrijavaOdgovor> {
    return this.http.post<PrijavaOdgovor>(environment.API_URL + 'autentifikacija/prijava', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  // Korisnici

  public vratiPodatkeKorisnika(korisnickoIme: string): Observable<KorisnikInfo> {
    const url = environment.API_URL + `korisnik/${korisnickoIme}`;
    return this.http.get<KorisnikInfo>(url);
  }

  public azurirajPodatkeKorisnika(zahtjev: KorisnikInfo) {
    return this.http.post(environment.API_URL + 'korisnik/azuriraj', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  // Kategorije fitnes programa

  public vratiSveKategorije(): Observable<Set<KategorijaOdgovor>> {
    const url = environment.API_URL + `kategorije`;
    return this.http.get<Set<KategorijaOdgovor>>(url);
  }

  public vratiSpecifikacijePoIdKategorije(kategorijaId: number): Observable<Set<SpecifikacijaOdgovor>> {
    const url = environment.API_URL + `kategorije/${kategorijaId}/specifikacije`;
    return this.http.get<Set<SpecifikacijaOdgovor>>(url);
  }

  // Programi

  public kreirajFitnesProgram(zahtjev: FitnesProgramZahtjev): Observable<number> {
    return this.http.post<number>(environment.API_URL + 'programi', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public odustaniOdKreiranjaFitnesPrograma(fitnesProgramId: number) {
    const url = environment.API_URL + `programi/${fitnesProgramId}`;
    return this.http.delete(url);
  }

  public dodajFitnesProgramSpecifikaciju(zahtjev: FitnesProgramSpecifikacijaZahtjev) {
    return this.http.post(environment.API_URL + 'programi/specifikacije', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public vratiKarticeAktivnihFitnesProgramaKorisnika(korisnickoIme: string, trenutnaStranica: number, brojElemenata: number) {
    const params = new HttpParams()
      .set('trenutnaStranica', trenutnaStranica.toString())
      .set('brojElemenata', brojElemenata.toString());
    return this.http.get<any>(environment.API_URL + `programi/kartice/aktivno/korisnik/${korisnickoIme}`, { params });
  }

  public vratiKarticeZavrsenihFitnesProgramaKorisnika(korisnickoIme: string, trenutnaStranica: number, brojElemenata: number) {
    const params = new HttpParams()
      .set('trenutnaStranica', trenutnaStranica.toString())
      .set('brojElemenata', brojElemenata.toString());
    return this.http.get<any>(environment.API_URL + `programi/kartice/zavrseno/korisnik/${korisnickoIme}`, { params });
  }

  public vratiKarticeKupljenihFitnesProgramaKorisnika(korisnickoIme: string, trenutnaStranica: number, brojElemenata: number) {
    const params = new HttpParams()
      .set('trenutnaStranica', trenutnaStranica.toString())
      .set('brojElemenata', brojElemenata.toString());
    return this.http.get<any>(environment.API_URL + `programi/kartice/kupljeno/korisnik/${korisnickoIme}`, { params });
  }

  public vratiStatistikuFitnesPrograma(korisnickoIme: string): Observable<FitnesProgramStatistikaOdgovor> {
    const url = environment.API_URL + `programi/statistika/${korisnickoIme}`;
    return this.http.get<FitnesProgramStatistikaOdgovor>(url);
  }

  public vratiSveKarticeFitnesPrograma(trenutnaStranica: number, brojElemenata: number, sortiranje: string) {
    const params = new HttpParams()
      .set('trenutnaStranica', trenutnaStranica.toString())
      .set('brojElemenata', brojElemenata.toString())
      .set('sortiranje', sortiranje);
    return this.http.get<any>(environment.API_URL + 'programi/javni/kartice', { params });
  }

  public vratiFitnesProgram(fitnesPrograId: number): Observable<FitnesProgramDetaljiOdgovor> {
    const url = environment.API_URL + `programi/javni/${fitnesPrograId}`;
    return this.http.get<FitnesProgramDetaljiOdgovor>(url);
  }

  public zavrsiFitnesProgram(korisnickoIme: string, fitnesPrograId: number) {
    const url = environment.API_URL + `programi/${korisnickoIme}/${fitnesPrograId}`;
    return this.http.put(url, {});
  }

  public obrisiFitnesProgram(korisnickoIme: string, fitnesPrograId: number) {
    const url = environment.API_URL + `programi/${korisnickoIme}/${fitnesPrograId}`;
    return this.http.delete(url);
  }

  // Pretraga

  public pretragaKarticaFitnesPrograma(zahtjev: PretragaZahtjev, trenutnaStranica: number, brojElemenata: number) {
    const params = new HttpParams()
      .set('trenutnaStranica', trenutnaStranica.toString())
      .set('brojElemenata', brojElemenata.toString());
    return this.http.post<any>(environment.API_URL + 'programi/javni/kartice/pretraga', zahtjev, { params });
  }

  // Slike

  public dodajSlike(file: File, fitnesProgramId: number, isPoster: boolean): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    const posterPrefiks = isPoster ? '1_' : '0_';
    const modifikovanoImeSlike = fitnesProgramId + "_" + posterPrefiks + file.name;
    const modifikovanFile = new File([file], modifikovanoImeSlike, { type: file.type });
    formData.append('file', modifikovanFile);
    const req = new HttpRequest('POST', environment.API_URL + 'programi/slika', formData, {
      reportProgress: true
    });
    return this.http.request(req);
  }
  // kao da ne treba
  public vratiPoster(fitnesProgramId: number): Observable<SlikaInfo> {
    const url = environment.API_URL + `programi/javni/slika/${fitnesProgramId}/poster`;
    return this.http.get<SlikaInfo>(url);
  }

  // Komentari

  public vratiSveKomentare(fitnesProgramId: number, trenutnaStranica: number, brojElemenata: number) {
    const params = new HttpParams()
      .set('trenutnaStranica', trenutnaStranica.toString())
      .set('brojElemenata', brojElemenata.toString());
    return this.http.get<any>(environment.API_URL + `programi/komentar/${fitnesProgramId}`, { params });
  }

  public dodajKomentar(zahtjev: FitnesProgramKomentarZahtjev) {
    return this.http.post(environment.API_URL + 'programi/komentar', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  // Kupovina

  public kupi(zahtjev: KupovinaZahtjev): Observable<string> {
    return this.http.post(environment.API_URL + 'kupovina', zahtjev, { responseType: 'text' });
  }

  // Poruke savjetnika

  public vratiImenaSavjetnika(): Observable<Set<string>> {
    return this.http.get<Set<string>>(environment.API_URL + 'porukeSavjetnika/imena');
  }

  public posaljiPorukuSavjetniku(zahtjev: PorukaSavjetnikZahtjev) {
    return this.http.post(environment.API_URL + 'porukeSavjetnika', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  // Poruke korisnika

  public vratiKorisnickaImena(korisnickoIme: string): Observable<Set<string>> {
    return this.http.get<Set<string>>(environment.API_URL + `porukeKorisnika/imena/${korisnickoIme}`);
  }

  public posaljiPorukuKorisniku(zahtjev: PorukaKorisnikZahtjev) {
    return this.http.post(environment.API_URL + 'porukeKorisnika', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public vratiPorukeKorisnika(korisnickoImePrviUcesnik: string, korisnickoImeDrugiUcesnik: string): Observable<Set<PorukaKorisnikOdgovor>> {
    const url = environment.API_URL + `porukeKorisnika/poruke/${korisnickoImePrviUcesnik}/${korisnickoImeDrugiUcesnik}`;
    console.log(url);
    return this.http.get<Set<PorukaKorisnikOdgovor>>(url);
  }

  // Vijesti

  public vratiNajnovijeVijesti(): Observable<Set<VijestiOdgovor>> {
    return this.http.get<Set<VijestiOdgovor>>(environment.API_URL + 'vijesti');
  }

  // Pretplata

  public pretplatiKorisnika(korisnickoIme: string, kategorijaId: number) {
    const url = environment.API_URL + `pretplata/${korisnickoIme}/${kategorijaId}`;
    return this.http.put(url, {});
  }

  // Aktivnosti

  public kreirajAktivnsot(zahtjev: AktivnostZahtjev) {
    return this.http.post(environment.API_URL + 'aktivnosti', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public vratiAktivnostiKorisnikaPoDatumu(korisnickoIme: string, datum: string): Observable<Set<AktivnostOdgovor>> {
    const url = environment.API_URL + `aktivnosti/${korisnickoIme}/${datum}`;
    return this.http.get<Set<AktivnostOdgovor>>(url);
  }

  public vratiDnevnikKorisnika(korisnickoIme: string): Observable<string> {
    const url = environment.API_URL + `aktivnosti/dnevnik/${korisnickoIme}`;
    return this.http.get(url, { responseType: 'text' });
  }

  public vratiInformacijeOAktivnostimaKorisnika(korisnickoIme: string): Observable<Set<AktivnostInfo>> {
    const url = environment.API_URL + `aktivnosti/info/${korisnickoIme}`;
    return this.http.get<Set<AktivnostInfo>>(url);
  }

  public zavrsiAktivnost(id: number) {
    const url = environment.API_URL + `aktivnosti/zavrsi/${id}`;
    return this.http.put(url, {});
  }

  // Masa korisnika

  public unesiMasu(zahtjev: MasaZahtjev): Observable<boolean> {
    return this.http.post<boolean>(environment.API_URL + 'aktivnosti/masa', zahtjev).pipe(
      catchError((error: HttpErrorResponse) => {
        return throwError(() => error)
      })
    );
  }

  public vratiSveIzmjereneMaseKorisnika(korisnickoIme: string): Observable<Set<MasaOdgovor>> {
    const url = environment.API_URL + `aktivnosti/masa/${korisnickoIme}`;
    return this.http.get<Set<MasaOdgovor>>(url);
  }

  // Predlozi

  public vratiPredloge(): Observable<Set<PredlogVjezbeOdgovor>> {
    const url = environment.API_URL + 'predlozi';
    return this.http.get<Set<PredlogVjezbeOdgovor>>(url);
  }

}
