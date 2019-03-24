/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { TabelaPrecoDeleteDialogComponent } from 'app/entities/tabela-preco/tabela-preco-delete-dialog.component';
import { TabelaPrecoService } from 'app/entities/tabela-preco/tabela-preco.service';

describe('Component Tests', () => {
    describe('TabelaPreco Management Delete Component', () => {
        let comp: TabelaPrecoDeleteDialogComponent;
        let fixture: ComponentFixture<TabelaPrecoDeleteDialogComponent>;
        let service: TabelaPrecoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [TabelaPrecoDeleteDialogComponent]
            })
                .overrideTemplate(TabelaPrecoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TabelaPrecoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabelaPrecoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
