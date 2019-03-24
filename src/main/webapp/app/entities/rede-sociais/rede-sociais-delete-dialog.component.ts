import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRedeSociais } from 'app/shared/model/rede-sociais.model';
import { RedeSociaisService } from './rede-sociais.service';

@Component({
    selector: 'jhi-rede-sociais-delete-dialog',
    templateUrl: './rede-sociais-delete-dialog.component.html'
})
export class RedeSociaisDeleteDialogComponent {
    redeSociais: IRedeSociais;

    constructor(
        protected redeSociaisService: RedeSociaisService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.redeSociaisService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'redeSociaisListModification',
                content: 'Deleted an redeSociais'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rede-sociais-delete-popup',
    template: ''
})
export class RedeSociaisDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ redeSociais }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RedeSociaisDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.redeSociais = redeSociais;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rede-sociais', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rede-sociais', { outlets: { popup: null } }]);
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
