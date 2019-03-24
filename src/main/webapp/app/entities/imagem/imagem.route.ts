import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Imagem } from 'app/shared/model/imagem.model';
import { ImagemService } from './imagem.service';
import { ImagemComponent } from './imagem.component';
import { ImagemDetailComponent } from './imagem-detail.component';
import { ImagemUpdateComponent } from './imagem-update.component';
import { ImagemDeletePopupComponent } from './imagem-delete-dialog.component';
import { IImagem } from 'app/shared/model/imagem.model';

@Injectable({ providedIn: 'root' })
export class ImagemResolve implements Resolve<IImagem> {
    constructor(private service: ImagemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImagem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Imagem>) => response.ok),
                map((imagem: HttpResponse<Imagem>) => imagem.body)
            );
        }
        return of(new Imagem());
    }
}

export const imagemRoute: Routes = [
    {
        path: '',
        component: ImagemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.imagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ImagemDetailComponent,
        resolve: {
            imagem: ImagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.imagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ImagemUpdateComponent,
        resolve: {
            imagem: ImagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.imagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ImagemUpdateComponent,
        resolve: {
            imagem: ImagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.imagem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ImagemDeletePopupComponent,
        resolve: {
            imagem: ImagemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.imagem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
