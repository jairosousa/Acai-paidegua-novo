package br.com.acaipaidegua.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Geolocalizacao entity.
 */
public class GeolocalizacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeolocalizacaoDTO geolocalizacaoDTO = (GeolocalizacaoDTO) o;
        if (geolocalizacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), geolocalizacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeolocalizacaoDTO{" +
            "id=" + getId() +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            "}";
    }
}
