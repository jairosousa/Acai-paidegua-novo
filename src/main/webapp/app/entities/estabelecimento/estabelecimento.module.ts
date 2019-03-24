import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    EstabelecimentoComponent,
    EstabelecimentoDetailComponent,
    EstabelecimentoUpdateComponent,
    EstabelecimentoDeletePopupComponent,
    EstabelecimentoDeleteDialogComponent,
    estabelecimentoRoute,
    estabelecimentoPopupRoute
} from './';

const ENTITY_STATES = [...estabelecimentoRoute, ...estabelecimentoPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstabelecimentoComponent,
        EstabelecimentoDetailComponent,
        EstabelecimentoUpdateComponent,
        EstabelecimentoDeleteDialogComponent,
        EstabelecimentoDeletePopupComponent
    ],
    entryComponents: [
        EstabelecimentoComponent,
        EstabelecimentoUpdateComponent,
        EstabelecimentoDeleteDialogComponent,
        EstabelecimentoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaEstabelecimentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
