import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Beneficiamento } from 'app/shared/model/beneficiamento.model';
import { BeneficiamentoService } from './beneficiamento.service';
import { BeneficiamentoComponent } from './beneficiamento.component';
import { BeneficiamentoDetailComponent } from './beneficiamento-detail.component';
import { BeneficiamentoUpdateComponent } from './beneficiamento-update.component';
import { BeneficiamentoDeletePopupComponent } from './beneficiamento-delete-dialog.component';
import { IBeneficiamento } from 'app/shared/model/beneficiamento.model';

@Injectable({ providedIn: 'root' })
export class BeneficiamentoResolve implements Resolve<IBeneficiamento> {
    constructor(private service: BeneficiamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBeneficiamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Beneficiamento>) => response.ok),
                map((beneficiamento: HttpResponse<Beneficiamento>) => beneficiamento.body)
            );
        }
        return of(new Beneficiamento());
    }
}

export const beneficiamentoRoute: Routes = [
    {
        path: '',
        component: BeneficiamentoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'newacaipaideguaApp.beneficiamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BeneficiamentoDetailComponent,
        resolve: {
            beneficiamento: BeneficiamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.beneficiamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BeneficiamentoUpdateComponent,
        resolve: {
            beneficiamento: BeneficiamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.beneficiamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BeneficiamentoUpdateComponent,
        resolve: {
            beneficiamento: BeneficiamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.beneficiamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const beneficiamentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BeneficiamentoDeletePopupComponent,
        resolve: {
            beneficiamento: BeneficiamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.beneficiamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
