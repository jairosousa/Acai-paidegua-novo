/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { BeneficiamentoUpdateComponent } from 'app/entities/beneficiamento/beneficiamento-update.component';
import { BeneficiamentoService } from 'app/entities/beneficiamento/beneficiamento.service';
import { Beneficiamento } from 'app/shared/model/beneficiamento.model';

describe('Component Tests', () => {
    describe('Beneficiamento Management Update Component', () => {
        let comp: BeneficiamentoUpdateComponent;
        let fixture: ComponentFixture<BeneficiamentoUpdateComponent>;
        let service: BeneficiamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [BeneficiamentoUpdateComponent]
            })
                .overrideTemplate(BeneficiamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BeneficiamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeneficiamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Beneficiamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.beneficiamento = entity;
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
                    const entity = new Beneficiamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.beneficiamento = entity;
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
