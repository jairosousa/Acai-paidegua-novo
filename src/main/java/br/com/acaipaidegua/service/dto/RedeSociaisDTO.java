package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.acaipaidegua.domain.enumeration.RedeSocial;

/**
 * A DTO for the RedeSociais entity.
 */
public class RedeSociaisDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    @NotNull
    private RedeSocial nome;


    private Long estabelecimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RedeSocial getNome() {
        return nome;
    }

    public void setNome(RedeSocial nome) {
        this.nome = nome;
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

        RedeSociaisDTO redeSociaisDTO = (RedeSociaisDTO) o;
        if (redeSociaisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), redeSociaisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RedeSociaisDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", nome='" + getNome() + "'" +
            ", estabelecimento=" + getEstabelecimentoId() +
            "}";
    }
}
