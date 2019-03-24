import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'estabelecimento',
                loadChildren: './estabelecimento/estabelecimento.module#NewacaipaideguaEstabelecimentoModule'
            },
            {
                path: 'endereco',
                loadChildren: './endereco/endereco.module#NewacaipaideguaEnderecoModule'
            },
            {
                path: 'geolocalizacao',
                loadChildren: './geolocalizacao/geolocalizacao.module#NewacaipaideguaGeolocalizacaoModule'
            },
            {
                path: 'email',
                loadChildren: './email/email.module#NewacaipaideguaEmailModule'
            },
            {
                path: 'rede-sociais',
                loadChildren: './rede-sociais/rede-sociais.module#NewacaipaideguaRedeSociaisModule'
            },
            {
                path: 'telefone',
                loadChildren: './telefone/telefone.module#NewacaipaideguaTelefoneModule'
            },
            {
                path: 'imagem',
                loadChildren: './imagem/imagem.module#NewacaipaideguaImagemModule'
            },
            {
                path: 'caracteristica',
                loadChildren: './caracteristica/caracteristica.module#NewacaipaideguaCaracteristicaModule'
            },
            {
                path: 'produto',
                loadChildren: './produto/produto.module#NewacaipaideguaProdutoModule'
            },
            {
                path: 'tabela-preco',
                loadChildren: './tabela-preco/tabela-preco.module#NewacaipaideguaTabelaPrecoModule'
            },
            {
                path: 'horario-funcionamento',
                loadChildren: './horario-funcionamento/horario-funcionamento.module#NewacaipaideguaHorarioFuncionamentoModule'
            },
            {
                path: 'beneficiamento',
                loadChildren: './beneficiamento/beneficiamento.module#NewacaipaideguaBeneficiamentoModule'
            },
            {
                path: 'residuo',
                loadChildren: './residuo/residuo.module#NewacaipaideguaResiduoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewacaipaideguaEntityModule {}
