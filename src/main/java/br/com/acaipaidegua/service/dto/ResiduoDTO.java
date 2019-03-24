package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.acaipaidegua.domain.enumeration.Destino;

/**
 * A DTO for the Residuo entity.
 */
public class ResiduoDTO implements Serializable {

    private Long id;

    @NotNull
    private Destino destino;

    private String outro;

    private BigDecimal custo;

    private String responsavel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public String getOutro() {
        return outro;
    }

    public void setOutro(String outro) {
        this.outro = outro;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResiduoDTO residuoDTO = (ResiduoDTO) o;
        if (residuoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), residuoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResiduoDTO{" +
            "id=" + getId() +
            ", destino='" + getDestino() + "'" +
            ", outro='" + getOutro() + "'" +
            ", custo=" + getCusto() +
            ", responsavel='" + getResponsavel() + "'" +
            "}";
    }
}
