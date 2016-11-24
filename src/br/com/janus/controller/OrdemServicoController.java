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
		    
		    ResultSet rs = st.executeQuery("SELECT MAX(idordemDeServico) as pau FROM ordemdeservico");
		    while(rs.next()){
		    	idOrdemServico = rs.getInt("pau");
		    }
//		    ResultSet result = st.executeQuery();
//			System.out.println("result set : " +result == null);
//			if (result != null){
//				System.out.println("st.getResultSet" + st.getResultSet());
//				while(result.next()){
//					idOrdemServico =result.getInt("idordemDeServico");
//					return;
//				}
//			}
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
						"(idOrdemServico,idServico,quantidade,qtdPorHora) " +
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

}
