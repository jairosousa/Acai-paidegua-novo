/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { RedeSociaisDeleteDialogComponent } from 'app/entities/rede-sociais/rede-sociais-delete-dialog.component';
import { RedeSociaisService } from 'app/entities/rede-sociais/rede-sociais.service';

describe('Component Tests', () => {
    describe('RedeSociais Management Delete Component', () => {
        let comp: RedeSociaisDeleteDialogComponent;
        let fixture: ComponentFixture<RedeSociaisDeleteDialogComponent>;
        let service: RedeSociaisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [RedeSociaisDeleteDialogComponent]
            })
                .overrideTemplate(RedeSociaisDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RedeSociaisDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RedeSociaisService);
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
