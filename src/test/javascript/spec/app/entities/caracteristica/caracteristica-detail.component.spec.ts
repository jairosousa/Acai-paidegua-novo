/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { CaracteristicaDetailComponent } from 'app/entities/caracteristica/caracteristica-detail.component';
import { Caracteristica } from 'app/shared/model/caracteristica.model';

describe('Component Tests', () => {
    describe('Caracteristica Management Detail Component', () => {
        let comp: CaracteristicaDetailComponent;
        let fixture: ComponentFixture<CaracteristicaDetailComponent>;
        const route = ({ data: of({ caracteristica: new Caracteristica(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [CaracteristicaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CaracteristicaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaracteristicaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.caracteristica).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
