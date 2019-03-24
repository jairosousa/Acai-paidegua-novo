/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { RedeSociaisComponent } from 'app/entities/rede-sociais/rede-sociais.component';
import { RedeSociaisService } from 'app/entities/rede-sociais/rede-sociais.service';
import { RedeSociais } from 'app/shared/model/rede-sociais.model';

describe('Component Tests', () => {
    describe('RedeSociais Management Component', () => {
        let comp: RedeSociaisComponent;
        let fixture: ComponentFixture<RedeSociaisComponent>;
        let service: RedeSociaisService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [RedeSociaisComponent],
                providers: []
            })
                .overrideTemplate(RedeSociaisComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RedeSociaisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RedeSociaisService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RedeSociais(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.redeSociais[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
