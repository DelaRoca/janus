package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.janus.Conecta;
import br.com.janus.model.OsProdutos;
import br.com.janus.model.Produto;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class ProdutoController {
	
	private Connection conexao = new Conecta().getConnection();
	//		
	public void salvaProduto(Produto produto){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into produto " +
	                "(descricao,nome,valor) " +
	                "values (?,?,?)");
	    	st.setString(1,produto.getDescricao());
			st.setString(2,produto.getNome());
			st.setString(3,produto.getValor());
			st.execute();
			JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}
	//
	public ArrayList<Produto> buscaProdutos() {
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from produto");
			ResultSet result = st.executeQuery();
			if (result != null){
				ArrayList<Produto> produtos = new ArrayList<Produto>();
				while(result.next()){
					Produto produto = new Produto();
					produto.setIdProduto(result.getInt("idproduto"));
					produto.setNome(result.getString("nome"));
					produto.setDescricao(result.getString("descricao"));
					produto.setValor(result.getString("valor"));
					produtos.add(produto);
				}
				return produtos;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//
	public ArrayList<Produto> buscaProdutos(ArrayList<OsProdutos> osProdutos) {
		try{
			for (OsProdutos osProduto : osProdutos) {
				PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from produto where idProduto = ?");
				st.setInt(1, osProduto.getIdProduto());
				ResultSet result = st.executeQuery();
				if (result != null){
					ArrayList<Produto> produtos = new ArrayList<Produto>();
					while(result.next()){
						Produto produto = new Produto();
						produto.setIdProduto(result.getInt("idproduto"));
						produto.setNome(result.getString("nome"));
						produto.setDescricao(result.getString("descricao"));
						produto.setValor(result.getString("valor"));
						produtos.add(produto);
					}
					return produtos;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
