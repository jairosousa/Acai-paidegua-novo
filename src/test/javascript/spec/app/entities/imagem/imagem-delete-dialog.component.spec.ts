/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { ImagemDeleteDialogComponent } from 'app/entities/imagem/imagem-delete-dialog.component';
import { ImagemService } from 'app/entities/imagem/imagem.service';

describe('Component Tests', () => {
    describe('Imagem Management Delete Component', () => {
        let comp: ImagemDeleteDialogComponent;
        let fixture: ComponentFixture<ImagemDeleteDialogComponent>;
        let service: ImagemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [ImagemDeleteDialogComponent]
            })
                .overrideTemplate(ImagemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImagemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImagemService);
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
