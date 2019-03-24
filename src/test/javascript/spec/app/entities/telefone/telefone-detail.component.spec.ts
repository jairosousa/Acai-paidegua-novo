/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { TelefoneDetailComponent } from 'app/entities/telefone/telefone-detail.component';
import { Telefone } from 'app/shared/model/telefone.model';

describe('Component Tests', () => {
    describe('Telefone Management Detail Component', () => {
        let comp: TelefoneDetailComponent;
        let fixture: ComponentFixture<TelefoneDetailComponent>;
        const route = ({ data: of({ telefone: new Telefone(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [TelefoneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TelefoneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TelefoneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.telefone).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
