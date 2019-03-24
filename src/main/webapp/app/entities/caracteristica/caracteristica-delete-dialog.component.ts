import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICaracteristica } from 'app/shared/model/caracteristica.model';
import { CaracteristicaService } from './caracteristica.service';

@Component({
    selector: 'jhi-caracteristica-delete-dialog',
    templateUrl: './caracteristica-delete-dialog.component.html'
})
export class CaracteristicaDeleteDialogComponent {
    caracteristica: ICaracteristica;

    constructor(
        protected caracteristicaService: CaracteristicaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.caracteristicaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'caracteristicaListModification',
                content: 'Deleted an caracteristica'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-caracteristica-delete-popup',
    template: ''
})
export class CaracteristicaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ caracteristica }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CaracteristicaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.caracteristica = caracteristica;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/caracteristica', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/caracteristica', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
