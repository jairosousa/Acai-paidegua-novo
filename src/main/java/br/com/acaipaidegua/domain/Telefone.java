package br.com.acaipaidegua.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.acaipaidegua.domain.enumeration.Operadora;

/**
 * A Telefone.
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operadora")
    private Operadora operadora;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @ManyToOne
    @JsonIgnoreProperties("telefones")
    private Estabelecimento estabelecimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public Telefone operadora(Operadora operadora) {
        this.operadora = operadora;
        return this;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public Telefone estabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
        return this;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
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
        Telefone telefone = (Telefone) o;
        if (telefone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telefone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Telefone{" +
            "id=" + getId() +
            ", operadora='" + getOperadora() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
