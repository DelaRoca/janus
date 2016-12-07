package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.janus.Conecta;
import br.com.janus.model.Endereco;

public class EnderecoController {
	
	private Connection conexao = new Conecta().getConnection();
	//
	public Endereco buscaEndereco(Integer idEndereco) throws SQLException {
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from endereco where idEndereco = ?;");
		st.setString(1, idEndereco.toString());
		ResultSet result = st.executeQuery();
		if (result != null){
			Endereco endereco = new Endereco();
			while(result.next()){
				endereco.setIdEndereco(result.getInt("idEndereco"));
				endereco.setRua(result.getString("rua"));
				endereco.setBairro(result.getString("bairro"));
				endereco.setCidade(result.getString("cidade"));
				endereco.setEstado(result.getString("estado"));
				return endereco;
			}
		}
		return null;
	}

}
