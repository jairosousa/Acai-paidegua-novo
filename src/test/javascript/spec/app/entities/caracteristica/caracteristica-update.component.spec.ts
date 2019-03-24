/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { CaracteristicaUpdateComponent } from 'app/entities/caracteristica/caracteristica-update.component';
import { CaracteristicaService } from 'app/entities/caracteristica/caracteristica.service';
import { Caracteristica } from 'app/shared/model/caracteristica.model';

describe('Component Tests', () => {
    describe('Caracteristica Management Update Component', () => {
        let comp: CaracteristicaUpdateComponent;
        let fixture: ComponentFixture<CaracteristicaUpdateComponent>;
        let service: CaracteristicaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [CaracteristicaUpdateComponent]
            })
                .overrideTemplate(CaracteristicaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaracteristicaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaracteristicaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Caracteristica(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.caracteristica = entity;
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
                    const entity = new Caracteristica();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.caracteristica = entity;
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
