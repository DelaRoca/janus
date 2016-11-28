package br.com.janus.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import br.com.janus.Conecta;
import br.com.janus.model.OrdemServico;
import br.com.janus.model.OsProdutos;
import br.com.janus.model.OsServicos;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class OrdemServicoController {

	private Connection conexao = new Conecta().getConnection();
	private Integer idOrdemServico;

	public void salva(OrdemServico ordemServico, ArrayList<OsServicos> osServicos, ArrayList<OsProdutos> osProdutos) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement(
					"insert into ordemdeservico " + "(idcliente,idveiculo,total,datacriacao) " + "values (?,?,?,?)");
			st.setInt(1, ordemServico.getIdCliente());
			st.setInt(2, ordemServico.getIdVeiculo());
			st.setString(3, ordemServico.getTotal());
			st.setString(4, ordemServico.getDataCriacao());
			st.execute();
			ResultSet rs = st.executeQuery("SELECT MAX(idordemdeservico) as id FROM ordemdeservico");
			while (rs.next()) {
				idOrdemServico = rs.getInt("id");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
			e.printStackTrace();
		}
		salvaOsProdutos(osProdutos);
		salvaOsServicos(osServicos);
		JOptionPane.showMessageDialog(null, "Ordem de servi�o de n�mero " + idOrdemServico + " criada com sucesso!");
		GerenciadorDeInterface.setPanel(new Principal());
	}

	private void salvaOsServicos(ArrayList<OsServicos> osServicos) {
		try {
			for (OsServicos osServico : osServicos) {
				PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into osservico "
						+ "(idordemdeservico,idservico,quantidade,qtdporhora) " + "values (?,?,?,?)");
				st.setInt(1, idOrdemServico);
				st.setInt(2, osServico.getIdServico());
				st.setInt(3, osServico.getQuantidade());
				st.setInt(4, osServico.getQtdPorHora());
				st.execute();
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}

	private void salvaOsProdutos(ArrayList<OsProdutos> osProdutos) {
		try {
			for (OsProdutos osProduto : osProdutos) {
				PreparedStatement st = (PreparedStatement) conexao.prepareStatement(
						"insert into osproduto " + "(idordemdeservico,idproduto,quantidade) " + "values (?,?,?)");
				st.setInt(1, idOrdemServico);
				st.setInt(2, osProduto.getIdProduto());
				st.setInt(3, osProduto.getQuantidade());
				st.execute();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}

	public OrdemServico buscaOrdemServico(Integer idOrdemDeServico) throws SQLException {
		System.out.println(idOrdemDeServico+" chegou o id buscarOrdem");
		try {
			PreparedStatement st = (PreparedStatement) conexao
					.prepareStatement("select * from ordemdeservico where idordemdeservico = ?;");
			st.setInt(1, idOrdemDeServico);
			ResultSet result = st.executeQuery();
			if (result != null) {
				OrdemServico os = new OrdemServico();
				while (result.next()) {
					os.setIdOrdemDeServico(result.getInt("idordemdeservico"));
					os.setIdCliente(result.getInt("idcliente"));
					os.setIdVeiculo(result.getInt("idveiculo"));
					os.setEstaExpirado(result.getString("estaexpirado") == "0" ? false : true);
					os.setDataCriacao(result.getString("datacriacao"));
					os.setDataAprovado(result.getString("dataaprovado"));
					os.setDataExecucao(result.getString("dataexecucao"));
					os.setDataFinalizado(result.getString("datafinalizado"));
					os.setDataCancelado(result.getString("datacancelado"));
					os.setTotal(result.getString("total"));
					return os;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<OsProdutos> buscaProdutosOrdemServico(Integer idOrdemDeServico) throws SQLException {
		ArrayList<OsProdutos> produtos = new ArrayList<OsProdutos>();
		PreparedStatement st = (PreparedStatement) conexao
				.prepareStatement("select * from osproduto where idordemdeservico = ?;");
		st.setInt(1, idOrdemDeServico);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " + result == null);
		if (result != null) {
			while (result.next()) {
				OsProdutos os = new OsProdutos();
				os.setIdProduto(result.getInt("idproduto"));
				os.setQuantidade(result.getInt("quantidade"));
				produtos.add(os);
			}
		}
		return produtos;
	}

	public ArrayList<OsServicos> buscaServicosOrdemServico(Integer idOrdemDeServico) throws SQLException {
		ArrayList<OsServicos> servicos = new ArrayList<OsServicos>();
		PreparedStatement st = (PreparedStatement) conexao
				.prepareStatement("select * from osservico where idordemdeservico = ?;");
		st.setInt(1, idOrdemDeServico);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " + result == null);
		if (result != null) {
			while (result.next()) {
				OsServicos os = new OsServicos();
				os.setIdServico(result.getInt("idservico"));
				os.setQuantidade(result.getInt("quantidade"));
				os.setQtdPorHora(result.getInt("qtdporhora"));
				servicos.add(os);
			}
		}
		return servicos;
	}

	public ArrayList<OrdemServico> buscaOrdensServicoAprovadas() {
		ArrayList<OrdemServico> ordens = new ArrayList<OrdemServico>();
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement(
				"select * from ordemdeservico where dataaprovado is not null and dataexecucao is null and datacancelado is null;");
			ResultSet result = st.executeQuery();
			if (result != null) {
				System.out.println("st.getResultSet " + st.getResultSet());
				while (result.next()) {
					OrdemServico os = new OrdemServico();
					os.setIdOrdemDeServico(result.getInt("idordemdeservico"));
					os.setIdCliente(result.getInt("idcliente"));
					os.setIdVeiculo(result.getInt("idveiculo"));
					os.setEstaExpirado(result.getString("estaexpirado") == "1" ? true : false);
					os.setDataCriacao(result.getString("datacriacao"));
					os.setDataAprovado(result.getString("dataaprovado"));
					os.setDataExecucao(result.getString("dataexecucao"));
					os.setDataFinalizado(result.getString("datafinalizado"));
					os.setDataCancelado(result.getString("datacancelado"));
					os.setTotal(result.getString("total"));
					ordens.add(os);
				}
			}
		} catch (SQLException e) {
			// TODO
			JOptionPane.showMessageDialog(null, "PENSAR MENSAGEM!!");
			e.printStackTrace();
		}
		return ordens;
	}
	
	public ArrayList<OrdemServico> buscaOrdensServicoExecutadas() {
		ArrayList<OrdemServico> ordens = new ArrayList<OrdemServico>();

		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement(
				"select * from ordemdeservico where dataexecucao is not null and datafinalizado is null and datacancelado is null;");
			ResultSet result = st.executeQuery();
			if (result != null) {
				System.out.println("st.getResultSet " + st.getResultSet());
				while (result.next()) {
					OrdemServico os = new OrdemServico();
					os.setIdOrdemDeServico(result.getInt("idordemdeservico"));
					os.setIdCliente(result.getInt("idcliente"));
					os.setIdVeiculo(result.getInt("idveiculo"));
					os.setEstaExpirado(result.getString("estaexpirado") == "1" ? true : false);
					os.setDataCriacao(result.getString("datacriacao"));
					os.setDataAprovado(result.getString("dataaprovado"));
					os.setDataExecucao(result.getString("dataexecucao"));
					os.setDataFinalizado(result.getString("datafinalizado"));
					os.setDataCancelado(result.getString("datacancelado"));
					os.setTotal(result.getString("total"));
					ordens.add(os);
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "PENSAR MENSAGEM!!");
			e.printStackTrace();
		}
		return ordens;
	}
	
	public boolean aprovaOrdemServico(OrdemServico ordemServico, ArrayList<OsServicos> osServicos, ArrayList<OsProdutos> osProdutos, String dataAprovado) {
		PreparedStatement st = null;
		try{
			st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico"+"set total = ?, dataaprovado = ? "+"where idordemservico = ?");
			st.setString(1, ordemServico.getTotal());
			st.setString(2, dataAprovado);
			st.setInt(3,ordemServico.getIdOrdemDeServico());
			st.executeUpdate();
			salvaOsProdutos(osProdutos);
			salvaOsServicos(osServicos);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean executaOrdemServico(Integer idOrdemDeServico, String dataExecucao) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico "
					+ "set dataexecucao= ? " + "where idordemdeservico = '" + idOrdemDeServico + "'");
			st.setString(1, dataExecucao);
			st.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean finalizaOrdemServico(Integer idOrdemDeServico, String dataFinalizado) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement(
					"update ordemdeservico " + "set datafinalizado= ?, dataexecucao = NULL, datacancelado = NULL" //TODO (tirar isso de setar NULLS, isso � errado e a prof vai chiar)
							+ "where idordemdeservico = '" + idOrdemDeServico + "'");
			st.setString(1, dataFinalizado);
			st.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cancelaOrdemServico(Integer idOrdemDeServico, String dataCancelado) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico "
					+ "set datacancelado= ? " + "where idordemdeservico = '" + idOrdemDeServico + "'");
			st.setString(1, dataCancelado);
			st.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean atualizaOrdemServico(OrdemServico ordemServico, ArrayList<OsServicos> osServicos, ArrayList<OsProdutos> osProdutos) {
		PreparedStatement st = null;
		try{
			st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico"+"set total = ? "+"where idordemservico = ?");
			st.setString(1, ordemServico.getTotal());
			st.setInt(2,ordemServico.getIdOrdemDeServico());
			st.executeUpdate();
			salvaOsProdutos(osProdutos);
			salvaOsServicos(osServicos);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
