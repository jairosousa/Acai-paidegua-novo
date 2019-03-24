import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RedeSociais } from 'app/shared/model/rede-sociais.model';
import { RedeSociaisService } from './rede-sociais.service';
import { RedeSociaisComponent } from './rede-sociais.component';
import { RedeSociaisDetailComponent } from './rede-sociais-detail.component';
import { RedeSociaisUpdateComponent } from './rede-sociais-update.component';
import { RedeSociaisDeletePopupComponent } from './rede-sociais-delete-dialog.component';
import { IRedeSociais } from 'app/shared/model/rede-sociais.model';

@Injectable({ providedIn: 'root' })
export class RedeSociaisResolve implements Resolve<IRedeSociais> {
    constructor(private service: RedeSociaisService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRedeSociais> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RedeSociais>) => response.ok),
                map((redeSociais: HttpResponse<RedeSociais>) => redeSociais.body)
            );
        }
        return of(new RedeSociais());
    }
}

export const redeSociaisRoute: Routes = [
    {
        path: '',
        component: RedeSociaisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.redeSociais.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RedeSociaisDetailComponent,
        resolve: {
            redeSociais: RedeSociaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.redeSociais.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RedeSociaisUpdateComponent,
        resolve: {
            redeSociais: RedeSociaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.redeSociais.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RedeSociaisUpdateComponent,
        resolve: {
            redeSociais: RedeSociaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.redeSociais.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const redeSociaisPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RedeSociaisDeletePopupComponent,
        resolve: {
            redeSociais: RedeSociaisResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.redeSociais.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
