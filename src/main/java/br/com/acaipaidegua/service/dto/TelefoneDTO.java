package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.acaipaidegua.domain.enumeration.Operadora;

/**
 * A DTO for the Telefone entity.
 */
public class TelefoneDTO implements Serializable {

    private Long id;

    private Operadora operadora;

    @NotNull
    private String numero;


    private Long estabelecimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

        TelefoneDTO telefoneDTO = (TelefoneDTO) o;
        if (telefoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telefoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TelefoneDTO{" +
            "id=" + getId() +
            ", operadora='" + getOperadora() + "'" +
            ", numero='" + getNumero() + "'" +
            ", estabelecimento=" + getEstabelecimentoId() +
            "}";
    }
}
