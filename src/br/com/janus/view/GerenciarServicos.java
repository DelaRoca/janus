package br.com.janus.view;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.com.janus.controller.ServicoController;
import br.com.janus.model.Servico;

public class GerenciarServicos extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel tabelaModelo = new DefaultTableModel();
	private JTable tabelaServico;

	public GerenciarServicos() {
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Gerenciar Serviços");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 980, 58);
		add(lblTitulo);
		
		populaTabela();

		JScrollPane scrollS = new JScrollPane(tabelaServico);
		scrollS.setBounds(175, 126, 650, 243);
		add(scrollS);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			// TODO implementar
		});
		btnSalvar.setBounds(411, 484, 89, 23);
		add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(573, 484, 89, 23);
		add(btnCancelar);

	}

	private void populaTabela() {
		tabelaServico = new JTable(tabelaModelo){

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
		tabelaModelo.addColumn("Habilitado");
		tabelaModelo.addColumn("Nome");
		tabelaModelo.addColumn("Valor/h");
		tabelaModelo.addColumn("Descrição");

		tabelaModelo.setNumRows(0);
		ArrayList<Servico> servicos = new ServicoController().buscaServicos();
		for (Servico servico : servicos) {
			if(servico.getPorHora()){
				System.out.println("Estou no ifs");
				tabelaModelo.addRow(new Object[]{true,servico.getNome(), servico.getValor(),servico.getDescricao()});
			}else				
				tabelaModelo.addRow(new Object[]{false,servico.getNome(), servico.getValor(),servico.getDescricao()});
		}
		
		tabelaServico.getTableHeader().setReorderingAllowed(false);
		tabelaServico.getColumnModel().getColumn(0).setPreferredWidth(75);
		tabelaServico.getColumnModel().getColumn(1).setPreferredWidth(225); 
		tabelaServico.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabelaServico.getColumnModel().getColumn(3).setPreferredWidth(250);
	}
	
}
