package br.com.acaipaidegua.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Caracteristica.
 */
@Entity
@Table(name = "caracteristica")
public class Caracteristica implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "possui_selo", nullable = false)
    private Boolean possuiSelo;

    @NotNull
    @Column(name = "possui_restaurante", nullable = false)
    private Boolean possuiRestaurante;

    @NotNull
    @Column(name = "area_producao_isolada", nullable = false)
    private Boolean areaProducaoIsolada;

    @NotNull
    @Column(name = "cobra_taxa_entrega", nullable = false)
    private Boolean cobraTaxaEntrega;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "observacao")
    private String observacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPossuiSelo() {
        return possuiSelo;
    }

    public Caracteristica possuiSelo(Boolean possuiSelo) {
        this.possuiSelo = possuiSelo;
        return this;
    }

    public void setPossuiSelo(Boolean possuiSelo) {
        this.possuiSelo = possuiSelo;
    }

    public Boolean isPossuiRestaurante() {
        return possuiRestaurante;
    }

    public Caracteristica possuiRestaurante(Boolean possuiRestaurante) {
        this.possuiRestaurante = possuiRestaurante;
        return this;
    }

    public void setPossuiRestaurante(Boolean possuiRestaurante) {
        this.possuiRestaurante = possuiRestaurante;
    }

    public Boolean isAreaProducaoIsolada() {
        return areaProducaoIsolada;
    }

    public Caracteristica areaProducaoIsolada(Boolean areaProducaoIsolada) {
        this.areaProducaoIsolada = areaProducaoIsolada;
        return this;
    }

    public void setAreaProducaoIsolada(Boolean areaProducaoIsolada) {
        this.areaProducaoIsolada = areaProducaoIsolada;
    }

    public Boolean isCobraTaxaEntrega() {
        return cobraTaxaEntrega;
    }

    public Caracteristica cobraTaxaEntrega(Boolean cobraTaxaEntrega) {
        this.cobraTaxaEntrega = cobraTaxaEntrega;
        return this;
    }

    public void setCobraTaxaEntrega(Boolean cobraTaxaEntrega) {
        this.cobraTaxaEntrega = cobraTaxaEntrega;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Caracteristica valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public Caracteristica observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Caracteristica caracteristica = (Caracteristica) o;
        if (caracteristica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caracteristica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Caracteristica{" +
            "id=" + getId() +
            ", possuiSelo='" + isPossuiSelo() + "'" +
            ", possuiRestaurante='" + isPossuiRestaurante() + "'" +
            ", areaProducaoIsolada='" + isAreaProducaoIsolada() + "'" +
            ", cobraTaxaEntrega='" + isCobraTaxaEntrega() + "'" +
            ", valor=" + getValor() +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
