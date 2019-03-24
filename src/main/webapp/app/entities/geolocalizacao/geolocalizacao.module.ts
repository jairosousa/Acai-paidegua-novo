import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewacaipaideguaSharedModule } from 'app/shared';
import {
    GeolocalizacaoComponent,
    GeolocalizacaoDetailComponent,
    GeolocalizacaoUpdateComponent,
    GeolocalizacaoDeletePopupComponent,
    GeolocalizacaoDeleteDialogComponent,
    geolocalizacaoRoute,
    geolocalizacaoPopupRoute
} from './';

const ENTITY_STATES = [...geolocalizacaoRoute, ...geolocalizacaoPopupRoute];

@NgModule({
    imports: [NewacaipaideguaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GeolocalizacaoComponent,
        GeolocalizacaoDetailComponent,
        GeolocalizacaoUpdateComponent,
        GeolocalizacaoDeleteDialogComponent,
        GeolocalizacaoDeletePopupComponent
    ],
    entryComponents: [
        GeolocalizacaoComponent,
        GeolocalizacaoUpdateComponent,
        GeolocalizacaoDeleteDialogComponent,
        GeolocalizacaoDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaGeolocalizacaoModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
