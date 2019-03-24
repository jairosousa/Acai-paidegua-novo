/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewacaipaideguaTestModule } from '../../../test.module';
import { CaracteristicaDeleteDialogComponent } from 'app/entities/caracteristica/caracteristica-delete-dialog.component';
import { CaracteristicaService } from 'app/entities/caracteristica/caracteristica.service';

describe('Component Tests', () => {
    describe('Caracteristica Management Delete Component', () => {
        let comp: CaracteristicaDeleteDialogComponent;
        let fixture: ComponentFixture<CaracteristicaDeleteDialogComponent>;
        let service: CaracteristicaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NewacaipaideguaTestModule],
                declarations: [CaracteristicaDeleteDialogComponent]
            })
                .overrideTemplate(CaracteristicaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CaracteristicaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaracteristicaService);
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
