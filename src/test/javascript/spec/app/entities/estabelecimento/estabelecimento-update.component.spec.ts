/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EstabelecimentoUpdateComponent } from 'app/entities/estabelecimento/estabelecimento-update.component';
import { EstabelecimentoService } from 'app/entities/estabelecimento/estabelecimento.service';
import { Estabelecimento } from 'app/shared/model/estabelecimento.model';

describe('Component Tests', () => {
    describe('Estabelecimento Management Update Component', () => {
        let comp: EstabelecimentoUpdateComponent;
        let fixture: ComponentFixture<EstabelecimentoUpdateComponent>;
        let service: EstabelecimentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EstabelecimentoUpdateComponent]
            })
                .overrideTemplate(EstabelecimentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstabelecimentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstabelecimentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Estabelecimento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estabelecimento = entity;
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
                    const entity = new Estabelecimento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.estabelecimento = entity;
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
