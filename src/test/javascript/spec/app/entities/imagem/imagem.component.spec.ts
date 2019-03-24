/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ImagemComponent } from 'app/entities/imagem/imagem.component';
import { ImagemService } from 'app/entities/imagem/imagem.service';
import { Imagem } from 'app/shared/model/imagem.model';

describe('Component Tests', () => {
    describe('Imagem Management Component', () => {
        let comp: ImagemComponent;
        let fixture: ComponentFixture<ImagemComponent>;
        let service: ImagemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ImagemComponent],
                providers: []
            })
                .overrideTemplate(ImagemComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImagemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagemService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Imagem(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.imagems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
