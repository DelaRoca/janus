package br.com.janus.controller;

import java.sql.SQLException;
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
	                "(descricao,nome,porHora,valor) " +
	                "values (?,?,?,?)");
	    	st.setString(1,servico.getDescricao());
			st.setString(2,servico.getNome());
			st.setString(3,setPortHora(servico));
			st.setString(4,servico.getValor());
			st.execute();
			JOptionPane.showMessageDialog(null, "serviço cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados invï¿½lidos, tente novamente");
			e.printStackTrace();
		}
	}

	private String setPortHora(Servico servico) {
		if(servico.getPorHora()){
			return "1";
		}
		return "0";
	}

}
