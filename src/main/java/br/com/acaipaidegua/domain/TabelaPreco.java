package br.com.acaipaidegua.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A TabelaPreco.
 */
@Entity
@Table(name = "tabela_preco")
public class TabelaPreco implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "preco", precision = 10, scale = 2, nullable = false)
    private BigDecimal preco;

    @ManyToOne
    @JsonIgnoreProperties("tabelaPrecos")
    private Estabelecimento estabelecimento;

    @ManyToOne
    @JsonIgnoreProperties("tabelaPrecos")
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public TabelaPreco preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public TabelaPreco estabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
        return this;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Produto getProduto() {
        return produto;
    }

    public TabelaPreco produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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
        TabelaPreco tabelaPreco = (TabelaPreco) o;
        if (tabelaPreco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tabelaPreco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TabelaPreco{" +
            "id=" + getId() +
            ", preco=" + getPreco() +
            "}";
    }
}
