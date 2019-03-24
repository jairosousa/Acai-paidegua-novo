import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Geolocalizacao } from 'app/shared/model/geolocalizacao.model';
import { GeolocalizacaoService } from './geolocalizacao.service';
import { GeolocalizacaoComponent } from './geolocalizacao.component';
import { GeolocalizacaoDetailComponent } from './geolocalizacao-detail.component';
import { GeolocalizacaoUpdateComponent } from './geolocalizacao-update.component';
import { GeolocalizacaoDeletePopupComponent } from './geolocalizacao-delete-dialog.component';
import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';

@Injectable({ providedIn: 'root' })
export class GeolocalizacaoResolve implements Resolve<IGeolocalizacao> {
    constructor(private service: GeolocalizacaoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGeolocalizacao> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Geolocalizacao>) => response.ok),
                map((geolocalizacao: HttpResponse<Geolocalizacao>) => geolocalizacao.body)
            );
        }
        return of(new Geolocalizacao());
    }
}

export const geolocalizacaoRoute: Routes = [
    {
        path: '',
        component: GeolocalizacaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.geolocalizacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: GeolocalizacaoDetailComponent,
        resolve: {
            geolocalizacao: GeolocalizacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.geolocalizacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: GeolocalizacaoUpdateComponent,
        resolve: {
            geolocalizacao: GeolocalizacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.geolocalizacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: GeolocalizacaoUpdateComponent,
        resolve: {
            geolocalizacao: GeolocalizacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.geolocalizacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const geolocalizacaoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: GeolocalizacaoDeletePopupComponent,
        resolve: {
            geolocalizacao: GeolocalizacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.geolocalizacao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
