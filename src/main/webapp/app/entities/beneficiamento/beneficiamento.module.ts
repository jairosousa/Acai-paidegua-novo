import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    BeneficiamentoComponent,
    BeneficiamentoDetailComponent,
    BeneficiamentoUpdateComponent,
    BeneficiamentoDeletePopupComponent,
    BeneficiamentoDeleteDialogComponent,
    beneficiamentoRoute,
    beneficiamentoPopupRoute
} from './';

const ENTITY_STATES = [...beneficiamentoRoute, ...beneficiamentoPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BeneficiamentoComponent,
        BeneficiamentoDetailComponent,
        BeneficiamentoUpdateComponent,
        BeneficiamentoDeleteDialogComponent,
        BeneficiamentoDeletePopupComponent
    ],
    entryComponents: [
        BeneficiamentoComponent,
        BeneficiamentoUpdateComponent,
        BeneficiamentoDeleteDialogComponent,
        BeneficiamentoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaBeneficiamentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
