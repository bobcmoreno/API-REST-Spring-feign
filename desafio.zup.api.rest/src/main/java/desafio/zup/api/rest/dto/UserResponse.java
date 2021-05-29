package desafio.zup.api.rest.dto;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Valor",
    "Marca",
    "Modelo",
    "AnoModelo",
    "Combustivel",
    "CodigoFipe",
    "MesReferencia",
    "TipoVeiculo",
    "SiglaCombustivel"
})
@Generated("jsonschema2pojo")
public class UserResponse {

    @JsonProperty("Valor")
    private String valor;
    @JsonProperty("Marca")
    private String marca;
    @JsonProperty("Modelo")
    private String modelo;
    @JsonProperty("AnoModelo")
    private Integer anoModelo;
    @JsonProperty("Combustivel")
    private String combustivel;
    @JsonProperty("CodigoFipe")
    private String codigoFipe;
    @JsonProperty("MesReferencia")
    private String mesReferencia;
    @JsonProperty("TipoVeiculo")
    private Integer tipoVeiculo;
    @JsonProperty("SiglaCombustivel")
    private String siglaCombustivel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Valor")
    public String getValor() {
        return valor;
    }

    @JsonProperty("Valor")
    public void setValor(String valor) {
        this.valor = valor;
    }

    @JsonProperty("Marca")
    public String getMarca() {
        return marca;
    }

    @JsonProperty("Marca")
    public void setMarca(String marca) {
        this.marca = marca;
    }

    @JsonProperty("Modelo")
    public String getModelo() {
        return modelo;
    }

    @JsonProperty("Modelo")
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @JsonProperty("AnoModelo")
    public Integer getAnoModelo() {
        return anoModelo;
    }

    @JsonProperty("AnoModelo")
    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    @JsonProperty("Combustivel")
    public String getCombustivel() {
        return combustivel;
    }

    @JsonProperty("Combustivel")
    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    @JsonProperty("CodigoFipe")
    public String getCodigoFipe() {
        return codigoFipe;
    }

    @JsonProperty("CodigoFipe")
    public void setCodigoFipe(String codigoFipe) {
        this.codigoFipe = codigoFipe;
    }

    @JsonProperty("MesReferencia")
    public String getMesReferencia() {
        return mesReferencia;
    }

    @JsonProperty("MesReferencia")
    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    @JsonProperty("TipoVeiculo")
    public Integer getTipoVeiculo() {
        return tipoVeiculo;
    }

    @JsonProperty("TipoVeiculo")
    public void setTipoVeiculo(Integer tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    @JsonProperty("SiglaCombustivel")
    public String getSiglaCombustivel() {
        return siglaCombustivel;
    }

    @JsonProperty("SiglaCombustivel")
    public void setSiglaCombustivel(String siglaCombustivel) {
        this.siglaCombustivel = siglaCombustivel;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
