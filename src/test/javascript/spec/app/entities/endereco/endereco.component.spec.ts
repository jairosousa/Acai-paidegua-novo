/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EnderecoComponent } from 'app/entities/endereco/endereco.component';
import { EnderecoService } from 'app/entities/endereco/endereco.service';
import { Endereco } from 'app/shared/model/endereco.model';

describe('Component Tests', () => {
    describe('Endereco Management Component', () => {
        let comp: EnderecoComponent;
        let fixture: ComponentFixture<EnderecoComponent>;
        let service: EnderecoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EnderecoComponent],
                providers: []
            })
                .overrideTemplate(EnderecoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnderecoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnderecoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Endereco(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.enderecos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
