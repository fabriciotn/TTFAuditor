package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.EstabelecimentoDAO;
import com.model.Estabelecimento;

public class EstabelecimentoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
	private static SessionFactory factory; 

	public void createEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		estabelecimentoDAO.save(estabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	public void updateEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento persistedEstabelecimento = estabelecimentoDAO.find(estabelecimento.getId());
		persistedEstabelecimento.setCnpj(estabelecimento.getCnpj());
		persistedEstabelecimento.setCnes(estabelecimento.getCnes());
		persistedEstabelecimento.setNomeFantasia(estabelecimento.getNomeFantasia());
		persistedEstabelecimento.setRazaoSocial(estabelecimento.getRazaoSocial());
		persistedEstabelecimento.setUnidade(estabelecimento.getUnidade());
		persistedEstabelecimento.setUser(estabelecimento.getUser());
		persistedEstabelecimento.setEndereco(estabelecimento.getEndereco());
		persistedEstabelecimento.setComplementoEndereco(estabelecimento.getComplementoEndereco());
		persistedEstabelecimento.setCidade(estabelecimento.getCidade());
		persistedEstabelecimento.setUf(estabelecimento.getUf());
		persistedEstabelecimento.setCep(estabelecimento.getCep());
		persistedEstabelecimento.setTelefone1(estabelecimento.getTelefone1());
		persistedEstabelecimento.setTelefone2(estabelecimento.getTelefone2());
		persistedEstabelecimento.setTelefone3(estabelecimento.getTelefone3());
		persistedEstabelecimento.setTelefone4(estabelecimento.getTelefone4());
		persistedEstabelecimento.setSite(estabelecimento.getSite());
		persistedEstabelecimento.setEmail(estabelecimento.getEmail());
		persistedEstabelecimento.setNumeroDeLeitos(estabelecimento.getNumeroDeLeitos());
		persistedEstabelecimento.setNomeRtAgenciaTransfusional(estabelecimento.getNomeRtAgenciaTransfusional());
		persistedEstabelecimento.setCrmRtAgenciaTransfusional(estabelecimento.getCrmRtAgenciaTransfusional());
		persistedEstabelecimento.setNomeResponsavelAT(estabelecimento.getNomeResponsavelAT());
		persistedEstabelecimento.setCargoResponsavelAT(estabelecimento.getCargoResponsavelAT());
		persistedEstabelecimento.setTipoServico(estabelecimento.getTipoServico());
		persistedEstabelecimento.setHorarioDeFuncionamento(estabelecimento.getHorarioDeFuncionamento());
		persistedEstabelecimento.setNumeroDeFuncionarios(estabelecimento.getNumeroDeFuncionarios());
		persistedEstabelecimento.setMediaDeTransfusoes(estabelecimento.getMediaDeTransfusoes());
		persistedEstabelecimento.setMediaDeReacoesImediatas(estabelecimento.getMediaDeReacoesImediatas());
		persistedEstabelecimento.setProntoAtendimento(estabelecimento.isProntoAtendimento());
		persistedEstabelecimento.setBlocoCirurgico(estabelecimento.isBlocoCirurgico());
		persistedEstabelecimento.setCirurgiaGrandePorte(estabelecimento.isCirurgiaGrandePorte());
		persistedEstabelecimento.setMediaCirurgiaGrandePorte(estabelecimento.getMediaCirurgiaGrandePorte());
		persistedEstabelecimento.setPacientesInternados(estabelecimento.isPacientesInternados());
		persistedEstabelecimento.setMaternidade(estabelecimento.isMaternidade());
		persistedEstabelecimento.setHemodialise(estabelecimento.isHemodialise());
		persistedEstabelecimento.setCti(estabelecimento.isCti());
		persistedEstabelecimento.setNumeroLeitosCti(estabelecimento.getNumeroLeitosCti());
		persistedEstabelecimento.setCtiNeoNatal(estabelecimento.isCtiNeoNatal());
		persistedEstabelecimento.setOncologia(estabelecimento.isOncologia());
		persistedEstabelecimento.setTransplantes(estabelecimento.isTransplantes());
		persistedEstabelecimento.setPediatria(estabelecimento.isPediatria());

		estabelecimentoDAO.update(persistedEstabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	public Estabelecimento findEstabelecimento(int estabelecimentoId) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento estabelecimento = estabelecimentoDAO.find(estabelecimentoId);
		estabelecimentoDAO.closeTransaction();
		return estabelecimento;
	}

	public List<Estabelecimento> listAll() {
		estabelecimentoDAO.beginTransaction();
		List<Estabelecimento> result = estabelecimentoDAO.findAllAsc();
		estabelecimentoDAO.closeTransaction();
		return result;
	}

	public void deleteEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento persistedEstabelecimento = estabelecimentoDAO.findReferenceOnly(estabelecimento
				.getId());
		estabelecimentoDAO.delete(persistedEstabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		estabelecimentoDAO.beginTransaction();
		Query query = estabelecimentoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		estabelecimentoDAO.closeTransaction();
		return list;
	}

}