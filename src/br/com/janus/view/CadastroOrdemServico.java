package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.janus.controller.ClienteController;
import br.com.janus.controller.OrdemServicoController;
import br.com.janus.controller.ProdutoController;
import br.com.janus.controller.ServicoController;
import br.com.janus.controller.VeiculoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.ClienteFisico;
import br.com.janus.model.ClienteJuridico;
import br.com.janus.model.OrdemServico;
import br.com.janus.model.OsProdutos;
import br.com.janus.model.OsServicos;
import br.com.janus.model.Produto;
import br.com.janus.model.Servico;
import br.com.janus.model.Veiculo;

public class CadastroOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JFormattedTextField textFieldCpf;
	private JFormattedTextField textFieldCnpj;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JFormattedTextField textFieldPlaca;
	private JFormattedTextField textFieldData;
	private JFormattedTextField textFieldTelefone;
	private JTextField textFieldTotal;
	private JRadioButton rdbtnCpf;
	private JRadioButton rdbtnCnpj;
	private JButton btnSalvar;
	private JButton btnBuscarVeiculo;
	
	private JTable tabelaServico;
	private JTable tabelaProduto;
	
	private Float parcialFloatServico = 0.00f;
	private Float parcialFloatProduto = 0.00f;
	private Float valorFloatTotal = 0.00f;
	
	private ClienteFisico clienteFisicoAtual;
	private ClienteJuridico clienteJuridicoAtual;
	
	private Veiculo veiculoAtual;
	private ArrayList<Produto> produtos;
	private ArrayList<Servico> servicos;
	
	private DefaultTableModel tabelaModeloProduto = new DefaultTableModel() {
        boolean[] selecionado = new boolean[]{true, false, false, true, false};
        boolean[] naoSelecionado = new boolean[]{true, false, false, false, false};
                
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (((Boolean) getValueAt(rowIndex, 0)).booleanValue() ) {
            	if (Integer.parseInt(getValueAt(rowIndex, 3).toString()) < 1) {
            		setValueAt("1", rowIndex, 3);
            	} else {
            		setValueAt(getValueAt(rowIndex, 3).toString(), rowIndex, 3);
            	}
            	return selecionado[columnIndex];
            }
            else {
            	setValueAt("0", rowIndex, 3);
            	return naoSelecionado[columnIndex];
            }
        }
        
        @Override
        public Object getValueAt(int row, int column) {
            //TODO TERMINAR ESSA lógica dos cliques
        	if (column == 4) {
                	String valorStr = getValueAt(row, 2).toString();
                	valorStr = valorStr.replace(",", ".");
                	float valor = Float.parseFloat(valorStr);
                	Integer quantidade = Integer.parseInt(getValueAt(row, 3).toString());
                    float resultado = 0.00f;
                    if (quantidade < 0) {
                    	quantidade = 0;
                    }
                    resultado = valor * quantidade;
                    String resultadoStr = String.format("%.2f", resultado);
                    resultadoStr = resultadoStr.replace(".", ",");
                    return resultadoStr;
            }
            return super.getValueAt(row, column);
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
        	if ( ((Boolean) getValueAt(row, 0)).booleanValue() ) {
        		System.out.println(((Boolean) getValueAt(row, 0)).booleanValue());
        		int testeQuantidade = Integer.parseInt(getValueAt(row, 3).toString());
        		if (testeQuantidade < 0) {
        			setValueAt("1", row, 3);
        		}
        	}
    	
        	//Forçar a escrita ser sempre 1 caso insira 0 ou negativos.
            if (column == 3 && ((Boolean) getValueAt(row, 0)).booleanValue()) {
            	int testeQuantidade = Integer.parseInt(aValue.toString());
            	if (testeQuantidade < 1){
            		setValueAt("1", row, column);
            	} 
            }
            if (column == 3 && !((Boolean) getValueAt(row, 0)).booleanValue()) {
            	int testeQuantidade = Integer.parseInt(aValue.toString());
            	if (testeQuantidade > 0){
            		setValueAt("0", row, column);
            	} 	
            } 

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
                	if (Integer.parseInt(getValueAt(rowIndex, 4).toString()) < 1) {
                		setValueAt("1", rowIndex, 4);
                	} else {
                		System.out.println((String)  getValueAt(rowIndex, 4).toString());
                		setValueAt((String) getValueAt(rowIndex, 4).toString(), rowIndex, 4);
                	}
                	return selecionadoPorHora[columnIndex];
            	} else {
                	if (Integer.parseInt(getValueAt(rowIndex, 4).toString()) < 1) {
                		setValueAt("1", rowIndex, 4);
                	} else {
                		setValueAt(getValueAt(rowIndex, 4).toString(), rowIndex, 4);
                	}
            		return selecionado[columnIndex];
            	}
            } else {
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
                	float valor = Float.parseFloat(valorStr);
                	Integer quantidade = Integer.parseInt(getValueAt(row, 4).toString());
                	Integer valorPorHora = Integer.parseInt(getValueAt(row, 3).toString());
                    float resultado = 0.00f;
                    if (quantidade < 0) {
                    	quantidade = 0;
                    }
                    if (valorPorHora > 0) {
                    	resultado = (valor * quantidade) * valorPorHora;	
                    } else {
                    	resultado = valor * quantidade;
                    }
                    String resultadoStr = String.format("%.2f", resultado);
                    resultadoStr = resultadoStr.replace(".", ",");
                    return resultadoStr;
                }
            }
            
            return super.getValueAt(row, column);
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);
            //TODO TERMINAR ESSA lógica dos cliques
        	if ( ((Boolean) getValueAt(row, 0)).booleanValue() ) {
        		System.out.println(((Boolean) getValueAt(row, 0)).booleanValue());
        		int testeQuantidade = Integer.parseInt(getValueAt(row, 3).toString());
        		if (testeQuantidade < 0) {
        			setValueAt("1", row, 3);
        		}
        	}
    	
        	//Forçar a escrita ser sempre 1 caso insira 0 ou negativos.
            if (column == 4 && ((Boolean) getValueAt(row, 0)).booleanValue()) {
            	int testeQuantidade = Integer.parseInt(aValue.toString());
            	if (testeQuantidade < 1){
            		setValueAt("1", row, column);
            	} 
            }
            if (column == 4 && !((Boolean) getValueAt(row, 0)).booleanValue()) {
            	int testeQuantidade = Integer.parseInt(aValue.toString());
            	if (testeQuantidade > 0){
            		setValueAt("0", row, column);
            	} 	
            }
            
            if (column == 3) {
            	int testePorHora = Integer.parseInt(aValue.toString());
            	if (testePorHora < 1){
            		setValueAt("1", row, column);
            	}
            }

//            if (column == 4) {
//	           	int testeQuantidade = Integer.parseInt(aValue.toString());
//	           	if (testeQuantidade < 1){
//	           		setValueAt("1", row, column);
//	           	}
//	        }
        	
            
            
            preencheValorTotalOrdemServico();
			
           	fireTableCellUpdated(row, 5);
        }   
	};

	public CadastroOrdemServico() throws ParseException {

		setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(70, 197, 60, 25);
		add(lblNome);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModelo.setBounds(550, 183, 60, 25);
		add(lblModelo);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAno.setBounds(550, 215, 60, 25);
		add(lblAno);

		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlaca.setBounds(550, 150, 60, 25);
		add(lblPlaca);

		textFieldNome = new JTextField();
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(141, 197, 300, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldModelo = new JTextField();
		textFieldModelo.setEditable(false);
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(617, 183, 244, 25);
		add(textFieldModelo);

		textFieldAno = new JTextField();
		textFieldAno.setEditable(false);
		textFieldAno.setColumns(10);
		textFieldAno.setBounds(617, 215, 126, 25);
		add(textFieldAno);

		textFieldPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
		textFieldPlaca.setColumns(10);
		textFieldPlaca.setBounds(617, 150, 130, 25);
		add(textFieldPlaca);

		JLabel lblData = new JLabel("Data:");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setBounds(46, 65, 45, 25);
		add(lblData);

		textFieldData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldData.setEditable(false);
		textFieldData.setBounds(101, 65, 85, 25);
		add(textFieldData);
		textFieldData.setColumns(10);
		textFieldData.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
		
		JLabel lblRegistroOrdemServico = new JLabel("Registro de Ordem de Serviço");
		lblRegistroOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroOrdemServico.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblRegistroOrdemServico.setBounds(10, 11, 980, 48);
		add(lblRegistroOrdemServico);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(46, 101, 46, 14);
		add(lblCliente);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			salvaOrdemServico();
		});
		btnSalvar.setBounds(373, 560, 89, 23);
		add(btnSalvar);
		btnSalvar.setEnabled(false);
		
		btnBuscarVeiculo = new JButton("Buscar");
		btnBuscarVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarVeiculo.setBounds(774, 150, 88, 25);
		btnBuscarVeiculo.addActionListener(a -> {
			buscarVeiculo();
		});
		add(btnBuscarVeiculo);
		btnBuscarVeiculo.setEnabled(false);
		
		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(329, 149, 90, 25);
		btnBuscarCliente.addActionListener(a -> {
			buscarCliente();
		});
		add(btnBuscarCliente);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setBounds(70, 232, 60, 25);
		add(lblTelefone);

		textFieldTelefone = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
		textFieldTelefone.setEditable(false);
		textFieldTelefone.setBounds(141, 232, 205, 25);
		add(textFieldTelefone);
		textFieldTelefone.setColumns(10);

		JLabel lblCaixaCliente = new JLabel("");
		lblCaixaCliente.setBounds(46, 118, 415, 150);
		lblCaixaCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(lblCaixaCliente);

		JLabel lblVeculo = new JLabel("Veículo:");
		lblVeculo.setBounds(532, 101, 46, 14);
		add(lblVeculo);

		JLabel label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(532, 118, 415, 150);
		add(label);

		JLabel lblProduto = new JLabel("Produtos:");
		lblProduto.setBounds(10, 297, 67, 14);
		add(lblProduto);

		populaTabelaProduto();
		JScrollPane scrollP = new JScrollPane(tabelaProduto);
		scrollP.setBounds(10, 315, 480, 196);
		add(scrollP);
		
		populaTabelaServicos();
		JScrollPane scrollS = new JScrollPane(tabelaServico);
		scrollS.setBounds(510, 315, 480, 196);
		add(scrollS);

		JLabel lblTotal = new JLabel("Total (R$):");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(410, 525, 60, 25);
		add(lblTotal);

		JLabel lblServico = new JLabel("Serviços:");
		lblServico.setBounds(509, 297, 67, 14);
		add(lblServico);

		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(478, 525, 86, 25);
		textFieldTotal.setColumns(10);
		textFieldTotal.setText("0,00");
		add(textFieldTotal);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(541, 560, 89, 23);
		add(btnCancelar);
		
		textFieldCnpj = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
		textFieldCnpj.setColumns(10);
		textFieldCnpj.setBounds(140, 165, 179, 25);
		add(textFieldCnpj);
		textFieldCnpj.setEditable(false);

		textFieldCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		textFieldCpf.setColumns(10);
		textFieldCpf.setBounds(139, 131, 180, 25);
		add(textFieldCpf);
		textFieldCpf.setEditable(false);
		
		rdbtnCpf = new JRadioButton("CPF:");
		rdbtnCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCpf.setBounds(65, 131, 68, 23);
		rdbtnCpf.addActionListener(a -> {
			textFieldCnpj.setText("");
			textFieldCnpj.setValue("");
			textFieldCnpj.repaint();
			textFieldCnpj.setEditable(false);
			textFieldCpf.setEditable(true);
			textFieldTelefone.setText("");
			textFieldTelefone.setValue("");
			textFieldTelefone.repaint();
			textFieldNome.setText("");
			textFieldNome.repaint();
			btnBuscarVeiculo.setEnabled(false);
		});
		add(rdbtnCpf);

		rdbtnCnpj = new JRadioButton("CNPJ:");
		rdbtnCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCnpj.setBounds(56, 165, 78, 23);
		rdbtnCnpj.addActionListener(a -> {
			textFieldCpf.setText("");
			textFieldCpf.setValue("");
			textFieldCpf.repaint();
			textFieldCnpj.setEditable(true);
			textFieldCpf.setEditable(false);
			textFieldTelefone.setText("");
			textFieldTelefone.setValue("");
			textFieldTelefone.repaint();
			textFieldNome.setText("");
			textFieldNome.repaint();
			btnBuscarVeiculo.setEnabled(false);
		});
		add(rdbtnCnpj);
		
	    ButtonGroup grupoRadios = new ButtonGroup();
	    grupoRadios.add(rdbtnCpf);
	    grupoRadios.add(rdbtnCnpj);
	}

	private void salvaOrdemServico() {
		OrdemServico ordemServico =constroiOrdemServico();
		ArrayList<OsServicos> osServicos = constroiServicos();
		ArrayList<OsProdutos> osProdutos = constroiProdutos();
		new OrdemServicoController().salva(ordemServico,osServicos,osProdutos);
		
	}

	private ArrayList<OsProdutos> constroiProdutos() {
		 ArrayList<OsProdutos> osProdutos = new ArrayList<OsProdutos>();
		for(int i=0; i < tabelaModeloProduto.getRowCount(); i++){
			if (((Boolean) tabelaModeloProduto.getValueAt(i, 0)).booleanValue()){
				OsProdutos osProduto = new OsProdutos();
				osProduto.setIdProduto(Integer.parseInt(tabelaModeloProduto.getValueAt(i, 5).toString()));
				osProduto.setQuantidade(Integer.parseInt(tabelaModeloProduto.getValueAt(i, 3).toString()));
				osProdutos.add(osProduto);
			}
		}
		return osProdutos;
	}

	private ArrayList<OsServicos> constroiServicos() {
		ArrayList<OsServicos> osServicos = new ArrayList<OsServicos>();
		for(int i=0; i < tabelaModeloServico.getRowCount(); i++){
			if (((Boolean) tabelaModeloServico.getValueAt(i, 0)).booleanValue()){
				OsServicos osServico = new OsServicos();
				osServico.setQtdPorHora(Integer.parseInt(tabelaModeloServico.getValueAt(i, 3).toString()));
				osServico.setQuantidade(Integer.parseInt(tabelaModeloServico.getValueAt(i, 4).toString()));
				osServico.setIdServico(Integer.parseInt(tabelaModeloServico.getValueAt(i, 6).toString()));
				osServicos.add(osServico);
			}
		}
		return osServicos;
	}

	private OrdemServico constroiOrdemServico() {
		OrdemServico ordemServico = new OrdemServico();
		ordemServico.setDataCriacao(this.textFieldData.getText());
		ordemServico.setTotal(this.textFieldTotal.getText());
		if(clienteFisicoAtual.getIdCliente() != null){
			ordemServico.setIdCliente(clienteFisicoAtual.getIdCliente());
		}else{
			ordemServico.setIdCliente(clienteJuridicoAtual.getIdCliente());
		}
		ordemServico.setIdVeiculo(veiculoAtual.getIdVeiculo());
		return ordemServico;
	}

	private void populaTabelaServicos() {
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

        servicos = new ServicoController().buscaServicos();
		for (Servico servico : servicos) {
			int porHora = 0;
			if(servico.getPorHora()){
				porHora = 1;
			}
			if (servico.getEstaAtivo())
				tabelaModeloServico.addRow(new Object[]{false, servico.getNome(), servico.getValor(), porHora, "0", "0,00", servico.getIdServico()});
			
		}
	}

	private void populaTabelaProduto() {
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
        
        produtos = new ProdutoController().buscaProdutos();
		for (Produto produto : produtos) {
			tabelaModeloProduto.addRow(new Object[]{false, produto.getNome(), produto.getValor(), "0", "0,00", produto.getIdProduto()});
		}
	}
	
	public void preencheDadosClienteJuridico(ClienteJuridico clienteJuridico) {
		if (clienteJuridico != null) {
			this.clienteJuridicoAtual = clienteJuridico;
			textFieldCnpj.setText(clienteJuridico.getCnpj());
			textFieldNome.setText(clienteJuridico.getNome());
			textFieldTelefone.setText(clienteJuridico.getTelefone());
		}
	}
	
	private void preencheDadosClienteFisico(ClienteFisico clienteFisico) {
		this.clienteFisicoAtual = clienteFisico;
		textFieldCpf.setText(clienteFisico.getCpf());
		textFieldNome.setText(clienteFisico.getNome());
		textFieldTelefone.setText(clienteFisico.getTelefone());
	}
	
	public void preencheDadosVeiculo(Veiculo veiculo) {
		if (veiculo != null) {
			String placa;
			String modelo;
			String ano;
			
			this.veiculoAtual = veiculo;
						
			placa = veiculo.getPlaca();
			textFieldPlaca.setText(placa);

			modelo = veiculo.getModelo();
			textFieldModelo.setText(modelo);
			
			ano = veiculo.getAno();
			textFieldAno.setText(ano);
		}
	}
	
	public void preencheValorTotalOrdemServico() {
        valorFloatTotal = 0.00f;
		for(int i=0; i < tabelaModeloProduto.getRowCount(); i++){
			if (((Boolean) tabelaModeloProduto.getValueAt(i, 0)).booleanValue()){
				String parcialStrProd = tabelaModeloProduto.getValueAt(i, 4).toString();
				parcialStrProd = parcialStrProd.replace(",", ".");
				parcialFloatProduto = Float.parseFloat(parcialStrProd);
				valorFloatTotal += parcialFloatProduto;
			}
		}

		for(int i=0; i < tabelaModeloServico.getRowCount(); i++){
			if (((Boolean) tabelaModeloServico.getValueAt(i, 0)).booleanValue()){
				String parcialStrServ = tabelaModeloServico.getValueAt(i, 5).toString();
				parcialStrServ = parcialStrServ.replace(",", ".");
				parcialFloatServico = Float.parseFloat(parcialStrServ);
				valorFloatTotal += parcialFloatServico;	
			}
		}
		String valorStrTotal = String.format("%.2f", valorFloatTotal);
		valorStrTotal = valorStrTotal.replace(".", ",");
		textFieldTotal.setText(valorStrTotal);
	}
	
	private String limpezaCaracteresNaoNumeraisCpfCnpj(String cpfCnpj) {
		cpfCnpj	 = cpfCnpj.replace(".", "");
		cpfCnpj	 = cpfCnpj.replace("/", "");
		cpfCnpj	 = cpfCnpj.replace("-", "");
		cpfCnpj	 = cpfCnpj.replace(" ", "");
		return cpfCnpj;
	}
	
	private void buscarCliente() {
		String cpf = this.textFieldCpf.getText();
		String cnpj = this.textFieldCnpj.getText();
		cpf = limpezaCaracteresNaoNumeraisCpfCnpj( cpf );
		cnpj = limpezaCaracteresNaoNumeraisCpfCnpj( cnpj );
		
		try{
			Long.parseLong(cpf);
		}catch (Exception e) {
			cpf = "";
		}
		if (!cpf.isEmpty()) {
			try {
				clienteFisicoAtual = new ClienteController().buscaDadosClienteCpf(cpf);
				if (clienteFisicoAtual != null) {
					preencheDadosClienteFisico(clienteFisicoAtual);
					btnBuscarVeiculo.setEnabled(true);
				} else {
					textFieldTelefone.setText("");
					textFieldTelefone.setValue("");
					textFieldTelefone.repaint();
					textFieldNome.setText("");
					textFieldNome.repaint();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (!cnpj.isEmpty()) {
			try {
				clienteJuridicoAtual = new ClienteController().buscaDadosClienteCnpj(cnpj);
				if (clienteJuridicoAtual != null) {
					preencheDadosClienteJuridico(clienteJuridicoAtual);
					btnBuscarVeiculo.setEnabled(true);
				} else {
					textFieldTelefone.setText("");
					textFieldTelefone.setValue("");
					textFieldTelefone.repaint();
					textFieldNome.setText("");
					textFieldNome.repaint();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void buscarVeiculo() {
		try {
			veiculoAtual = new VeiculoController().buscaDadosVeiculoPlaca(this.textFieldPlaca.getText());
			if (veiculoAtual != null) {
				this.preencheDadosVeiculo(veiculoAtual);
				btnSalvar.setEnabled(true);
			} else {
				textFieldModelo.setText("");
				textFieldModelo.repaint();
				textFieldAno.setText("");
				textFieldAno.repaint();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}