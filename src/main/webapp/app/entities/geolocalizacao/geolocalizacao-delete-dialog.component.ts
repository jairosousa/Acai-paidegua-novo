import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGeolocalizacao } from 'app/shared/model/geolocalizacao.model';
import { GeolocalizacaoService } from './geolocalizacao.service';

@Component({
    selector: 'jhi-geolocalizacao-delete-dialog',
    templateUrl: './geolocalizacao-delete-dialog.component.html'
})
export class GeolocalizacaoDeleteDialogComponent {
    geolocalizacao: IGeolocalizacao;

    constructor(
        protected geolocalizacaoService: GeolocalizacaoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.geolocalizacaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'geolocalizacaoListModification',
                content: 'Deleted an geolocalizacao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-geolocalizacao-delete-popup',
    template: ''
})
export class GeolocalizacaoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ geolocalizacao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GeolocalizacaoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.geolocalizacao = geolocalizacao;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/geolocalizacao', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/geolocalizacao', { outlets: { popup: null } }]);
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
