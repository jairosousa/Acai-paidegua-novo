/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { CaracteristicaComponent } from 'app/entities/caracteristica/caracteristica.component';
import { CaracteristicaService } from 'app/entities/caracteristica/caracteristica.service';
import { Caracteristica } from 'app/shared/model/caracteristica.model';

describe('Component Tests', () => {
    describe('Caracteristica Management Component', () => {
        let comp: CaracteristicaComponent;
        let fixture: ComponentFixture<CaracteristicaComponent>;
        let service: CaracteristicaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [CaracteristicaComponent],
                providers: []
            })
                .overrideTemplate(CaracteristicaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CaracteristicaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaracteristicaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Caracteristica(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.caracteristicas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
