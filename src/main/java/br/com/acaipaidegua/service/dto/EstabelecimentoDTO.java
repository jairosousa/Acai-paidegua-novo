package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.acaipaidegua.domain.enumeration.Status;
import br.com.acaipaidegua.domain.enumeration.Conta;

/**
 * A DTO for the Estabelecimento entity.
 */
public class EstabelecimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String responsavel;

    private Status status;

    private Conta conta;


    private Long userId;

    private String userLogin;

    private Long enderecoId;

    private Long caracteristicaId;

    private Long beneficiamentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public Long getCaracteristicaId() {
        return caracteristicaId;
    }

    public void setCaracteristicaId(Long caracteristicaId) {
        this.caracteristicaId = caracteristicaId;
    }

    public Long getBeneficiamentoId() {
        return beneficiamentoId;
    }

    public void setBeneficiamentoId(Long beneficiamentoId) {
        this.beneficiamentoId = beneficiamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstabelecimentoDTO estabelecimentoDTO = (EstabelecimentoDTO) o;
        if (estabelecimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estabelecimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstabelecimentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", status='" + getStatus() + "'" +
            ", conta='" + getConta() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", endereco=" + getEnderecoId() +
            ", caracteristica=" + getCaracteristicaId() +
            ", beneficiamento=" + getBeneficiamentoId() +
            "}";
    }
}
