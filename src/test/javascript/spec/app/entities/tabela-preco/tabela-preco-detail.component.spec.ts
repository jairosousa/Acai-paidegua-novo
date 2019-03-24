/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { TabelaPrecoDetailComponent } from 'app/entities/tabela-preco/tabela-preco-detail.component';
import { TabelaPreco } from 'app/shared/model/tabela-preco.model';

describe('Component Tests', () => {
    describe('TabelaPreco Management Detail Component', () => {
        let comp: TabelaPrecoDetailComponent;
        let fixture: ComponentFixture<TabelaPrecoDetailComponent>;
        const route = ({ data: of({ tabelaPreco: new TabelaPreco(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [TabelaPrecoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TabelaPrecoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TabelaPrecoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tabelaPreco).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
