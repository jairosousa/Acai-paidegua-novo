import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    EmailComponent,
    EmailDetailComponent,
    EmailUpdateComponent,
    EmailDeletePopupComponent,
    EmailDeleteDialogComponent,
    emailRoute,
    emailPopupRoute
} from './';

const ENTITY_STATES = [...emailRoute, ...emailPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EmailComponent, EmailDetailComponent, EmailUpdateComponent, EmailDeleteDialogComponent, EmailDeletePopupComponent],
    entryComponents: [EmailComponent, EmailUpdateComponent, EmailDeleteDialogComponent, EmailDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaEmailModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
