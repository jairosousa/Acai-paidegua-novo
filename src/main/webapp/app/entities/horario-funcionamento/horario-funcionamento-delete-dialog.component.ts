import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHorarioFuncionamento } from 'app/shared/model/horario-funcionamento.model';
import { HorarioFuncionamentoService } from './horario-funcionamento.service';

@Component({
    selector: 'jhi-horario-funcionamento-delete-dialog',
    templateUrl: './horario-funcionamento-delete-dialog.component.html'
})
export class HorarioFuncionamentoDeleteDialogComponent {
    horarioFuncionamento: IHorarioFuncionamento;

    constructor(
        protected horarioFuncionamentoService: HorarioFuncionamentoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.horarioFuncionamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'horarioFuncionamentoListModification',
                content: 'Deleted an horarioFuncionamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-horario-funcionamento-delete-popup',
    template: ''
})
export class HorarioFuncionamentoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ horarioFuncionamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HorarioFuncionamentoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.horarioFuncionamento = horarioFuncionamento;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/horario-funcionamento', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/horario-funcionamento', { outlets: { popup: null } }]);
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
