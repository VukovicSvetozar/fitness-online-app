import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { LoginService } from './login.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuardService {

  constructor(private loginService: LoginService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.loginService.vratiPodatke()) {
      return true;
    }
    this.router.navigate(['']);
    return false;
  }

  canDeactivate({ route, state }: { route: ActivatedRouteSnapshot; state: RouterStateSnapshot; }): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.loginService.vratiPodatke()) {
      this.router.navigate(['']);
      return false;
    }
    return true;
  }

}