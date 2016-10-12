package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.EstabelecimentoDAO;
import com.model.Estabelecimento;
import com.model.Unidade;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author TTF Informática
 *
 */
public class EstabelecimentoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
	private static SessionFactory factory; 

	/**
	 * Cria um novo estabelecimento
	 * @param estabelecimento
	 */
	public void createEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		estabelecimentoDAO.save(estabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza Estabelecimento
	 * @param estabelecimento
	 */
	public void updateEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento persistedEstabelecimento = estabelecimentoDAO.find(estabelecimento.getId());
		if(persistedEstabelecimento != null){
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
			persistedEstabelecimento.setDiretorClinico(estabelecimento.getDiretorClinico());
			persistedEstabelecimento.setDiretorTecnico(estabelecimento.getDiretorTecnico());
			persistedEstabelecimento.setDiretorOuGerenteAdm(estabelecimento.getDiretorOuGerenteAdm());
			persistedEstabelecimento.setProvedorDoEstab(estabelecimento.getProvedorDoEstab());
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
			persistedEstabelecimento.setBercario(estabelecimento.isBercario());
			persistedEstabelecimento.setObservacao(estabelecimento.getObservacao());
			persistedEstabelecimento.setAlvaraSanitario(estabelecimento.getAlvaraSanitario());
		}else{
			persistedEstabelecimento = estabelecimento;
		}

		estabelecimentoDAO.update(persistedEstabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	/**
	 * Reaiza a busca de um estabelecimento de acordo com o ID
	 * @param estabelecimentoId
	 * @return estabelecimento
	 */
	public Estabelecimento findEstabelecimento(int estabelecimentoId) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento estabelecimento = estabelecimentoDAO.find(estabelecimentoId);
		estabelecimentoDAO.closeTransaction();
		return estabelecimento;
	}

	/**
	 * Busca todos os Estebelecimentos
	 * @return listaDeEstabelecimentos
	 */
	public List<Estabelecimento> listAll() {
		estabelecimentoDAO.beginTransaction();
		List<Estabelecimento> result = estabelecimentoDAO.findAllAsc();
		estabelecimentoDAO.closeTransaction();
		return result;
	}
	
	/**
	 * Busca todos os Estabelecimentos de acordo com a unidade
	 * @param unidade
	 * @return listaDeEstabelecimentosPorUnidade
	 */
	public List<Estabelecimento> listaEstabelecimentoPorUnidade(Unidade unidade) {
		estabelecimentoDAO.beginTransaction();
		List<Estabelecimento> result = estabelecimentoDAO.listaEstabelecimentoPorUnidade(unidade);
		estabelecimentoDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um estabelecimento
	 * @param estabelecimento
	 */
	public void deleteEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento persistedEstabelecimento = estabelecimentoDAO.findReferenceOnly(estabelecimento
				.getId());
		estabelecimentoDAO.delete(persistedEstabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza uma consulta de acordo com a query passada por parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		estabelecimentoDAO.beginTransaction();
		Query query = estabelecimentoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		estabelecimentoDAO.closeTransaction();
		return list;
	}

}