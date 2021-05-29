package desafio.zup.api.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tab_veiculos")

public class Veiculo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int marcaFipe;
    
    @Column(nullable = false)
    private String Marca;

    @Column(nullable = false) 
    private int modeloFipe;
    
    @Column(nullable = false)
    private String Modelo;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int combustivelFipe;
    
    @Column(nullable = false)
    private String Combustivel;
    
    @Column(nullable = false) 
    private String Valor;

    @Column(nullable = false)
    private int diarodizio;
    
    @Column(nullable = false)
    private String diarodiziodesc;
    
    @Column(nullable = false) 
    private boolean rodiziohoje;
    
    @JsonIgnore
    @ForeignKey(name = "usuario_id")
    @ManyToOne(optional = false)
    private Usuario usuario_id;



	public Usuario getUsuario_id() {
		return usuario_id;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getMarcaFipe() {
		return marcaFipe;
	}


	public void setMarcaFipe(int marcaFipe) {
		this.marcaFipe = marcaFipe;
	}


	public String getMarca() {
		return Marca;
	}


	public void setMarca(String marca) {
		Marca = marca;
	}


	public int getModeloFipe() {
		return modeloFipe;
	}


	public void setModeloFipe(int modeloFipe) {
		this.modeloFipe = modeloFipe;
	}


	public String getModelo() {
		return Modelo;
	}


	public void setModelo(String modelo) {
		Modelo = modelo;
	}


	public int getAno() {
		return ano;
	}


	public void setAno(int ano) {
		this.ano = ano;
	}


	public int getCombustivelFipe() {
		return combustivelFipe;
	}


	public void setCombustivelFipe(int combustivelFipe) {
		this.combustivelFipe = combustivelFipe;
	}


	public String getCombustivel() {
		return Combustivel;
	}


	public void setCombustivel(String combustivel) {
		Combustivel = combustivel;
	}


	public String getValor() {
		return Valor;
	}


	public void setValor(String valor) {
		Valor = valor;
	}


	public int getDiarodizio() {
		return diarodizio;
	}


	public void setDiarodizio(int diarodizio) {
		this.diarodizio = diarodizio;
	}


	public String getDiarodiziodesc() {
		return diarodiziodesc;
	}


	public void setDiarodiziodesc(String diarodiziodesc) {
		this.diarodiziodesc = diarodiziodesc;
	}


	public boolean isRodiziohoje() {
		return rodiziohoje;
	}


	public void setRodiziohoje(boolean rodiziohoje) {
		this.rodiziohoje = rodiziohoje;
	}

/*
	public void setUsuario_id1(Usuario usuario) {
		this.usuario_id = usuario;
		// TODO Auto-generated method stub
}
*/		

	public void setUsuario_id(Usuario usuario) {
		// TODO Auto-generated method stub
		this.usuario_id = usuario;
		
	}
}