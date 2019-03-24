package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.acaipaidegua.domain.enumeration.TipoHorario;

/**
 * A DTO for the HorarioFuncionamento entity.
 */
public class HorarioFuncionamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoHorario tipo;

    @NotNull
    private String diasSemana;

    @NotNull
    private String hrAbertura;

    @NotNull
    private String hrFechamento;


    private Long estabelecimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoHorario getTipo() {
        return tipo;
    }

    public void setTipo(TipoHorario tipo) {
        this.tipo = tipo;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public String getHrAbertura() {
        return hrAbertura;
    }

    public void setHrAbertura(String hrAbertura) {
        this.hrAbertura = hrAbertura;
    }

    public String getHrFechamento() {
        return hrFechamento;
    }

    public void setHrFechamento(String hrFechamento) {
        this.hrFechamento = hrFechamento;
    }

    public Long getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(Long estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorarioFuncionamentoDTO horarioFuncionamentoDTO = (HorarioFuncionamentoDTO) o;
        if (horarioFuncionamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horarioFuncionamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorarioFuncionamentoDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", diasSemana='" + getDiasSemana() + "'" +
            ", hrAbertura='" + getHrAbertura() + "'" +
            ", hrFechamento='" + getHrFechamento() + "'" +
            ", estabelecimento=" + getEstabelecimentoId() +
            "}";
    }
}
