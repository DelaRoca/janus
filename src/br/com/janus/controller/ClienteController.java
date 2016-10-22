package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.janus.Conecta;
import br.com.janus.model.Cliente;
import br.com.janus.model.Endereco;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class ClienteController {
	
	private Connection conexao = new Conecta().getConnection();
	
	public Cliente buscaDadosClienteCpf(String cpf) throws SQLException{
		System.out.println("aqui no busca dados do cliente, cpf : "+ cpf);
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where cpf = ?;");
		st.setString(1, cpf);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " +result == null);
		if (result != null){
			System.out.println("st.getResultSet" + st.getResultSet());
			Cliente cliente = new Cliente();
			while(result.next()){
				//comentarios
				cliente.setIdCliente(result.getInt("idCliente"));
				cliente.setNome(result.getString("nome"));
				cliente.setCpf(result.getString("cpf"));
				cliente.setDataNascimento(result.getString("dataNascimento"));
				cliente.setEmail(result.getString("email"));
				cliente.setTelefone(result.getString("telefone"));
				cliente.setCelular(result.getString("celular"));
				cliente.setEndereco(Integer.parseInt(result.getString("idendereco")));
				return cliente;
			}
		}
		return null;
	}
	
	public void salvaCliente(Cliente cliente, Integer idEndereco){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into cliente " +
	                "(cpf,cnpj,nome,dataNascimento,telefone,email,celular,idEndereco) " +
	                "values (?,?,?,?,?,?,?,?)");
	    	st.setString(1, cliente.getCpf());
			st.setString(2,cliente.getCnpj());
			st.setString(3,cliente.getNome());
		    st.setString(4,cliente.getDataNascimento());
		    st.setString(5,cliente.getTelefone());
		    st.setString(6,cliente.getEmail());
		    st.setString(7,cliente.getCelular());
		    System.out.println(cliente.getEndereco().toString() + "cliente.getEndereco().toString()");
			st.setString(8,cliente.getEndereco().toString());
			st.execute();
			JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
			e.printStackTrace();
		}
	}

	public void atualizaCliente(Cliente cliente, Integer idEndereco){
	    try {
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update cliente " +
	                "set cpf= ?,cnpj= ?,nome= ?,dataNascimento= ?,telefone= ?,email= ?,celular= ?,idEndereco=? " +
	                "where nome = '"+cliente.getNome()+"'");
	    	st.setString(1, cliente.getCpf());
			st.setString(2,cliente.getCnpj());
			st.setString(3,cliente.getNome());
		    st.setString(4,cliente.getDataNascimento());
		    st.setString(5,cliente.getTelefone());
		    st.setString(6,cliente.getEmail());
		    st.setString(7,cliente.getCelular());
			st.setString(8,cliente.getEndereco().toString());
			st.execute();
			JOptionPane.showMessageDialog(null, "cliente cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
			e.printStackTrace();
		}
	}
	
	private Integer buscaMaiorIdEndereco() throws SQLException{
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select Max(idEndereco) as maiorId from endereco;");
		ResultSet result = st.executeQuery();
		while(result.next()){
			String maiorId = result.getString("maiorId");
			if(maiorId == null){
				maiorId = "0";
			}
			System.out.println("maiorId" + maiorId);
			return Integer.parseInt(maiorId) + 1;
		}
		return 99999;
	}
	
	private Integer buscaMaiorIdCliente() throws SQLException{
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select Max(idCliente) as maiorId from cliente;");
		ResultSet result = st.executeQuery();
		while(result.next()){
			return Integer.parseInt(result.getString("maiorId")) + 1;
		}
		return 99999;
	}
	
	public String salvaEndereco(Endereco endereco) {
		
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into endereco " +
			        "(idEndereco,rua,bairro,cidade,estado) " +
			        "values (?,?,?,?,?)");
			st.setString(1, buscaMaiorIdEndereco().toString());
			st.setString(2, endereco.getRua());
			st.setString(3,endereco.getBairro());
			st.setString(4,endereco.getCidade()); 
		    st.setString(5,endereco.getEstado());
		    
		    int result = st.executeUpdate();
		    if(result == 1){
		    	PreparedStatement ps = (PreparedStatement) conexao.prepareStatement("select max(idEndereco) as max from endereco; ");
		    	ResultSet rs = ps.executeQuery();
		    	while(rs.next()){
		    		return rs.getString("max");
		    	}
		    }
		    		    
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
			e.printStackTrace();
		}
		return "0";
	}

	public Cliente buscaDadosClienteCnpj(String cnpj) throws SQLException{
		System.out.println("aqui no busca dados do cliente, cnpj : "+ cnpj);
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from cliente where cnpj = ?;");
		st.setString(1, cnpj);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " +result == null);
		if (result != null){
			System.out.println("st.getResultSet" + st.getResultSet());
			Cliente cliente = new Cliente();
			while(result.next()){
				cliente.setIdCliente(Integer.parseInt(result.getString("idCliente")));
				cliente.setNome(result.getString("nome"));
				cliente.setCnpj(result.getString("cnpj"));
				cliente.setDataNascimento(result.getString("dataNascimento"));
				cliente.setEmail(result.getString("email"));
				cliente.setTelefone(result.getString("telefone"));
				cliente.setCelular(result.getString("celular"));
				cliente.setEndereco(Integer.parseInt(result.getString("idendereco")));
				return cliente;
			}
		}
		return null;
	}

	public Integer atualizaEndereco(Endereco endereco) {
		try {
			System.out.println("endereco.getIdEndereco()"+endereco.getIdEndereco());
	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update endereco " +
	    			"set rua = ?,bairro = ?,cidade = ?,estado= ? " +
	                "where idEndereco = '"+endereco.getIdEndereco()+"';");
			st.setString(1,endereco.getRua() == null ? null : endereco.getRua());
			st.setString(2,endereco.getBairro()== null ? null : endereco.getBairro());
		    st.setString(3,endereco.getCidade()== null ? null : endereco.getCidade());
		    st.setString(4, endereco.getEstado()== null ? null : endereco.getEstado());
			int executeUpdate = st.executeUpdate();
			System.out.println("executeUpdate atualizaEndereco"+ executeUpdate);
			if (executeUpdate == 1){
				return endereco.getIdEndereco();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
			e.printStackTrace();
		}
		return 0;
	}
}
