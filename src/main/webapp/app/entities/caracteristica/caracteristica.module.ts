import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    CaracteristicaComponent,
    CaracteristicaDetailComponent,
    CaracteristicaUpdateComponent,
    CaracteristicaDeletePopupComponent,
    CaracteristicaDeleteDialogComponent,
    caracteristicaRoute,
    caracteristicaPopupRoute
} from './';

const ENTITY_STATES = [...caracteristicaRoute, ...caracteristicaPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CaracteristicaComponent,
        CaracteristicaDetailComponent,
        CaracteristicaUpdateComponent,
        CaracteristicaDeleteDialogComponent,
        CaracteristicaDeletePopupComponent
    ],
    entryComponents: [
        CaracteristicaComponent,
        CaracteristicaUpdateComponent,
        CaracteristicaDeleteDialogComponent,
        CaracteristicaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaCaracteristicaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
