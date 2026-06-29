import { NgModule } from '@angular/core';
import { Routes, RouterModule, mapToCanActivate } from '@angular/router';
import { MenuComponent } from '../app-layout/menu/menu.component';
import { GuardService } from 'src/app/services/guard.service';
import { MessageComponent } from './message/message.component';

const routes: Routes = [

    {
        path: '',
        component: MessageComponent,
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
export class MessageRoutingModule { }
