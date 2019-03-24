import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImagem } from 'app/shared/model/imagem.model';
import { ImagemService } from './imagem.service';

@Component({
    selector: 'jhi-imagem-delete-dialog',
    templateUrl: './imagem-delete-dialog.component.html'
})
export class ImagemDeleteDialogComponent {
    imagem: IImagem;

    constructor(protected imagemService: ImagemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imagemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'imagemListModification',
                content: 'Deleted an imagem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-imagem-delete-popup',
    template: ''
})
export class ImagemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ imagem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ImagemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.imagem = imagem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/imagem', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/imagem', { outlets: { popup: null } }]);
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
