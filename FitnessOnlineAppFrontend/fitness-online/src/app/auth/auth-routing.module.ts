import { NgModule } from '@angular/core';
import { Routes, RouterModule, mapToCanActivate, mapToCanDeactivate } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { InfoComponent } from './info/info.component';
import { ActivationComponent } from './activation/activation.component';
import { GuardService } from 'src/app/services/guard.service';
import { MenuComponent } from '../app-layout/menu/menu.component';

const routes: Routes = [

    {
        path: 'login',
        component: LoginComponent,
        canActivate: mapToCanDeactivate([GuardService])
    },
    {
        path: 'registration',
        component: RegistrationComponent,
        canActivate: mapToCanDeactivate([GuardService])
    },
    {
        path: 'activation',
        component: ActivationComponent,
        canActivate: mapToCanDeactivate([GuardService])
    },
    {
        path: 'info',
        component: InfoComponent,
        canActivate: mapToCanActivate([GuardService]),
        children: [
            {
                path: '',
                component: MenuComponent,
            }
        ]
    }

];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthRoutingModule { }
