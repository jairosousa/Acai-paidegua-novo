/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ImagemUpdateComponent } from 'app/entities/imagem/imagem-update.component';
import { ImagemService } from 'app/entities/imagem/imagem.service';
import { Imagem } from 'app/shared/model/imagem.model';

describe('Component Tests', () => {
    describe('Imagem Management Update Component', () => {
        let comp: ImagemUpdateComponent;
        let fixture: ComponentFixture<ImagemUpdateComponent>;
        let service: ImagemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ImagemUpdateComponent]
            })
                .overrideTemplate(ImagemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImagemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Imagem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.imagem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Imagem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.imagem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
