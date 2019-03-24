/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { GeolocalizacaoUpdateComponent } from 'app/entities/geolocalizacao/geolocalizacao-update.component';
import { GeolocalizacaoService } from 'app/entities/geolocalizacao/geolocalizacao.service';
import { Geolocalizacao } from 'app/shared/model/geolocalizacao.model';

describe('Component Tests', () => {
    describe('Geolocalizacao Management Update Component', () => {
        let comp: GeolocalizacaoUpdateComponent;
        let fixture: ComponentFixture<GeolocalizacaoUpdateComponent>;
        let service: GeolocalizacaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [GeolocalizacaoUpdateComponent]
            })
                .overrideTemplate(GeolocalizacaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GeolocalizacaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GeolocalizacaoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Geolocalizacao(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.geolocalizacao = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Geolocalizacao();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.geolocalizacao = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
