/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { GeolocalizacaoComponent } from 'app/entities/geolocalizacao/geolocalizacao.component';
import { GeolocalizacaoService } from 'app/entities/geolocalizacao/geolocalizacao.service';
import { Geolocalizacao } from 'app/shared/model/geolocalizacao.model';

describe('Component Tests', () => {
    describe('Geolocalizacao Management Component', () => {
        let comp: GeolocalizacaoComponent;
        let fixture: ComponentFixture<GeolocalizacaoComponent>;
        let service: GeolocalizacaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [GeolocalizacaoComponent],
                providers: []
            })
                .overrideTemplate(GeolocalizacaoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GeolocalizacaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeolocalizacaoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Geolocalizacao(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.geolocalizacaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
