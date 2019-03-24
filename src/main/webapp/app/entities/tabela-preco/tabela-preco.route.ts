import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TabelaPreco } from 'app/shared/model/tabela-preco.model';
import { TabelaPrecoService } from './tabela-preco.service';
import { TabelaPrecoComponent } from './tabela-preco.component';
import { TabelaPrecoDetailComponent } from './tabela-preco-detail.component';
import { TabelaPrecoUpdateComponent } from './tabela-preco-update.component';
import { TabelaPrecoDeletePopupComponent } from './tabela-preco-delete-dialog.component';
import { ITabelaPreco } from 'app/shared/model/tabela-preco.model';

@Injectable({ providedIn: 'root' })
export class TabelaPrecoResolve implements Resolve<ITabelaPreco> {
    constructor(private service: TabelaPrecoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITabelaPreco> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TabelaPreco>) => response.ok),
                map((tabelaPreco: HttpResponse<TabelaPreco>) => tabelaPreco.body)
            );
        }
        return of(new TabelaPreco());
    }
}

export const tabelaPrecoRoute: Routes = [
    {
        path: '',
        component: TabelaPrecoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'newacaipaideguaApp.tabelaPreco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TabelaPrecoDetailComponent,
        resolve: {
            tabelaPreco: TabelaPrecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.tabelaPreco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TabelaPrecoUpdateComponent,
        resolve: {
            tabelaPreco: TabelaPrecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.tabelaPreco.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TabelaPrecoUpdateComponent,
        resolve: {
            tabelaPreco: TabelaPrecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.tabelaPreco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tabelaPrecoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TabelaPrecoDeletePopupComponent,
        resolve: {
            tabelaPreco: TabelaPrecoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.tabelaPreco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
