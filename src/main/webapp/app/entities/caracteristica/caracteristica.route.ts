import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Caracteristica } from 'app/shared/model/caracteristica.model';
import { CaracteristicaService } from './caracteristica.service';
import { CaracteristicaComponent } from './caracteristica.component';
import { CaracteristicaDetailComponent } from './caracteristica-detail.component';
import { CaracteristicaUpdateComponent } from './caracteristica-update.component';
import { CaracteristicaDeletePopupComponent } from './caracteristica-delete-dialog.component';
import { ICaracteristica } from 'app/shared/model/caracteristica.model';

@Injectable({ providedIn: 'root' })
export class CaracteristicaResolve implements Resolve<ICaracteristica> {
    constructor(private service: CaracteristicaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICaracteristica> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Caracteristica>) => response.ok),
                map((caracteristica: HttpResponse<Caracteristica>) => caracteristica.body)
            );
        }
        return of(new Caracteristica());
    }
}

export const caracteristicaRoute: Routes = [
    {
        path: '',
        component: CaracteristicaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.caracteristica.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CaracteristicaDetailComponent,
        resolve: {
            caracteristica: CaracteristicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.caracteristica.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CaracteristicaUpdateComponent,
        resolve: {
            caracteristica: CaracteristicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.caracteristica.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CaracteristicaUpdateComponent,
        resolve: {
            caracteristica: CaracteristicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.caracteristica.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const caracteristicaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CaracteristicaDeletePopupComponent,
        resolve: {
            caracteristica: CaracteristicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.caracteristica.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
