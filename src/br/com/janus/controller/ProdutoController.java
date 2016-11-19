package br.com.janus.controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import br.com.janus.Conecta;
import br.com.janus.model.Produto;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class ProdutoController {
	
	private Connection conexao = new Conecta().getConnection();
	
	public void salvaProduto(Produto produto){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into produto " +
	                "(descricao,nome,valor) " +
	                "values (?,?,?)");
	    	st.setString(1,produto.getDescricao());
			st.setString(2,produto.getNome());
			st.setString(3,produto.getValor());
			st.execute();
			JOptionPane.showMessageDialog(null, "produto cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
			e.printStackTrace();
		}
	}

}
