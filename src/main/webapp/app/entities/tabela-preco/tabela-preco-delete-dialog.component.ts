import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITabelaPreco } from 'app/shared/model/tabela-preco.model';
import { TabelaPrecoService } from './tabela-preco.service';

@Component({
    selector: 'jhi-tabela-preco-delete-dialog',
    templateUrl: './tabela-preco-delete-dialog.component.html'
})
export class TabelaPrecoDeleteDialogComponent {
    tabelaPreco: ITabelaPreco;

    constructor(
        protected tabelaPrecoService: TabelaPrecoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tabelaPrecoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tabelaPrecoListModification',
                content: 'Deleted an tabelaPreco'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tabela-preco-delete-popup',
    template: ''
})
export class TabelaPrecoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tabelaPreco }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TabelaPrecoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.tabelaPreco = tabelaPreco;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/tabela-preco', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/tabela-preco', { outlets: { popup: null } }]);
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
