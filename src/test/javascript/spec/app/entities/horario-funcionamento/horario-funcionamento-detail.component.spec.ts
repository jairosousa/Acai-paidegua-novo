/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { HorarioFuncionamentoDetailComponent } from 'app/entities/horario-funcionamento/horario-funcionamento-detail.component';
import { HorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';

describe('Component Tests', () => {
    describe('HorarioFuncionamento Management Detail Component', () => {
        let comp: HorarioFuncionamentoDetailComponent;
        let fixture: ComponentFixture<HorarioFuncionamentoDetailComponent>;
        const route = ({ data: of({ horarioFuncionamento: new HorarioFuncionamento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [HorarioFuncionamentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HorarioFuncionamentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HorarioFuncionamentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.horarioFuncionamento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
