package br.com.janus.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.janus.Conecta;
import br.com.janus.model.Cliente;
import br.com.janus.model.Veiculo;
import br.com.janus.view.GerenciadorDeInterface;
import br.com.janus.view.Principal;

public class VeiculoController {

	private Connection conexao = new Conecta().getConnection();

	public boolean verificaVeiculo (String placa) throws Exception {
		return buscaDadosVeiculoPlaca(placa) != null;
	}
	
	public Veiculo buscaDadosVeiculoPlaca(String placa) throws SQLException {
		System.out.println("aqui no busca dados do veiculo, placa : " + placa);
		PreparedStatement st = (PreparedStatement) conexao.prepareStatement("select * from veiculo where placa = ?;");
		st.setString(1, placa);
		ResultSet result = st.executeQuery();
		System.out.println("result set : " + result == null);
		if (result != null) {
			System.out.println("st.getResultSet" + st.getResultSet());
			Veiculo veiculo = new Veiculo();
			while (result.next()) {
				veiculo.setIdVeiculo(result.getInt("idVeiculo"));
				veiculo.setPlaca(result.getString("placa"));
				veiculo.setFabricante(result.getString("fabricante"));
				veiculo.setModelo(result.getString("modelo"));
				veiculo.setAno(result.getString("ano"));
				return veiculo;
			}
		}
		return null;
	}

	public void salvaVeiculo(Veiculo veiculo) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("insert into veiculo "
					+ "(ano,fabricante,modelo,placa) " + "values (?,?,?,?)");
			st.setString(1, veiculo.getAno());
			st.setString(2, veiculo.getFabricante());
			st.setString(3, veiculo.getModelo());
			st.setString(4, veiculo.getPlaca());
			
			st.execute();
			JOptionPane.showMessageDialog(null, "Veiculo cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
			e.printStackTrace();
		}
	}
	

	public void atualizaVeiculo(Veiculo veiculo) {
		try {
			PreparedStatement st = (PreparedStatement) conexao.prepareStatement("update veiculo "
					+ "set ano = ?,fabricante = ?,modelo = ? "
					+ "where placa = '" + veiculo.getPlaca() + "'");
			st.setString(1, veiculo.getAno());
			st.setString(2, veiculo.getFabricante());
			st.setString(3, veiculo.getModelo());
			st.execute();
			JOptionPane.showMessageDialog(null, "Veiculo cadastrado com sucesso!");
			GerenciadorDeInterface.setPanel(new Principal());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Dados invalidos, tente novamente");
			e.printStackTrace();
		}
	
	}

}
