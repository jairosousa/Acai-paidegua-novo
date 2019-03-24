import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    HorarioFuncionamentoComponent,
    HorarioFuncionamentoDetailComponent,
    HorarioFuncionamentoUpdateComponent,
    HorarioFuncionamentoDeletePopupComponent,
    HorarioFuncionamentoDeleteDialogComponent,
    horarioFuncionamentoRoute,
    horarioFuncionamentoPopupRoute
} from './';

const ENTITY_STATES = [...horarioFuncionamentoRoute, ...horarioFuncionamentoPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HorarioFuncionamentoComponent,
        HorarioFuncionamentoDetailComponent,
        HorarioFuncionamentoUpdateComponent,
        HorarioFuncionamentoDeleteDialogComponent,
        HorarioFuncionamentoDeletePopupComponent
    ],
    entryComponents: [
        HorarioFuncionamentoComponent,
        HorarioFuncionamentoUpdateComponent,
        HorarioFuncionamentoDeleteDialogComponent,
        HorarioFuncionamentoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaHorarioFuncionamentoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
