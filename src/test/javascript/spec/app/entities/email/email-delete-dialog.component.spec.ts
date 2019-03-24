/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { EmailDeleteDialogComponent } from 'app/entities/email/email-delete-dialog.component';
import { EmailService } from 'app/entities/email/email.service';

describe('Component Tests', () => {
    describe('Email Management Delete Component', () => {
        let comp: EmailDeleteDialogComponent;
        let fixture: ComponentFixture<EmailDeleteDialogComponent>;
        let service: EmailService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [EmailDeleteDialogComponent]
            })
                .overrideTemplate(EmailDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmailDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailService);
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
