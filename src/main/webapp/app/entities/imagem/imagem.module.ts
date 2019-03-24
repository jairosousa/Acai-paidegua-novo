import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    ImagemComponent,
    ImagemDetailComponent,
    ImagemUpdateComponent,
    ImagemDeletePopupComponent,
    ImagemDeleteDialogComponent,
    imagemRoute,
    imagemPopupRoute
} from './';

const ENTITY_STATES = [...imagemRoute, ...imagemPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ImagemComponent, ImagemDetailComponent, ImagemUpdateComponent, ImagemDeleteDialogComponent, ImagemDeletePopupComponent],
    entryComponents: [ImagemComponent, ImagemUpdateComponent, ImagemDeleteDialogComponent, ImagemDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaImagemModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
