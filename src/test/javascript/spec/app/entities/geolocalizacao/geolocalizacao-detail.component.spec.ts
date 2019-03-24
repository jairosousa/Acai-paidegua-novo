/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { GeolocalizacaoDetailComponent } from 'app/entities/geolocalizacao/geolocalizacao-detail.component';
import { Geolocalizacao } from 'app/shared/model/geolocalizacao.model';

describe('Component Tests', () => {
    describe('Geolocalizacao Management Detail Component', () => {
        let comp: GeolocalizacaoDetailComponent;
        let fixture: ComponentFixture<GeolocalizacaoDetailComponent>;
        const route = ({ data: of({ geolocalizacao: new Geolocalizacao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [GeolocalizacaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GeolocalizacaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GeolocalizacaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.geolocalizacao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
