package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import br.com.janus.Conecta;
import br.com.janus.model.Servico;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class ServicoController {
	
	private Connection conexao = new Conecta().getConnection();
	
	public void salvaServico(Servico servico){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into servico " +
	                "(descricao,nome,porHora,valor,estaAtivo) " +
	                "values (?,?,?,?,?)");
	    	st.setString(1,servico.getDescricao());
			st.setString(2,servico.getNome());
			st.setString(3,setPortHora(servico));
			st.setString(4,servico.getValor());
			st.setString(5,setEstatAtivo(servico));
			st.execute();
			JOptionPane.showMessageDialog(null, "serviÃ§o cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados invÃ¡lidos, tente novamente");
			e.printStackTrace();
		}
	}

	private String setPortHora(Servico servico) {
		if(servico.getPorHora()){
			return "1";
		}
		return "0";
	}
	
	private String setEstatAtivo(Servico servico) {
		if(servico.getEstaAtivo()){
			return "1";
		}
		return "0";
	}

	public ArrayList<Servico> buscaServicos() {
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from servico");
			ResultSet result = st.executeQuery();
			System.out.println("result set : " +result == null);
			if (result != null){
				ArrayList<Servico> servicos = new ArrayList<Servico>();
				while(result.next()){
					Servico servico = new Servico();
					servico.setIdServico(result.getInt("idservico"));
					servico.setNome(result.getString("nome"));
					servico.setDescricao(result.getString("descricao"));
					servico.setValor(result.getString("valor"));
					if(result.getString("porHora").equals("1") ){
						servico.setPorHora(true);
					} else{
						servico.setPorHora(false);
					}
					if(result.getString("estaAtivo").equals("1") ){
						servico.setEstaAtivo(true);
					} else{
						servico.setEstaAtivo(false);
					}
					servicos.add(servico);
				}
				return servicos;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public void atualizaServicos(ArrayList<Servico> servicos) {
		for (Servico servico : servicos) {
			try {
		    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update servico " +
		                "set estaAtivo = ? " +
		                "where idservico = ?");
		    	if(servico.getEstaAtivo()){
		    		st.setString(1,"1");
		    	}else{
		    		st.setString(1,"0");
		    	}
				st.setInt(2,servico.getIdServico());
				st.execute();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Não foi possível salvar as alterações. Por favor, tente novamente");
				GerenciadorDeInterface.setPanel(new Principal());
				e.printStackTrace();
				
			}
		}
		JOptionPane.showMessageDialog(null, "Alteração salva com sucesso");
		GerenciadorDeInterface.setPanel(new Principal());
		
	}

	

}
