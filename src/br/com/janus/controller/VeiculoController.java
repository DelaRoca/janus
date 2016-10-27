package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.janus.Conecta;
import br.com.janus.model.Veiculo;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class VeiculoController {
	
	private Connection conexao = new Conecta().getConnection();
	
//	public Veiculo buscaDadosVeiculoCpf(String cpf) throws SQLException{
//		System.out.println("aqui no busca dados do veiculo, cpf : "+ cpf);
//		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from veiculo where cpf = ?;");
//		st.setString(1, cpf);
//		ResultSet result = st.executeQuery();
//		System.out.println("result set : " +result == null);
//		if (result != null){
//			System.out.println("st.getResultSet" + st.getResultSet());
//			Veiculo veiculo = new Veiculo();
//			while(result.next()){
//				//comentarios
//				veiculo.setIdVeiculo(result.getInt("idVeiculo"));
//				veiculo.setNome(result.getString("nome"));
//				veiculo.setCpf(result.getString("cpf"));
//				veiculo.setDataNascimento(result.getString("dataNascimento"));
//				veiculo.setEmail(result.getString("email"));
//				veiculo.setTelefone(result.getString("telefone"));
//				veiculo.setCelular(result.getString("celular"));
//				veiculo.setEndereco(Integer.parseInt(result.getString("idendereco")));
//				return veiculo;
//			}
//		}
//		return null;
//	}
	
//	public void salvaVeiculo(Veiculo veiculo, Integer idEndereco){
//	    try {
//	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into veiculo " +
//	                "(cpf,cnpj,nome,dataNascimento,telefone,email,celular,idEndereco) " +
//	                "values (?,?,?,?,?,?,?,?)");
//	    	st.setString(1, veiculo.getCpf());
//			st.setString(2,veiculo.getCnpj());
//			st.setString(3,veiculo.getNome());
//		    st.setString(4,veiculo.getDataNascimento());
//		    st.setString(5,veiculo.getTelefone());
//		    st.setString(6,veiculo.getEmail());
//		    st.setString(7,veiculo.getCelular());
//		    System.out.println(veiculo.getEndereco().toString() + "veiculo.getEndereco().toString()");
//			st.setString(8,veiculo.getEndereco().toString());
//			st.execute();
//			JOptionPane.showMessageDialog(null, "veiculo cadastrado com sucesso!");
//			GerenciadorDeInterface.setPanel(new Principal());
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
//			e.printStackTrace();
//		}
//	}

//	public void atualizaVeiculo(Veiculo veiculo, Integer idEndereco){
//	    try {
//	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update veiculo " +
//	                "set cpf= ?,cnpj= ?,nome= ?,dataNascimento= ?,telefone= ?,email= ?,celular= ?,idEndereco=? " +
//	                "where nome = '"+veiculo.getNome()+"'");
//	    	st.setString(1, veiculo.getCpf());
//			st.setString(2,veiculo.getCnpj());
//			st.setString(3,veiculo.getNome());
//		    st.setString(4,veiculo.getDataNascimento());
//		    st.setString(5,veiculo.getTelefone());
//		    st.setString(6,veiculo.getEmail());
//		    st.setString(7,veiculo.getCelular());
//			st.setString(8,veiculo.getEndereco().toString());
//			st.execute();
//			JOptionPane.showMessageDialog(null, "veiculo cadastrado com sucesso!");
//			GerenciadorDeInterface.setPanel(new Principal());
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
//			e.printStackTrace();
//		}
//	}
	
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
	
	private Integer buscaMaiorIdVeiculo() throws SQLException{
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select Max(idVeiculo) as maiorId from veiculo;");
		ResultSet result = st.executeQuery();
		while(result.next()){
			return Integer.parseInt(result.getString("maiorId")) + 1;
		}
		return 99999;
	}
	
//	public String salvaEndereco(Endereco endereco) {
//		
//		try {
//			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into endereco " +
//			        "(idEndereco,rua,bairro,cidade,estado) " +
//			        "values (?,?,?,?,?)");
//			st.setString(1, buscaMaiorIdEndereco().toString());
//			st.setString(2, endereco.getRua());
//			st.setString(3,endereco.getBairro());
//			st.setString(4,endereco.getCidade()); 
//		    st.setString(5,endereco.getEstado());
//		    
//		    int result = st.executeUpdate();
//		    if(result == 1){
//		    	PreparedStatement ps = (PreparedStatement) conexao.prepareStatement("select max(idEndereco) as max from endereco; ");
//		    	ResultSet rs = ps.executeQuery();
//		    	while(rs.next()){
//		    		return rs.getString("max");
//		    	}
//		    }
//		    		    
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
//			e.printStackTrace();
//		}
//		return "0";
//	}


//	public Integer atualizaEndereco(Endereco endereco) {
//		try {
//			System.out.println("endereco.getIdEndereco()"+endereco.getIdEndereco());
//	    	PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update endereco " +
//	    			"set rua = ?,bairro = ?,cidade = ?,estado= ? " +
//	                "where idEndereco = '"+endereco.getIdEndereco()+"';");
//			st.setString(1,endereco.getRua() == null ? null : endereco.getRua());
//			st.setString(2,endereco.getBairro()== null ? null : endereco.getBairro());
//		    st.setString(3,endereco.getCidade()== null ? null : endereco.getCidade());
//		    st.setString(4, endereco.getEstado()== null ? null : endereco.getEstado());
//			int executeUpdate = st.executeUpdate();
//			System.out.println("executeUpdate atualizaEndereco"+ executeUpdate);
//			if (executeUpdate == 1){
//				return endereco.getIdEndereco();
//			}
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Dados inv�lidos, tente novamente");
//			e.printStackTrace();
//		}
//		return 0;
//	}
}
