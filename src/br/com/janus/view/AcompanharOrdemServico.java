package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.janus.StatusENUM;
import br.com.janus.controller.ClienteController;
import br.com.janus.controller.OrdemServicoController;
import br.com.janus.controller.ProdutoController;
import br.com.janus.controller.ServicoController;
import br.com.janus.controller.VeiculoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.OrdemServico;
import br.com.janus.model.OsProdutos;
import br.com.janus.model.OsServicos;
import br.com.janus.model.Produto;
import br.com.janus.model.Servico;
import br.com.janus.model.Veiculo;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

public class AcompanharOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldOrdemServico;
	private JTextField textFieldData;
	private JComboBox<String> comboBoxStatus;
	private JTextField textFieldCpfCnpj;
	private JTextField textFieldNome;
	private JTextField textFieldTelefone;
	private JTextField textFieldPlaca;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JTextField textFieldTotal;
	
	private JTable tabelaProduto;
	private JTable tabelaServico;

	private Double parcialDoubleServico = 0.0;
	private Double parcialDoubleProduto = 0.0;
	private Double valorDoubleTotal = 0.0;
	
	private OrdemServico ordemServico;
	private Cliente clienteAtual;
	private Veiculo veiculoAtual;
	private ArrayList<Produto> produtos;
	private ArrayList<Servico> servicos;
	
	
	private DefaultTableModel tabelaModeloProduto = new DefaultTableModel() {
        boolean[] selecionado = new boolean[]{true, false, false, true, false};
        boolean[] naoSelecionado = new boolean[]{true, false, false, false, false};
                
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (((Boolean) getValueAt(rowIndex, 0)).booleanValue()) {
            	return selecionado[columnIndex];
            }
            else {
            	setValueAt("0", rowIndex, 3);
            	return naoSelecionado[columnIndex];
            }
        }
        
        @Override
        public Object getValueAt(int row, int column) {
            if (column == 4) {
                if (getValueAt(row, 2).equals("") && getValueAt(row, 3).equals("")){
                    return super.getValueAt(row, column);
                } else {
                	String valorStr = getValueAt(row, 2).toString();
                	valorStr = valorStr.replace(",", ".");
                	double valor = Double.parseDouble(valorStr);
                	Integer quantidade = Integer.parseInt(getValueAt(row, 3).toString());
                    double resultado = 0.0;
                    if (quantidade < 0) {
                    	quantidade = 0;
                    }
                    resultado = valor * quantidade;
                    String resultadoStr = String.valueOf(resultado);
                    resultadoStr = resultadoStr.replace(".", ",");
                    return resultadoStr;
                }
            }
            return super.getValueAt(row, column);
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);
            
            preencheValorTotalOrdemServico();
			
            tabelaModeloProduto.fireTableCellUpdated(row, 4);
        }
	};

	
	private DefaultTableModel tabelaModeloServico = new DefaultTableModel() {
        boolean[] selecionado = new boolean[]{true, false, false, false, true, false};
        boolean[] selecionadoPorHora = new boolean[]{true, false, false, true, true, false};
        boolean[] naoSelecionado = new boolean[]{true, false, false, false, false, false};
                
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (((Boolean) getValueAt(rowIndex, 0)).booleanValue()) { 
            	if ( Integer.parseInt(getValueAt(rowIndex, 3).toString()) > 0  ) {
            		return selecionadoPorHora[columnIndex];
            	} else {
            		return selecionado[columnIndex];
            	}
            }
            else {
            	setValueAt("0", rowIndex, 4);
            	return naoSelecionado[columnIndex];
            }
        }
	
        @Override
        public Object getValueAt(int row, int column) {
            if (column == 5) {
                if (getValueAt(row, 2).equals("") && getValueAt(row, 4).equals("")){
                    return super.getValueAt(row, column);
                } else {
                	String valorStr = getValueAt(row, 2).toString();
                	valorStr = valorStr.replace(",", ".");
                	double valor = Double.parseDouble(valorStr);
                	Integer quantidade = Integer.parseInt(getValueAt(row, 4).toString());
                	Integer valorPorHora = Integer.parseInt(getValueAt(row, 3).toString());
                    double resultado = 0.0;
                    if (quantidade < 0) {
                    	quantidade = 0;
                    }
                    if (valorPorHora > 0) {
                    	resultado = (valor * quantidade) * valorPorHora;	
                    } else {
                    	resultado = valor * quantidade;
                    }
                    String resultadoStr = String.valueOf(resultado);
                    resultadoStr = resultadoStr.replace(".", ",");
                    return resultadoStr;
                }
            }
            return super.getValueAt(row, column);
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);
            
            if (column == 3) {
            	int testePorHora = Integer.parseInt(aValue.toString());
            	if (testePorHora < 1){
            		setValueAt("1", row, column);
            	}
            }
            
            preencheValorTotalOrdemServico();
			
           	fireTableCellUpdated(row, 5);
        }   
	};

	public AcompanharOrdemServico() throws ParseException {
		setLayout(null);

		JLabel lblRegistroOrdemServico = new JLabel("Acompanhar Ordem de Servi\u00E7o");
		lblRegistroOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroOrdemServico.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblRegistroOrdemServico.setBounds(10, 11, 980, 48);
		add(lblRegistroOrdemServico);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(81, 203, 60, 25);
		add(lblNome);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCpfcnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCpfcnpj.setBounds(81, 171, 60, 25);
		add(lblCpfcnpj);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModelo.setBounds(570, 203, 60, 25);
		add(lblModelo);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAno.setBounds(570, 233, 60, 25);
		add(lblAno);

		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlaca.setBounds(570, 171, 60, 25);
		add(lblPlaca);

		textFieldNome = new JTextField();
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(151, 203, 304, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldCpfCnpj = new JTextField();
		textFieldCpfCnpj.setEditable(false);
		textFieldCpfCnpj.setColumns(10);
		textFieldCpfCnpj.setBounds(151, 171, 205, 25);
		add(textFieldCpfCnpj);

		textFieldModelo = new JTextField();
		textFieldModelo.setEditable(false);
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(638, 203, 244, 25);
		add(textFieldModelo);

		textFieldAno = new JTextField();
		textFieldAno.setEditable(false);
		textFieldAno.setColumns(10);
		textFieldAno.setBounds(638, 233, 126, 25);
		add(textFieldAno);

		textFieldPlaca = new JTextField();
		textFieldPlaca.setEditable(false);
		textFieldPlaca.setColumns(10);
		textFieldPlaca.setBounds(638, 171, 131, 25);
		add(textFieldPlaca);

		JLabel lblData = new JLabel("Data de Registro:");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setBounds(532, 70, 100, 25);
		add(lblData);

		textFieldData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldData.setEditable(false);
		textFieldData.setBounds(644, 70, 100, 25);
		add(textFieldData);
		textFieldData.setColumns(10);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(46, 142, 46, 14);
		add(lblCliente);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setBounds(81, 234, 60, 25);
		add(lblTelefone);

		textFieldTelefone = new JTextField();
		textFieldTelefone.setEditable(false);
		textFieldTelefone.setBounds(151, 234, 205, 25);
		add(textFieldTelefone);
		textFieldTelefone.setColumns(10);

		JLabel lblCaixaCliente = new JLabel("");
		lblCaixaCliente.setBounds(46, 160, 416, 108);
		lblCaixaCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(lblCaixaCliente);

		JLabel lblVeiculo = new JLabel("Veículo:");
		lblVeiculo.setBounds(532, 142, 46, 14);
		add(lblVeiculo);

		JLabel label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(532, 160, 416, 108);
		add(label);


		JLabel lblProduto = new JLabel("Produtos:");
		lblProduto.setBounds(10, 297, 67, 14);
		add(lblProduto);

		criaTabelaProduto();
		JScrollPane scrollP = new JScrollPane(tabelaProduto);
		scrollP.setBounds(10, 315, 480, 196);
		add(scrollP);
		
		JLabel lblServico = new JLabel("Serviços:");
		lblServico.setBounds(509, 297, 67, 14);
		add(lblServico);
		
		criaTabelaServico();
		JScrollPane scrollS = new JScrollPane(tabelaServico);
		scrollS.setBounds(510, 315, 480, 196);
		add(scrollS);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(422, 525, 46, 25);
		add(lblTotal);

		
		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(478, 525, 86, 25);
		add(textFieldTotal);
		textFieldTotal.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			//TODO implementar
		});
		btnSalvar.setBounds(373, 560, 89, 23);
		add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(541, 560, 89, 23);
		add(btnCancelar);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(20, 106, 58, 25);
		add(lblStatus);

		comboBoxStatus = new JComboBox<String>();
		comboBoxStatus.removeAllItems();
		comboBoxStatus.addItem(StatusENUM.ABERTO.getNome());
		comboBoxStatus.addItem(StatusENUM.APROVADO.getNome());
		comboBoxStatus.addItem(StatusENUM.EXECUCAO.getNome());
		comboBoxStatus.addItem(StatusENUM.FINALIZADO.getNome());
		comboBoxStatus.addItem(StatusENUM.CANCELADO.getNome());
		comboBoxStatus.addItem("");
		comboBoxStatus.setSelectedItem("");
		comboBoxStatus.setBounds(85, 106, 225, 25);
		add(comboBoxStatus);

		//if status desativado, bloquear combobox e todos os outros campos...
		
		JLabel lblNDaOs = new JLabel("N\u00BA da OS:");
		lblNDaOs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNDaOs.setBounds(20, 70, 60, 25);
		add(lblNDaOs);

		textFieldOrdemServico = new JTextField();
		textFieldOrdemServico.setBounds(85, 70, 126, 25);
		add(textFieldOrdemServico);
		textFieldOrdemServico.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(a -> {
			buscaOrdemServico();
			preencheDados();
		});
		btnBuscar.setBounds(221, 70, 90, 25);
		add(btnBuscar);
	}
	
	private void preencheDados() {
	
		//ORDEM DE SERVIÇO
		textFieldData.setText(ordemServico.getData());
		textFieldTotal.setText(ordemServico.getTotal());
		
//		//CLIENTE
		if ( !clienteAtual.getCnpj().equals("") ) {
			textFieldCpfCnpj.setText(clienteAtual.getCnpj());
		} else {
			textFieldCpfCnpj.setText(clienteAtual.getCpf());
		}
		textFieldNome.setText(clienteAtual.getNome());
		textFieldTelefone.setText(clienteAtual.getTelefone());
		
		//VEICULO
		textFieldPlaca.setText(veiculoAtual.getPlaca());
		textFieldModelo.setText(veiculoAtual.getModelo());
		textFieldAno.setText(veiculoAtual.getAno());

		if(ordemServico.getStatus() == StatusENUM.EXPIRADO.getValor()){
			comboBoxStatus.setSelectedItem(StatusENUM.ABERTO.getValor());
			
			JLabel lblExpirado = new JLabel("Expirado!");
			lblExpirado.setForeground(Color.RED);
			lblExpirado.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblExpirado.setBounds(325, 106, 106, 25);
			add(lblExpirado);

			//TODO ------>>>>> bloquear tudo e todos !!!! <<<-------
		
		} else {
			comboBoxStatus.setSelectedItem(ordemServico.getStatus());
		}

	}

	private void buscaOrdemServico() {
		try {
			ordemServico = new OrdemServicoController().buscaOrdemServico(Integer.parseInt(textFieldOrdemServico.getText()));
			ArrayList<OsProdutos> osProdutos = new OrdemServicoController().buscaProdutosOrdemServico(ordemServico.getIdOrdemServico());
			populaTabelaProduto(osProdutos);
			ArrayList<OsServicos> osServicos = new OrdemServicoController().buscaServicosOrdemServico(ordemServico.getIdOrdemServico());
			populaTabelaServico(osServicos);
			clienteAtual = new ClienteController().buscaDadosclienteId(ordemServico.getIdCliente());
			veiculoAtual = new VeiculoController().buscaDadosVeiculoId(ordemServico.getIdVeiculo());
		} catch (Exception e) {
			//pensar em mensagem default
			e.printStackTrace();
		}
	}



	public void preencheDadosCliente(Cliente cliente) {
		System.out.println("Cliente : " + cliente.getIdCliente());
		if (cliente != null) {
			this.clienteAtual = cliente;
			if (cliente.getCpf().equals(""))
				textFieldCpfCnpj.setText(cliente.getCnpj());
			else if (cliente.getCnpj().equals(""))
				textFieldCpfCnpj.setText(cliente.getCpf());
			textFieldNome.setText(cliente.getNome());
			textFieldTelefone.setText(cliente.getTelefone());
		}
	}
	
	public void preencheDadosVeiculo(Veiculo veiculo) {
		System.out.println("Veiculo : " + veiculo.getIdVeiculo());
		if (veiculo != null) {
			this.veiculoAtual = veiculo;
			textFieldPlaca.setText(veiculo.getPlaca());
			textFieldModelo.setText(veiculo.getModelo());
			textFieldAno.setText(veiculo.getAno());
		}
	}

	public void preencheValorTotalOrdemServico() {
		//Metodo Calcula Total (criar)
        valorDoubleTotal = 0.0;
		for(int i=0; i < tabelaModeloProduto.getRowCount(); i++){
			if (((Boolean) tabelaModeloProduto.getValueAt(i, 0)).booleanValue()){
				String parcialStrProd = tabelaModeloProduto.getValueAt(i, 4).toString();
				parcialStrProd = parcialStrProd.replace(",", ".");
				parcialDoubleProduto = Double.parseDouble(parcialStrProd);
				System.out.println("aqui no produto");
				System.out.println("parcial: " + parcialDoubleProduto);
				valorDoubleTotal += parcialDoubleProduto;
			}
		}

		for(int i=0; i < tabelaModeloServico.getRowCount(); i++){
			if (((Boolean) tabelaModeloServico.getValueAt(i, 0)).booleanValue()){
				String parcialStrServ = tabelaModeloServico.getValueAt(i, 5).toString();
				parcialStrServ = parcialStrServ.replace(",", ".");
				parcialDoubleServico = Double.parseDouble(parcialStrServ);
				System.out.println("aqui no servico");
				System.out.println("parcial: " + parcialDoubleServico);
				valorDoubleTotal += parcialDoubleServico;	
			}
		}
		String valorStrTotal = valorDoubleTotal.toString();
		valorStrTotal = valorStrTotal.replace(".", ",");
		textFieldTotal.setText(valorStrTotal);
		//Acaba Metodo Calcula Total
	}

	private void populaTabelaProduto(ArrayList<OsProdutos> osProdutos) {
		
		//LIMPAR TABELA
		while (tabelaModeloProduto.getRowCount() > 0) {
			tabelaModeloProduto.removeRow(0);
		}
		
		
		//POPULAR TABELA
		produtos = new ProdutoController().buscaProdutos();
		
		int contador = 0;
		for (Produto produto : produtos) {
			tabelaModeloProduto.addRow(new Object[]{false, produto.getNome(), produto.getValor(), "0", "0", produto.getIdProduto()});
			for (OsProdutos osProduto : osProdutos) {
				if(produto.getIdProduto() == osProduto.getIdProduto()){
					tabelaModeloProduto.setValueAt(true, contador, 0);
					tabelaModeloProduto.setValueAt(osProduto.getQuantidade(), contador, 3);
				}
			}
			contador++;
		}
		
	}

	private void populaTabelaServico(ArrayList<OsServicos> osServicos) {
		
//		//LIMPAR TABELA
		while (tabelaModeloServico.getRowCount() > 0) {
			tabelaModeloServico.removeRow(0);
		}

		
		//POPULAR TABELA
		servicos = new ServicoController().buscaServicos();
		
		int contador = 0;
		for (Servico servico : servicos) {
			int porHora = 0;
			if(servico.getPorHora()){
				porHora = 1;
			}
			if (!servico.getEstaAtivo()) {
				for (OsServicos osServico : osServicos) {
					if(servico.getIdServico() == osServico.getIdServico()) {
						tabelaModeloServico.addRow(new Object[]{true, servico.getNome(), servico.getValor(), osServico.getQtdPorHora(), osServico.getQuantidade(), "0", servico.getIdServico()});
					}
				}
			} else {
				tabelaModeloServico.addRow(new Object[]{false, servico.getNome(), servico.getValor(), porHora, "0", "0", servico.getIdServico()});
				for (OsServicos osServico : osServicos) {
					if(servico.getIdServico() == osServico.getIdServico()) {
						tabelaModeloServico.setValueAt(true, contador, 0);
						tabelaModeloServico.setValueAt(osServico.getQtdPorHora(), contador, 3);
						tabelaModeloServico.setValueAt(osServico.getQuantidade(), contador, 4);
					}
				}
			}
			contador++;
		}
	}
	
	private void criaTabelaProduto() {
		tabelaProduto = new JTable(tabelaModeloProduto){

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
                        return Integer.class;
                    case 4:
                        return String.class;
                    case 5:
                        return Integer.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tabelaModeloProduto.addColumn("Selecione");
        tabelaModeloProduto.addColumn("Nome");
        tabelaModeloProduto.addColumn("Valor (R$)");
        tabelaModeloProduto.addColumn("Quantidade");
        tabelaModeloProduto.addColumn("Total (R$)");
        tabelaModeloProduto.addColumn("idProduto");
        tabelaModeloProduto.setNumRows(0);
		tabelaProduto.getTableHeader().setReorderingAllowed(false);
		tabelaProduto.getColumnModel().getColumn(0).setPreferredWidth(70);
		tabelaProduto.getColumnModel().getColumn(1).setPreferredWidth(195); 
		tabelaProduto.getColumnModel().getColumn(2).setPreferredWidth(70);
		tabelaProduto.getColumnModel().getColumn(3).setPreferredWidth(75);
		tabelaProduto.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelaProduto.getColumnModel().getColumn(5).setMinWidth(0);
        tabelaProduto.getColumnModel().getColumn(5).setMaxWidth(0);
	}
	
	
	private void criaTabelaServico() {
		tabelaServico = new JTable(tabelaModeloServico){

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
                        return Integer.class;
                    case 4:
                        return Integer.class;
                    case 5:
                        return String.class;
                    case 6:
                        return Integer.class;                        
                    default:
                        return Boolean.class;
                }
            }
        };
        tabelaModeloServico.addColumn("Selecione");
        tabelaModeloServico.addColumn("Nome");
        tabelaModeloServico.addColumn("Valor (R$)");
        tabelaModeloServico.addColumn("Por Hora");
        tabelaModeloServico.addColumn("Quantidade");
        tabelaModeloServico.addColumn("Total (R$)");
        tabelaModeloServico.addColumn("idServico");
        tabelaModeloServico.setNumRows(0);
		tabelaServico.getTableHeader().setReorderingAllowed(false);
		tabelaServico.getColumnModel().getColumn(0).setPreferredWidth(70);
		tabelaServico.getColumnModel().getColumn(1).setPreferredWidth(135); 
		tabelaServico.getColumnModel().getColumn(2).setPreferredWidth(70);
		tabelaServico.getColumnModel().getColumn(3).setPreferredWidth(60);
		tabelaServico.getColumnModel().getColumn(4).setPreferredWidth(75);
		tabelaServico.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabelaServico.getColumnModel().getColumn(6).setMinWidth(0);
		tabelaServico.getColumnModel().getColumn(6).setMaxWidth(0);
	}
}
