package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.acaipaidegua.domain.enumeration.Periodo;
import br.com.acaipaidegua.domain.enumeration.Unidade;

/**
 * A DTO for the Beneficiamento entity.
 */
public class BeneficiamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Periodo periodo;

    @NotNull
    private BigDecimal quantidade;

    @NotNull
    private Unidade unidade;

    private BigDecimal total;


    private Long residuoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getResiduoId() {
        return residuoId;
    }

    public void setResiduoId(Long residuoId) {
        this.residuoId = residuoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeneficiamentoDTO beneficiamentoDTO = (BeneficiamentoDTO) o;
        if (beneficiamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beneficiamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BeneficiamentoDTO{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            ", quantidade=" + getQuantidade() +
            ", unidade='" + getUnidade() + "'" +
            ", total=" + getTotal() +
            ", residuo=" + getResiduoId() +
            "}";
    }
}
