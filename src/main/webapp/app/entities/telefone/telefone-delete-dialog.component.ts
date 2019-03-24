import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITelefone } from 'app/shared/model/telefone.model';
import { TelefoneService } from './telefone.service';

@Component({
    selector: 'jhi-telefone-delete-dialog',
    templateUrl: './telefone-delete-dialog.component.html'
})
export class TelefoneDeleteDialogComponent {
    telefone: ITelefone;

    constructor(protected telefoneService: TelefoneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.telefoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'telefoneListModification',
                content: 'Deleted an telefone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-telefone-delete-popup',
    template: ''
})
export class TelefoneDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ telefone }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TelefoneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.telefone = telefone;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/telefone', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/telefone', { outlets: { popup: null } }]);
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
