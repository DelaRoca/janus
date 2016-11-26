package br.com.janus.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

import br.com.janus.Conecta;
import br.com.janus.model.Cliente;
import br.com.janus.model.OrdemServico;
import br.com.janus.model.OsProdutos;
import br.com.janus.model.OsServicos;
import br.com.janus.model.Produto;
import br.com.janus.model.Servico;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class OrdemServicoController {
	
	private Connection conexao = new Conecta().getConnection();
	private Integer idOrdemServico;

	public void salva(OrdemServico ordemServico, ArrayList<OsServicos> osServicos, ArrayList<OsProdutos> osProdutos) {
		System.out.println(ordemServico.getIdCliente());
		try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into ordemdeservico " +
	                "(idCliente,data,status,total,idVeiculo) " +
	                "values (?,?,?,?,?)");
	    	st.setInt(1, ordemServico.getIdCliente());
			st.setString(2,ordemServico.getData());
			st.setInt(3,ordemServico.getStatus());
		    st.setString(4,ordemServico.getTotal());
		    st.setInt(5,ordemServico.getIdVeiculo());
		    st.execute();
		    
		    ResultSet rs = st.executeQuery("SELECT MAX(idordemDeServico) as id FROM ordemdeservico");
		    while(rs.next()){
		    	idOrdemServico = rs.getInt("id");
		    }
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
		salvaOsProdutos(osProdutos);
		salvaOsServicos(osServicos);
		JOptionPane.showMessageDialog(null, "Ordem de serviço com sucesso!");
		GerenciadorDeInterface.setPanel(new Principal());
	}

	private void salvaOsServicos(ArrayList<OsServicos> osServicos) {
		try{
			for (OsServicos osServico : osServicos) {
				PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into osservico " +
						"(idOrdemDeServico,idServico,quantidade,qtdPorHora) " +
						"values (?,?,?,?)");
				st.setInt(1, idOrdemServico);
				st.setInt(2,osServico.getIdServico());
				st.setInt(3, osServico.getQuantidade());
				st.setInt(4,osServico.getQtdPorHora());
				st.execute();
			}
			
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}

	private void salvaOsProdutos(ArrayList<OsProdutos> osProdutos) {
		try{
			for (OsProdutos osProduto : osProdutos) {
				PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into osproduto " +
						"(idOrdemDeServico,idProduto,quantidade) " +
						"values (?,?,?)");
				st.setInt(1, idOrdemServico);
				st.setInt(2,osProduto.getIdProduto());
				st.setInt(3,osProduto.getQuantidade());
				st.execute();
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}

	public OrdemServico buscaOrdemServico(Integer idOrdemServico) throws SQLException {
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from ordemdeservico where idordemDeServico = ?;");
		st.setInt(1, idOrdemServico);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " +result == null);
		if (result != null){
			System.out.println("st.getResultSet" + st.getResultSet());
			OrdemServico os = new OrdemServico();
			while(result.next()){
				os.setIdOrdemServico(result.getInt("idordemDeServico"));
				os.setIdCliente(result.getInt("idCliente"));
				os.setIdVeiculo(result.getInt("idVeiculo"));
				os.setData(result.getString("data"));
				os.setStatus(result.getInt("status"));
				os.setTotal(result.getString("total"));
				return os;
			}
		}
		return null;
	}

	public ArrayList<OsProdutos> buscaProdutosOrdemServico(Integer idOrdemDeServico) throws SQLException {
		ArrayList<OsProdutos> produtos = new ArrayList<OsProdutos>();
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from osproduto where idOrdemDeServico = ?;");
		st.setInt(1, idOrdemDeServico);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " +result == null);
		if (result != null){
			while(result.next()){
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
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from osservico where idordemdeservico = ?;");
		st.setInt(1, idOrdemDeServico);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " +result == null);
		if (result != null){
			while(result.next()){
				OsServicos os = new OsServicos();
				os.setIdServico(result.getInt("idservico"));
				os.setQuantidade(result.getInt("quantidade"));
				os.setQtdPorHora(result.getInt("qtdporhora"));
				servicos.add(os);
			}
		}
		return servicos;
	}

	//TODO (arrumar a l�gica do "status" -> n�o tem mais vari�vel status
	public ArrayList<OrdemServico> buscaOrdensServico(Integer status) {
		ArrayList<OrdemServico> ordens = new ArrayList<OrdemServico>();
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from ordemdeservico where status = ?;");
			st.setInt(1, status);
			ResultSet result = st.executeQuery();
			if (result != null){
				System.out.println("st.getResultSet " + st.getResultSet());
				while(result.next()){
					OrdemServico os = new OrdemServico();
					os.setIdOrdemDeServico(result.getInt("idordemdeservico"));
					os.setIdCliente(result.getInt("idcliente"));
					os.setIdVeiculo(result.getInt("idveiculo"));
					os.setEstaExpirado(result.getString("estaexpirado") == "1" ? true:false);
					os.setDataCriacao(result.getString("datacriacao"));
					os.setDataAprovado(result.getString("dataaprovado"));
					os.setDataExecucao(result.getString("dataexecucao"));
					os.setDataFinalizado(result.getString("datafinalizado"));
					os.setDataCancelado(result.getString("datacancelado"));
					os.setTotal(result.getString("total"));
					ordens.add(os);
				}
			}
		}catch (SQLException e) {
			//TODO 
			JOptionPane.showMessageDialog(null, "PENSAR MENSAGEM!!");
			e.printStackTrace();
		}
		return ordens;
	}

	public boolean executaOrdem(Integer idOrdemDeServico, String dataExecucao) {
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico " +
	                "set dataexecucao= ? " +
	                "where idordemdeservico = '"+idOrdemDeServico+"'");
	    	st.setString(1,dataExecucao);
			st.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean finalizaOrdem(Integer idOrdemDeServico, String dataFinalizado) {
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico " +
	                "set datafinalizado= ? " +
	                "where idordemdeservico = '"+idOrdemDeServico+"'");
	    	st.setString(1,dataFinalizado);
			st.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean cancelaOrdemServico(Integer idOrdemDeServico, String dataCancelado) {
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update ordemdeservico " +
	                "set datacancelado= ? " +
	                "where idordemdeservico = '"+idOrdemDeServico+"'");
	    	st.setString(1,dataCancelado);
			st.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
