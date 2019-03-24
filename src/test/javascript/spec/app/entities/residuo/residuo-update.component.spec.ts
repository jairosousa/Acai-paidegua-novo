/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ResiduoUpdateComponent } from 'app/entities/residuo/residuo-update.component';
import { ResiduoService } from 'app/entities/residuo/residuo.service';
import { Residuo } from 'app/shared/model/residuo.model';

describe('Component Tests', () => {
    describe('Residuo Management Update Component', () => {
        let comp: ResiduoUpdateComponent;
        let fixture: ComponentFixture<ResiduoUpdateComponent>;
        let service: ResiduoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ResiduoUpdateComponent]
            })
                .overrideTemplate(ResiduoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResiduoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResiduoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Residuo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.residuo = entity;
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
                    const entity = new Residuo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.residuo = entity;
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
