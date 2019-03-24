import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    ResiduoComponent,
    ResiduoDetailComponent,
    ResiduoUpdateComponent,
    ResiduoDeletePopupComponent,
    ResiduoDeleteDialogComponent,
    residuoRoute,
    residuoPopupRoute
} from './';

const ENTITY_STATES = [...residuoRoute, ...residuoPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResiduoComponent,
        ResiduoDetailComponent,
        ResiduoUpdateComponent,
        ResiduoDeleteDialogComponent,
        ResiduoDeletePopupComponent
    ],
    entryComponents: [ResiduoComponent, ResiduoUpdateComponent, ResiduoDeleteDialogComponent, ResiduoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaResiduoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
