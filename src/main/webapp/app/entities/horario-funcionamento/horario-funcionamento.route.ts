import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';
import { HorarioFuncionamentoService } from './horario-funcionamento.service';
import { HorarioFuncionamentoComponent } from './horario-funcionamento.component';
import { HorarioFuncionamentoDetailComponent } from './horario-funcionamento-detail.component';
import { HorarioFuncionamentoUpdateComponent } from './horario-funcionamento-update.component';
import { HorarioFuncionamentoDeletePopupComponent } from './horario-funcionamento-delete-dialog.component';
import { IHorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';

@Injectable({ providedIn: 'root' })
export class HorarioFuncionamentoResolve implements Resolve<IHorarioFuncionamento> {
    constructor(private service: HorarioFuncionamentoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHorarioFuncionamento> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<HorarioFuncionamento>) => response.ok),
                map((horarioFuncionamento: HttpResponse<HorarioFuncionamento>) => horarioFuncionamento.body)
            );
        }
        return of(new HorarioFuncionamento());
    }
}

export const horarioFuncionamentoRoute: Routes = [
    {
        path: '',
        component: HorarioFuncionamentoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.horarioFuncionamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: HorarioFuncionamentoDetailComponent,
        resolve: {
            horarioFuncionamento: HorarioFuncionamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.horarioFuncionamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: HorarioFuncionamentoUpdateComponent,
        resolve: {
            horarioFuncionamento: HorarioFuncionamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.horarioFuncionamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: HorarioFuncionamentoUpdateComponent,
        resolve: {
            horarioFuncionamento: HorarioFuncionamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.horarioFuncionamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const horarioFuncionamentoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: HorarioFuncionamentoDeletePopupComponent,
        resolve: {
            horarioFuncionamento: HorarioFuncionamentoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.horarioFuncionamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
