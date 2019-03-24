/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { RedeSociaisUpdateComponent } from 'app/entities/rede-sociais/rede-sociais-update.component';
import { RedeSociaisService } from 'app/entities/rede-sociais/rede-sociais.service';
import { RedeSociais } from 'app/shared/model/rede-sociais.model';

describe('Component Tests', () => {
    describe('RedeSociais Management Update Component', () => {
        let comp: RedeSociaisUpdateComponent;
        let fixture: ComponentFixture<RedeSociaisUpdateComponent>;
        let service: RedeSociaisService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [RedeSociaisUpdateComponent]
            })
                .overrideTemplate(RedeSociaisUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RedeSociaisUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RedeSociaisService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RedeSociais(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.redeSociais = entity;
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
                    const entity = new RedeSociais();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.redeSociais = entity;
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
