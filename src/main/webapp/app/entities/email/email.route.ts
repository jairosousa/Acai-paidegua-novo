import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Email } from 'app/shared/model/email.model';
import { EmailService } from './email.service';
import { EmailComponent } from './email.component';
import { EmailDetailComponent } from './email-detail.component';
import { EmailUpdateComponent } from './email-update.component';
import { EmailDeletePopupComponent } from './email-delete-dialog.component';
import { IEmail } from 'app/shared/model/email.model';

@Injectable({ providedIn: 'root' })
export class EmailResolve implements Resolve<IEmail> {
    constructor(private service: EmailService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmail> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Email>) => response.ok),
                map((email: HttpResponse<Email>) => email.body)
            );
        }
        return of(new Email());
    }
}

export const emailRoute: Routes = [
    {
        path: '',
        component: EmailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EmailDetailComponent,
        resolve: {
            email: EmailResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EmailUpdateComponent,
        resolve: {
            email: EmailResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EmailUpdateComponent,
        resolve: {
            email: EmailResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.email.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EmailDeletePopupComponent,
        resolve: {
            email: EmailResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'newacaipaideguaApp.email.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
