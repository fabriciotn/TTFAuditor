package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size = 500)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Estabelecimento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	private User user;
	@Column(unique = true)
	private String cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnes;
	private String endereco;
	private String complementoEndereco;
	private String cidade;
	private String uf;
	private String cep;
	private String telefone1;
	private String telefone2;
	private String telefone3;
	private String telefone4;
	private String site;
	private String email;
	private int numeroDeLeitos;
	private String nomeRtAgenciaTransfusional;
	private String CrmRtAgenciaTransfusional;
	private String nomeResponsavelAT;
	private String diretorTecnico;
	private String diretorClinico;
	private String diretorOuGerenteAdm;
	private String provedorDoEstab;
	private String cargoResponsavelAT;
	private String tipoServico;
	private String observacao;
	private String horarioDeFuncionamento;
	private int numeroDeFuncionarios;
	private int mediaDeTransfusoes;
	private int mediaDeReacoesImediatas;
	private boolean prontoAtendimento;
	private boolean blocoCirurgico;
	private boolean cirurgiaGrandePorte;
	private int mediaCirurgiaGrandePorte;
	private boolean pacientesInternados;
	private boolean maternidade;
	private boolean hemodialise;
	private boolean cti;
	private int numeroLeitosCti;
	private boolean ctiNeoNatal;
	private boolean oncologia;
	private boolean transplantes;
	private boolean pediatria;
	private boolean bercario;
	@OneToOne
	private Unidade unidade;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getCnes() {
		return cnes;
	}
	public void setCnes(String cnes) {
		this.cnes = cnes;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getComplementoEndereco() {
		return complementoEndereco;
	}
	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getTelefone3() {
		return telefone3;
	}
	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}
	public String getTelefone4() {
		return telefone4;
	}
	public void setTelefone4(String telefone4) {
		this.telefone4 = telefone4;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNumeroDeLeitos() {
		return numeroDeLeitos;
	}
	public void setNumeroDeLeitos(int numeroDeLeitos) {
		this.numeroDeLeitos = numeroDeLeitos;
	}
	public String getNomeRtAgenciaTransfusional() {
		return nomeRtAgenciaTransfusional;
	}
	public void setNomeRtAgenciaTransfusional(String nomeRtAgenciaTransfusional) {
		this.nomeRtAgenciaTransfusional = nomeRtAgenciaTransfusional;
	}
	public String getCrmRtAgenciaTransfusional() {
		return CrmRtAgenciaTransfusional;
	}
	public void setCrmRtAgenciaTransfusional(String crmRtAgenciaTransfusional) {
		CrmRtAgenciaTransfusional = crmRtAgenciaTransfusional;
	}
	public String getNomeResponsavelAT() {
		return nomeResponsavelAT;
	}
	public void setNomeResponsavelAT(String nomeResponsavelAT) {
		this.nomeResponsavelAT = nomeResponsavelAT;
	}
	public String getCargoResponsavelAT() {
		return cargoResponsavelAT;
	}
	public void setCargoResponsavelAT(String cargoResponsavelAT) {
		this.cargoResponsavelAT = cargoResponsavelAT;
	}
	public String getTipoServico() {
		return tipoServico;
	}
	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}
	public String getHorarioDeFuncionamento() {
		return horarioDeFuncionamento;
	}
	public void setHorarioDeFuncionamento(String horarioDeFuncionamento) {
		this.horarioDeFuncionamento = horarioDeFuncionamento;
	}
	public int getNumeroDeFuncionarios() {
		return numeroDeFuncionarios;
	}
	public void setNumeroDeFuncionarios(int numeroDeFuncionarios) {
		this.numeroDeFuncionarios = numeroDeFuncionarios;
	}
	public int getMediaDeTransfusoes() {
		return mediaDeTransfusoes;
	}
	public void setMediaDeTransfusoes(int mediaDeTransfusoes) {
		this.mediaDeTransfusoes = mediaDeTransfusoes;
	}
	public int getMediaDeReacoesImediatas() {
		return mediaDeReacoesImediatas;
	}
	public void setMediaDeReacoesImediatas(int mediaDeReacoesImediatas) {
		this.mediaDeReacoesImediatas = mediaDeReacoesImediatas;
	}
	public boolean isProntoAtendimento() {
		return prontoAtendimento;
	}
	public void setProntoAtendimento(boolean prontoAtendimento) {
		this.prontoAtendimento = prontoAtendimento;
	}
	public boolean isBlocoCirurgico() {
		return blocoCirurgico;
	}
	public void setBlocoCirurgico(boolean blocoCirurgico) {
		this.blocoCirurgico = blocoCirurgico;
	}
	public boolean isCirurgiaGrandePorte() {
		return cirurgiaGrandePorte;
	}
	public void setCirurgiaGrandePorte(boolean cirurgiaGrandePorte) {
		this.cirurgiaGrandePorte = cirurgiaGrandePorte;
	}
	public int getMediaCirurgiaGrandePorte() {
		return mediaCirurgiaGrandePorte;
	}
	public void setMediaCirurgiaGrandePorte(int mediaCirurgiaGrandePorte) {
		this.mediaCirurgiaGrandePorte = mediaCirurgiaGrandePorte;
	}
	public boolean isPacientesInternados() {
		return pacientesInternados;
	}
	public void setPacientesInternados(boolean pacientesInternados) {
		this.pacientesInternados = pacientesInternados;
	}
	public boolean isMaternidade() {
		return maternidade;
	}
	public void setMaternidade(boolean maternidade) {
		this.maternidade = maternidade;
	}
	public boolean isHemodialise() {
		return hemodialise;
	}
	public void setHemodialise(boolean hemodialise) {
		this.hemodialise = hemodialise;
	}
	public boolean isCti() {
		return cti;
	}
	public void setCti(boolean cti) {
		this.cti = cti;
	}
	public int getNumeroLeitosCti() {
		return numeroLeitosCti;
	}
	public void setNumeroLeitosCti(int numeroLeitosCti) {
		this.numeroLeitosCti = numeroLeitosCti;
	}
	public boolean isCtiNeoNatal() {
		return ctiNeoNatal;
	}
	public void setCtiNeoNatal(boolean ctiNeoNatal) {
		this.ctiNeoNatal = ctiNeoNatal;
	}
	public boolean isOncologia() {
		return oncologia;
	}
	public void setOncologia(boolean oncologia) {
		this.oncologia = oncologia;
	}
	public boolean isTransplantes() {
		return transplantes;
	}
	public void setTransplantes(boolean transplantes) {
		this.transplantes = transplantes;
	}
	public boolean isPediatria() {
		return pediatria;
	}
	public void setPediatria(boolean pediatria) {
		this.pediatria = pediatria;
	}	
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public boolean isBercario() {
		return bercario;
	}
	public void setBercario(boolean bercario) {
		this.bercario = bercario;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getDiretorTecnico() {
		return diretorTecnico;
	}
	public void setDiretorTecnico(String diretorTecnico) {
		this.diretorTecnico = diretorTecnico;
	}
	public String getDiretorClinico() {
		return diretorClinico;
	}
	public void setDiretorClinico(String diretorClinico) {
		this.diretorClinico = diretorClinico;
	}
	public String getDiretorOuGerenteAdm() {
		return diretorOuGerenteAdm;
	}
	public void setDiretorOuGerenteAdm(String diretorOuGerenteAdm) {
		this.diretorOuGerenteAdm = diretorOuGerenteAdm;
	}
	public String getProvedorDoEstab() {
		return provedorDoEstab;
	}
	public void setProvedorDoEstab(String provedorDoEstab) {
		this.provedorDoEstab = provedorDoEstab;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estabelecimento other = (Estabelecimento) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
