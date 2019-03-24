package br.com.acaipaidegua.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Imagem entity.
 */
public class ImagemDTO implements Serializable {

    private Long id;

    private String title;

    private String descricao;

    @Lob
    private byte[] imagem;

    private String imagemContentType;
    private Instant uploaded;


    private Long estabelecimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Instant getUploaded() {
        return uploaded;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
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

        ImagemDTO imagemDTO = (ImagemDTO) o;
        if (imagemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagemDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", uploaded='" + getUploaded() + "'" +
            ", estabelecimento=" + getEstabelecimentoId() +
            "}";
    }
}
