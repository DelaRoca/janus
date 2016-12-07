package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.janus.Conecta;
import br.com.janus.model.Cliente;
import br.com.janus.model.ClienteFisico;
import br.com.janus.model.ClienteJuridico;
import br.com.janus.model.Endereco;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class ClienteController {
	
	private Connection conexao = new Conecta().getConnection();
	
	public Cliente buscaDadosclienteId(Integer idCliente) throws SQLException {
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where idcliente = ?;");
			st.setInt(1, idCliente);
			ResultSet result = st.executeQuery();
			if (result != null){
				while(result.next()){
						Cliente cliente = new Cliente();
						cliente.setIdCliente(result.getInt("idcliente"));
						cliente.setNome(result.getString("nome"));
						return cliente;
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ClienteFisico buscaDadosclienteFisicoId(Integer idCliente) throws SQLException {
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where idcliente = ?;");
			st.setInt(1, idCliente);
			ResultSet result = st.executeQuery();
			if (result != null){
				while(result.next()){
						ClienteFisico cliente = new ClienteFisico();
						cliente.setCpf(result.getString("cpf"));
						cliente.setDataNascimento(result.getString("datanascimento"));
						cliente.setIdCliente(result.getInt("idcliente"));
						cliente.setNome(result.getString("nome"));
						cliente.setTelefone(result.getString("telefone"));
						return cliente;
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ClienteJuridico buscaDadosclienteJuridicoId(Integer idCliente) throws SQLException {
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where idcliente = ?;");
			st.setInt(1, idCliente);
			ResultSet result = st.executeQuery();
			if (result != null){
				while(result.next()){
						ClienteJuridico cliente = new ClienteJuridico();
						cliente.setCnpj(result.getString("cnpj"));
						cliente.setIdCliente(result.getInt("idcliente"));
						cliente.setNome(result.getString("nome"));
						cliente.setTelefone(result.getString("telefone"));
						return cliente;
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean verificaClienteFisico(Integer idCliente) throws SQLException {
		try{
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where idcliente = ?;");
			st.setInt(1, idCliente);
			ResultSet result = st.executeQuery();
			if (result != null){
				while(result.next()){
					return result.getString("cpf") != null;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ClienteFisico buscaDadosClienteCpf(String cpf) throws SQLException{
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where cpf = ?;");
		st.setString(1, cpf);
		ResultSet result = st.executeQuery();
		if (result != null){
			ClienteFisico cliente = new ClienteFisico();
			while(result.next()){
				cliente.setIdCliente(result.getInt("idcliente"));
				cliente.setNome(result.getString("nome"));
				cliente.setCpf(result.getString("cpf"));
				cliente.setDataNascimento(result.getString("datanascimento"));
				cliente.setEmail(result.getString("email"));
				cliente.setTelefone(result.getString("telefone"));
				cliente.setCelular(result.getString("celular"));
				cliente.setEndereco(Integer.parseInt(result.getString("idendereco")));
				return cliente;
			}
		}
		return null;
	}
	
	public ClienteJuridico buscaDadosClienteCnpj(String cnpj) throws SQLException{
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where cnpj = ?;");
			st.setString(1, cnpj);
			ResultSet result = st.executeQuery();
			if (result != null){
				ClienteJuridico cliente = new ClienteJuridico();
				while(result.next()){
					cliente.setIdCliente(Integer.parseInt(result.getString("idcliente")));
					cliente.setNome(result.getString("nome"));
					cliente.setCnpj(result.getString("cnpj"));
					cliente.setEmail(result.getString("email"));
					cliente.setTelefone(result.getString("telefone"));
					cliente.setCelular(result.getString("celular"));
					cliente.setEndereco(Integer.parseInt(result.getString("idendereco")));
					return cliente;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void salvaCliente(ClienteFisico clienteFisico, Integer idEndereco){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into cliente " +
	                "(cpf,nome,datanascimento,telefone,email,celular,idendereco) " +
	                "values (?,?,?,?,?,?,?)");
	    	st.setString(1, clienteFisico.getCpf());
			st.setString(2,clienteFisico.getNome());
		    st.setString(3,clienteFisico.getDataNascimento());
		    st.setString(4,clienteFisico.getTelefone());
		    st.setString(5,clienteFisico.getEmail());
		    st.setString(6,clienteFisico.getCelular());
			st.setString(7,clienteFisico.getEndereco().toString());
			st.execute();
			JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}
	
	public void salvaCliente(ClienteJuridico clienteJuridico, Integer idEndereco){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into cliente " +
	                "(cnpj,nome,telefone,email,celular,idendereco) " +
	                "values (?,?,?,?,?,?)");
			st.setString(1,clienteJuridico.getCnpj());
			st.setString(2,clienteJuridico.getNome());
		    st.setString(3,clienteJuridico.getTelefone());
		    st.setString(4,clienteJuridico.getEmail());
		    st.setString(5,clienteJuridico.getCelular());
			st.setString(6,clienteJuridico.getEndereco().toString());
			st.execute();
			JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}
	
	public void atualizaCliente(ClienteFisico clienteFisico){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update cliente " +
	                "set cpf= ?,nome= ?,datanascimento= ?,telefone= ?,email= ?,celular= ? " +
	                "where cpf = '"+clienteFisico.getCpf()+"'");
	    	st.setString(1,clienteFisico.getCpf());
			st.setString(2,clienteFisico.getNome());
		    st.setString(3,clienteFisico.getDataNascimento());
		    st.setString(4,clienteFisico.getTelefone());
		    st.setString(5,clienteFisico.getEmail());
		    st.setString(6,clienteFisico.getCelular());
			st.execute();
			JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}
	
	public void atualizaCliente(ClienteJuridico clienteJuridico){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update cliente " +
	                "set cnpj= ?,nome= ?,telefone= ?,email= ?,celular= ? " +
	                "where cnpj = ?;");
			st.setString(1,clienteJuridico.getCnpj());
			st.setString(2,clienteJuridico.getNome());
		    st.setString(3,clienteJuridico.getTelefone());
		    st.setString(4,clienteJuridico.getEmail());
		    st.setString(5,clienteJuridico.getCelular());
			st.setString(6,clienteJuridico.getCnpj());
			st.execute();
			JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}
	
	public String salvaEndereco(Endereco endereco) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into endereco " +
					"(rua,bairro,cidade,estado) " +
			        "values (?,?,?,?)");
			st.setString(1, endereco.getRua());
			st.setString(2,endereco.getBairro());
			st.setString(3,endereco.getCidade()); 
		    st.setString(4,endereco.getEstado());		    
		    int result = st.executeUpdate();
		    if(result == 1){
		    	PreparedStatement ps = (PreparedStatement) conexao.prepareStatement("select max(idendereco) as max from endereco; ");
		    	ResultSet rs = ps.executeQuery();
		    	while(rs.next()){
		    		return rs.getString("max");
		    	}
		    }
		    		    
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
		return "0";
	}
	
	public Integer atualizaEndereco(Endereco endereco) {
		try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update endereco " +
	    			"set rua = ?,bairro = ?,cidade = ?,estado= ? " +
	                "where idendereco = ?;");
			st.setString(1,endereco.getRua() == null ? null : endereco.getRua());
			st.setString(2,endereco.getBairro()== null ? null : endereco.getBairro());
		    st.setString(3,endereco.getCidade()== null ? null : endereco.getCidade());
		    st.setString(4,endereco.getEstado()== null ? null : endereco.getEstado());
		    st.setInt(5,endereco.getIdEndereco());
			int executeUpdate = st.executeUpdate();
			if (executeUpdate == 1){
				return endereco.getIdEndereco();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
		return 0;
	}
}
