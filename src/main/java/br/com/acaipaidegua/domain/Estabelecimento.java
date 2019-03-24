package br.com.acaipaidegua.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.acaipaidegua.domain.enumeration.Status;

import br.com.acaipaidegua.domain.enumeration.Conta;

/**
 * A Estabelecimento.
 */
@Entity
@Table(name = "estabelecimento")
public class Estabelecimento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "responsavel")
    private String responsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "conta")
    private Conta conta;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(unique = true)
    private Caracteristica caracteristica;

    @OneToOne
    @JoinColumn(unique = true)
    private Beneficiamento beneficiamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Estabelecimento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public Estabelecimento responsavel(String responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Status getStatus() {
        return status;
    }

    public Estabelecimento status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Conta getConta() {
        return conta;
    }

    public Estabelecimento conta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public User getUser() {
        return user;
    }

    public Estabelecimento user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Estabelecimento endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public Estabelecimento caracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
        return this;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Beneficiamento getBeneficiamento() {
        return beneficiamento;
    }

    public Estabelecimento beneficiamento(Beneficiamento beneficiamento) {
        this.beneficiamento = beneficiamento;
        return this;
    }

    public void setBeneficiamento(Beneficiamento beneficiamento) {
        this.beneficiamento = beneficiamento;
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
        Estabelecimento estabelecimento = (Estabelecimento) o;
        if (estabelecimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estabelecimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estabelecimento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", status='" + getStatus() + "'" +
            ", conta='" + getConta() + "'" +
            "}";
    }
}
