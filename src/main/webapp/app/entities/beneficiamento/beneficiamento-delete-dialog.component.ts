import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeneficiamento } from 'app/shared/model/beneficiamento.model';
import { BeneficiamentoService } from './beneficiamento.service';

@Component({
    selector: 'jhi-beneficiamento-delete-dialog',
    templateUrl: './beneficiamento-delete-dialog.component.html'
})
export class BeneficiamentoDeleteDialogComponent {
    beneficiamento: IBeneficiamento;

    constructor(
        protected beneficiamentoService: BeneficiamentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.beneficiamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'beneficiamentoListModification',
                content: 'Deleted an beneficiamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-beneficiamento-delete-popup',
    template: ''
})
export class BeneficiamentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ beneficiamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BeneficiamentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.beneficiamento = beneficiamento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/beneficiamento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/beneficiamento', { outlets: { popup: null } }]);
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
