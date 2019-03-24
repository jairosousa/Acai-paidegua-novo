/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EstabelecimentoDetailComponent } from 'app/entities/estabelecimento/estabelecimento-detail.component';
import { Estabelecimento } from 'app/shared/model/estabelecimento.model';

describe('Component Tests', () => {
    describe('Estabelecimento Management Detail Component', () => {
        let comp: EstabelecimentoDetailComponent;
        let fixture: ComponentFixture<EstabelecimentoDetailComponent>;
        const route = ({ data: of({ estabelecimento: new Estabelecimento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EstabelecimentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstabelecimentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstabelecimentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estabelecimento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
