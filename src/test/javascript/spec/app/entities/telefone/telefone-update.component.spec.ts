/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { TelefoneUpdateComponent } from 'app/entities/telefone/telefone-update.component';
import { TelefoneService } from 'app/entities/telefone/telefone.service';
import { Telefone } from 'app/shared/model/telefone.model';

describe('Component Tests', () => {
    describe('Telefone Management Update Component', () => {
        let comp: TelefoneUpdateComponent;
        let fixture: ComponentFixture<TelefoneUpdateComponent>;
        let service: TelefoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [TelefoneUpdateComponent]
            })
                .overrideTemplate(TelefoneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TelefoneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TelefoneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Telefone(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.telefone = entity;
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
                    const entity = new Telefone();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.telefone = entity;
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
