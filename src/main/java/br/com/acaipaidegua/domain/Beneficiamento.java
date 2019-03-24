package br.com.acaipaidegua.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.com.acaipaidegua.domain.enumeration.Periodo;

import br.com.acaipaidegua.domain.enumeration.Unidade;

/**
 * A Beneficiamento.
 */
@Entity
@Table(name = "beneficiamento")
public class Beneficiamento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "periodo", nullable = false)
    private Periodo periodo;

    @NotNull
    @Column(name = "quantidade", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unidade", nullable = false)
    private Unidade unidade;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @OneToOne
    @JoinColumn(unique = true)
    private Residuo residuo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Beneficiamento periodo(Periodo periodo) {
        this.periodo = periodo;
        return this;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public Beneficiamento quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public Beneficiamento unidade(Unidade unidade) {
        this.unidade = unidade;
        return this;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Beneficiamento total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Residuo getResiduo() {
        return residuo;
    }

    public Beneficiamento residuo(Residuo residuo) {
        this.residuo = residuo;
        return this;
    }

    public void setResiduo(Residuo residuo) {
        this.residuo = residuo;
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
        Beneficiamento beneficiamento = (Beneficiamento) o;
        if (beneficiamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beneficiamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Beneficiamento{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            ", quantidade=" + getQuantidade() +
            ", unidade='" + getUnidade() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
