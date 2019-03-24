package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the TabelaPreco entity.
 */
public class TabelaPrecoDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal preco;


    private Long estabelecimentoId;

    private Long produtoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(Long estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TabelaPrecoDTO tabelaPrecoDTO = (TabelaPrecoDTO) o;
        if (tabelaPrecoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tabelaPrecoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TabelaPrecoDTO{" +
            "id=" + getId() +
            ", preco=" + getPreco() +
            ", estabelecimento=" + getEstabelecimentoId() +
            ", produto=" + getProdutoId() +
            "}";
    }
}
