package br.com.acaipaidegua.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.acaipaidegua.domain.enumeration.TipoHorario;

/**
 * A HorarioFuncionamento.
 */
@Entity
@Table(name = "horario_funcionamento")
public class HorarioFuncionamento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoHorario tipo;

    @NotNull
    @Column(name = "dias_semana", nullable = false)
    private String diasSemana;

    @NotNull
    @Column(name = "hr_abertura", nullable = false)
    private String hrAbertura;

    @NotNull
    @Column(name = "hr_fechamento", nullable = false)
    private String hrFechamento;

    @ManyToOne
    @JsonIgnoreProperties("horarioFuncionamentos")
    private Estabelecimento estabelecimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoHorario getTipo() {
        return tipo;
    }

    public HorarioFuncionamento tipo(TipoHorario tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoHorario tipo) {
        this.tipo = tipo;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public HorarioFuncionamento diasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
        return this;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public String getHrAbertura() {
        return hrAbertura;
    }

    public HorarioFuncionamento hrAbertura(String hrAbertura) {
        this.hrAbertura = hrAbertura;
        return this;
    }

    public void setHrAbertura(String hrAbertura) {
        this.hrAbertura = hrAbertura;
    }

    public String getHrFechamento() {
        return hrFechamento;
    }

    public HorarioFuncionamento hrFechamento(String hrFechamento) {
        this.hrFechamento = hrFechamento;
        return this;
    }

    public void setHrFechamento(String hrFechamento) {
        this.hrFechamento = hrFechamento;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public HorarioFuncionamento estabelecimento(Estabelecimento estabelecimento) {
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
        HorarioFuncionamento horarioFuncionamento = (HorarioFuncionamento) o;
        if (horarioFuncionamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), horarioFuncionamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HorarioFuncionamento{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", diasSemana='" + getDiasSemana() + "'" +
            ", hrAbertura='" + getHrAbertura() + "'" +
            ", hrFechamento='" + getHrFechamento() + "'" +
            "}";
    }
}
