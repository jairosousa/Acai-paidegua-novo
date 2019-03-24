/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { TabelaPrecoUpdateComponent } from 'app/entities/tabela-preco/tabela-preco-update.component';
import { TabelaPrecoService } from 'app/entities/tabela-preco/tabela-preco.service';
import { TabelaPreco } from 'app/shared/model/tabela-preco.model';

describe('Component Tests', () => {
    describe('TabelaPreco Management Update Component', () => {
        let comp: TabelaPrecoUpdateComponent;
        let fixture: ComponentFixture<TabelaPrecoUpdateComponent>;
        let service: TabelaPrecoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [TabelaPrecoUpdateComponent]
            })
                .overrideTemplate(TabelaPrecoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TabelaPrecoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabelaPrecoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TabelaPreco(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tabelaPreco = entity;
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
                    const entity = new TabelaPreco();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tabelaPreco = entity;
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
