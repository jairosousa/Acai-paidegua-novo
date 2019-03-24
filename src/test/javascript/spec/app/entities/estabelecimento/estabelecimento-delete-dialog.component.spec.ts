/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EstabelecimentoDeleteDialogComponent } from 'app/entities/estabelecimento/estabelecimento-delete-dialog.component';
import { EstabelecimentoService } from 'app/entities/estabelecimento/estabelecimento.service';

describe('Component Tests', () => {
    describe('Estabelecimento Management Delete Component', () => {
        let comp: EstabelecimentoDeleteDialogComponent;
        let fixture: ComponentFixture<EstabelecimentoDeleteDialogComponent>;
        let service: EstabelecimentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EstabelecimentoDeleteDialogComponent]
            })
                .overrideTemplate(EstabelecimentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstabelecimentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstabelecimentoService);
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
