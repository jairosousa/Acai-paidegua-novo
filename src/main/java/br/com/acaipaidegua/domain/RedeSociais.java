package br.com.acaipaidegua.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.acaipaidegua.domain.enumeration.RedeSocial;

/**
 * A RedeSociais.
 */
@Entity
@Table(name = "rede_sociais")
public class RedeSociais implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nome", nullable = false)
    private RedeSocial nome;

    @ManyToOne
    @JsonIgnoreProperties("redeSociais")
    private Estabelecimento estabelecimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public RedeSociais url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RedeSocial getNome() {
        return nome;
    }

    public RedeSociais nome(RedeSocial nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(RedeSocial nome) {
        this.nome = nome;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public RedeSociais estabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
        return this;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
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
        RedeSociais redeSociais = (RedeSociais) o;
        if (redeSociais.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), redeSociais.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RedeSociais{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
