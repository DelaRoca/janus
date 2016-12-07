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
	
	private DefaultTableModel tabelaModelo = new DefaultTableModel() {
        boolean[] chckbxAtivos = new boolean[]{true, false, false, false, false};

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            	return chckbxAtivos[columnIndex];
        }
	};
			
	private JTable tabelaServico;
	private ArrayList<Servico> servicos = new ArrayList<Servico>();
	
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
			salvaServicos();
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

	private void salvaServicos() {
		for (int count = 0; count < tabelaModelo.getRowCount(); count++){
			boolean estaAtivo = Boolean.parseBoolean(tabelaModelo.getValueAt(count, 0).toString());
			servicos.get(count).setEstaAtivo(estaAtivo);
		}
		new ServicoController().atualizaServicos(servicos);
	}

	private void populaTabela() {
		
		tabelaServico = new JTable(tabelaModelo){

            private static final long serialVersionUID = 1L;

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
                    case 4:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
		tabelaModelo.addColumn("Ativo");
		tabelaModelo.addColumn("Nome");
		tabelaModelo.addColumn("Valor (R$)");
		tabelaModelo.addColumn("Por Hora");
		tabelaModelo.addColumn("Descri��o");
		tabelaModelo.setNumRows(0);
		tabelaServico.getTableHeader().setReorderingAllowed(false);
		tabelaServico.getColumnModel().getColumn(0).setPreferredWidth(30);
		tabelaServico.getColumnModel().getColumn(1).setPreferredWidth(225); 
		tabelaServico.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabelaServico.getColumnModel().getColumn(3).setPreferredWidth(50);
		tabelaServico.getColumnModel().getColumn(4).setPreferredWidth(235);
		
		servicos = new ServicoController().buscaServicos();
		for (Servico servico : servicos) {
			String porHora = "";
			if(servico.getPorHora()){
				porHora = "Sim";
			}else{
				porHora = "N�o";
			}
			tabelaModelo.addRow(new Object[]{servico.getEstaAtivo(), servico.getNome(), servico.getValor(), porHora, servico.getDescricao()});
		}
	}
	
}
