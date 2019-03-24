import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResiduo } from 'app/shared/model/residuo.model';
import { ResiduoService } from './residuo.service';

@Component({
    selector: 'jhi-residuo-delete-dialog',
    templateUrl: './residuo-delete-dialog.component.html'
})
export class ResiduoDeleteDialogComponent {
    residuo: IResiduo;

    constructor(protected residuoService: ResiduoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.residuoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'residuoListModification',
                content: 'Deleted an residuo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-residuo-delete-popup',
    template: ''
})
export class ResiduoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ residuo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResiduoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.residuo = residuo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/residuo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/residuo', { outlets: { popup: null } }]);
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
