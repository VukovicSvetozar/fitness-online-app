import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { LoginService } from 'src/app/services/login.service';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) { }

  intercept(zahtjev: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (!zahtjev.url.includes('/programi/slika')) {
      zahtjev = zahtjev.clone({
        setHeaders: {
          'Content-Type': 'application/json'
        }
      });
    }
    if (!zahtjev.url.includes('/autentifikacija') && !zahtjev.url.includes('/programi/javni')
      && !zahtjev.url.includes('/kategorije') && !zahtjev.url.includes('/vijesti')) {
      const jwtToken = this.loginService.vratiPodatke().jwtToken;
      if (jwtToken) {
        const prilagodjeniZahtjev = zahtjev.clone({
          setHeaders: {
            Authorization: `Bearer ${jwtToken}`
          },
        });
        return next.handle(prilagodjeniZahtjev);
      }
    }
    return next.handle(zahtjev);
  }
  
}
