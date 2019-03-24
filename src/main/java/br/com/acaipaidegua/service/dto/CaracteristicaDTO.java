package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Caracteristica entity.
 */
public class CaracteristicaDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean possuiSelo;

    @NotNull
    private Boolean possuiRestaurante;

    @NotNull
    private Boolean areaProducaoIsolada;

    @NotNull
    private Boolean cobraTaxaEntrega;

    private BigDecimal valor;

    private String observacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPossuiSelo() {
        return possuiSelo;
    }

    public void setPossuiSelo(Boolean possuiSelo) {
        this.possuiSelo = possuiSelo;
    }

    public Boolean isPossuiRestaurante() {
        return possuiRestaurante;
    }

    public void setPossuiRestaurante(Boolean possuiRestaurante) {
        this.possuiRestaurante = possuiRestaurante;
    }

    public Boolean isAreaProducaoIsolada() {
        return areaProducaoIsolada;
    }

    public void setAreaProducaoIsolada(Boolean areaProducaoIsolada) {
        this.areaProducaoIsolada = areaProducaoIsolada;
    }

    public Boolean isCobraTaxaEntrega() {
        return cobraTaxaEntrega;
    }

    public void setCobraTaxaEntrega(Boolean cobraTaxaEntrega) {
        this.cobraTaxaEntrega = cobraTaxaEntrega;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaracteristicaDTO caracteristicaDTO = (CaracteristicaDTO) o;
        if (caracteristicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caracteristicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaracteristicaDTO{" +
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
