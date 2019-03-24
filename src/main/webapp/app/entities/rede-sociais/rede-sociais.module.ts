import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    RedeSociaisComponent,
    RedeSociaisDetailComponent,
    RedeSociaisUpdateComponent,
    RedeSociaisDeletePopupComponent,
    RedeSociaisDeleteDialogComponent,
    redeSociaisRoute,
    redeSociaisPopupRoute
} from './';

const ENTITY_STATES = [...redeSociaisRoute, ...redeSociaisPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RedeSociaisComponent,
        RedeSociaisDetailComponent,
        RedeSociaisUpdateComponent,
        RedeSociaisDeleteDialogComponent,
        RedeSociaisDeletePopupComponent
    ],
    entryComponents: [RedeSociaisComponent, RedeSociaisUpdateComponent, RedeSociaisDeleteDialogComponent, RedeSociaisDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaRedeSociaisModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
