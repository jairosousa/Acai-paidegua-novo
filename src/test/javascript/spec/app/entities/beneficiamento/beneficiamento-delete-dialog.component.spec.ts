/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { BeneficiamentoDeleteDialogComponent } from 'app/entities/beneficiamento/beneficiamento-delete-dialog.component';
import { BeneficiamentoService } from 'app/entities/beneficiamento/beneficiamento.service';

describe('Component Tests', () => {
    describe('Beneficiamento Management Delete Component', () => {
        let comp: BeneficiamentoDeleteDialogComponent;
        let fixture: ComponentFixture<BeneficiamentoDeleteDialogComponent>;
        let service: BeneficiamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [BeneficiamentoDeleteDialogComponent]
            })
                .overrideTemplate(BeneficiamentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BeneficiamentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeneficiamentoService);
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
