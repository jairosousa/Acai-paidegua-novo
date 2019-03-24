import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstabelecimento } from 'app/shared/model/estabelecimento.model';
import { EstabelecimentoService } from './estabelecimento.service';

@Component({
    selector: 'jhi-estabelecimento-delete-dialog',
    templateUrl: './estabelecimento-delete-dialog.component.html'
})
export class EstabelecimentoDeleteDialogComponent {
    estabelecimento: IEstabelecimento;

    constructor(
        protected estabelecimentoService: EstabelecimentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estabelecimentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estabelecimentoListModification',
                content: 'Deleted an estabelecimento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estabelecimento-delete-popup',
    template: ''
})
export class EstabelecimentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estabelecimento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstabelecimentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.estabelecimento = estabelecimento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/estabelecimento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/estabelecimento', { outlets: { popup: null } }]);
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
