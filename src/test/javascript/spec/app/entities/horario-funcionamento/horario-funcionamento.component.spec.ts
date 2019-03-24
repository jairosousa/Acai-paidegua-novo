/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { HorarioFuncionamentoComponent } from 'app/entities/horario-funcionamento/horario-funcionamento.component';
import { HorarioFuncionamentoService } from 'app/entities/horario-funcionamento/horario-funcionamento.service';
import { HorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';

describe('Component Tests', () => {
    describe('HorarioFuncionamento Management Component', () => {
        let comp: HorarioFuncionamentoComponent;
        let fixture: ComponentFixture<HorarioFuncionamentoComponent>;
        let service: HorarioFuncionamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [HorarioFuncionamentoComponent],
                providers: []
            })
                .overrideTemplate(HorarioFuncionamentoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HorarioFuncionamentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HorarioFuncionamentoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new HorarioFuncionamento(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.horarioFuncionamentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
