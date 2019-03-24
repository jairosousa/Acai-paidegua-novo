import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    TabelaPrecoComponent,
    TabelaPrecoDetailComponent,
    TabelaPrecoUpdateComponent,
    TabelaPrecoDeletePopupComponent,
    TabelaPrecoDeleteDialogComponent,
    tabelaPrecoRoute,
    tabelaPrecoPopupRoute
} from './';

const ENTITY_STATES = [...tabelaPrecoRoute, ...tabelaPrecoPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TabelaPrecoComponent,
        TabelaPrecoDetailComponent,
        TabelaPrecoUpdateComponent,
        TabelaPrecoDeleteDialogComponent,
        TabelaPrecoDeletePopupComponent
    ],
    entryComponents: [TabelaPrecoComponent, TabelaPrecoUpdateComponent, TabelaPrecoDeleteDialogComponent, TabelaPrecoDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaTabelaPrecoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
