package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Email entity.
 */
public class EmailDTO implements Serializable {

    private Long id;

    @NotNull
    private String endereco;


    private Long estabelecimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

        EmailDTO emailDTO = (EmailDTO) o;
        if (emailDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
            "id=" + getId() +
            ", endereco='" + getEndereco() + "'" +
            ", estabelecimento=" + getEstabelecimentoId() +
            "}";
    }
}
