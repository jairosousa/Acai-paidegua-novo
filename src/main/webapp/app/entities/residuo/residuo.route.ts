import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Residuo } from 'app/shared/model/residuo.model';
import { ResiduoService } from './residuo.service';
import { ResiduoComponent } from './residuo.component';
import { ResiduoDetailComponent } from './residuo-detail.component';
import { ResiduoUpdateComponent } from './residuo-update.component';
import { ResiduoDeletePopupComponent } from './residuo-delete-dialog.component';
import { IResiduo } from 'app/shared/model/residuo.model';

@Injectable({ providedIn: 'root' })
export class ResiduoResolve implements Resolve<IResiduo> {
    constructor(private service: ResiduoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResiduo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Residuo>) => response.ok),
                map((residuo: HttpResponse<Residuo>) => residuo.body)
            );
        }
        return of(new Residuo());
    }
}

export const residuoRoute: Routes = [
    {
        path: '',
        component: ResiduoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.residuo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ResiduoDetailComponent,
        resolve: {
            residuo: ResiduoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.residuo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ResiduoUpdateComponent,
        resolve: {
            residuo: ResiduoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.residuo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ResiduoUpdateComponent,
        resolve: {
            residuo: ResiduoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.residuo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const residuoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ResiduoDeletePopupComponent,
        resolve: {
            residuo: ResiduoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.residuo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
