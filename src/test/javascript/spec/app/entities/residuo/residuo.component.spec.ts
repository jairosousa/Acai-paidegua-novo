/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ResiduoComponent } from 'app/entities/residuo/residuo.component';
import { ResiduoService } from 'app/entities/residuo/residuo.service';
import { Residuo } from 'app/shared/model/residuo.model';

describe('Component Tests', () => {
    describe('Residuo Management Component', () => {
        let comp: ResiduoComponent;
        let fixture: ComponentFixture<ResiduoComponent>;
        let service: ResiduoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ResiduoComponent],
                providers: []
            })
                .overrideTemplate(ResiduoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResiduoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResiduoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Residuo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.residuos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
