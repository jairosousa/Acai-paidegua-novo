/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ImagemDetailComponent } from 'app/entities/imagem/imagem-detail.component';
import { Imagem } from 'app/shared/model/imagem.model';

describe('Component Tests', () => {
    describe('Imagem Management Detail Component', () => {
        let comp: ImagemDetailComponent;
        let fixture: ComponentFixture<ImagemDetailComponent>;
        const route = ({ data: of({ imagem: new Imagem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ImagemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ImagemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImagemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.imagem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
