/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { BeneficiamentoDetailComponent } from 'app/entities/beneficiamento/beneficiamento-detail.component';
import { Beneficiamento } from 'app/shared/model/beneficiamento.model';

describe('Component Tests', () => {
    describe('Beneficiamento Management Detail Component', () => {
        let comp: BeneficiamentoDetailComponent;
        let fixture: ComponentFixture<BeneficiamentoDetailComponent>;
        const route = ({ data: of({ beneficiamento: new Beneficiamento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [BeneficiamentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BeneficiamentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BeneficiamentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.beneficiamento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
