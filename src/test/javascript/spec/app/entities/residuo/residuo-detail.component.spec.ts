/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ResiduoDetailComponent } from 'app/entities/residuo/residuo-detail.component';
import { Residuo } from 'app/shared/model/residuo.model';

describe('Component Tests', () => {
    describe('Residuo Management Detail Component', () => {
        let comp: ResiduoDetailComponent;
        let fixture: ComponentFixture<ResiduoDetailComponent>;
        const route = ({ data: of({ residuo: new Residuo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ResiduoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResiduoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResiduoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.residuo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
