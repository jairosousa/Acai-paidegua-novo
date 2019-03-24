import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Estabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from './estabelecimento.service';
import { EstabelecimentoComponent } from './estabelecimento.component';
import { EstabelecimentoDetailComponent } from './estabelecimento-detail.component';
import { EstabelecimentoUpdateComponent } from './estabelecimento-update.component';
import { EstabelecimentoDeletePopupComponent } from './estabelecimento-delete-dialog.component';
import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';

@Injectable({ providedIn: 'root' })
export class EstabelecimentoResolve implements Resolve<IEstabelecimento> {
    constructor(private service: EstabelecimentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstabelecimento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Estabelecimento>) => response.ok),
                map((estabelecimento: HttpResponse<Estabelecimento>) => estabelecimento.body)
            );
        }
        return of(new Estabelecimento());
    }
}

export const estabelecimentoRoute: Routes = [
    {
        path: '',
        component: EstabelecimentoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'newacaipaideguaApp.estabelecimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EstabelecimentoDetailComponent,
        resolve: {
            estabelecimento: EstabelecimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.estabelecimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EstabelecimentoUpdateComponent,
        resolve: {
            estabelecimento: EstabelecimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.estabelecimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EstabelecimentoUpdateComponent,
        resolve: {
            estabelecimento: EstabelecimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.estabelecimento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estabelecimentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EstabelecimentoDeletePopupComponent,
        resolve: {
            estabelecimento: EstabelecimentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.estabelecimento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
