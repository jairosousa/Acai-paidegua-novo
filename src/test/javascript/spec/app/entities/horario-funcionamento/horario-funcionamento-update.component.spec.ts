/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { HorarioFuncionamentoUpdateComponent } from 'app/entities/horario-funcionamento/horario-funcionamento-update.component';
import { HorarioFuncionamentoService } from 'app/entities/horario-funcionamento/horario-funcionamento.service';
import { HorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';

describe('Component Tests', () => {
    describe('HorarioFuncionamento Management Update Component', () => {
        let comp: HorarioFuncionamentoUpdateComponent;
        let fixture: ComponentFixture<HorarioFuncionamentoUpdateComponent>;
        let service: HorarioFuncionamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [HorarioFuncionamentoUpdateComponent]
            })
                .overrideTemplate(HorarioFuncionamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HorarioFuncionamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorarioFuncionamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HorarioFuncionamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.horarioFuncionamento = entity;
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
                    const entity = new HorarioFuncionamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.horarioFuncionamento = entity;
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
