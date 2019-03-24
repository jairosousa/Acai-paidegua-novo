package br.com.acaipaidegua.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import br.com.acaipaidegua.domain.enumeration.Destino;

/**
 * A Residuo.
 */
@Entity
@Table(name = "residuo")
public class Residuo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "destino", nullable = false)
    private Destino destino;

    @Column(name = "outro")
    private String outro;

    @Column(name = "custo", precision = 10, scale = 2)
    private BigDecimal custo;

    @Column(name = "responsavel")
    private String responsavel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Destino getDestino() {
        return destino;
    }

    public Residuo destino(Destino destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public String getOutro() {
        return outro;
    }

    public Residuo outro(String outro) {
        this.outro = outro;
        return this;
    }

    public void setOutro(String outro) {
        this.outro = outro;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public Residuo custo(BigDecimal custo) {
        this.custo = custo;
        return this;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public Residuo responsavel(String responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
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
        Residuo residuo = (Residuo) o;
        if (residuo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), residuo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Residuo{" +
            "id=" + getId() +
            ", destino='" + getDestino() + "'" +
            ", outro='" + getOutro() + "'" +
            ", custo=" + getCusto() +
            ", responsavel='" + getResponsavel() + "'" +
            "}";
    }
}
