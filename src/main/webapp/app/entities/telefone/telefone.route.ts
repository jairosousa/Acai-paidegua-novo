import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Telefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';
import { TelefoneComponent } from './telefone.component';
import { TelefoneDetailComponent } from './telefone-detail.component';
import { TelefoneUpdateComponent } from './telefone-update.component';
import { TelefoneDeletePopupComponent } from './telefone-delete-dialog.component';
import { ITelefone } from 'app/shared/model/telefone.model';

@Injectable({ providedIn: 'root' })
export class TelefoneResolve implements Resolve<ITelefone> {
    constructor(private service: TelefoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITelefone> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Telefone>) => response.ok),
                map((telefone: HttpResponse<Telefone>) => telefone.body)
            );
        }
        return of(new Telefone());
    }
}

export const telefoneRoute: Routes = [
    {
        path: '',
        component: TelefoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TelefoneDetailComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TelefoneUpdateComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TelefoneUpdateComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const telefonePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TelefoneDeletePopupComponent,
        resolve: {
            telefone: TelefoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.telefone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
