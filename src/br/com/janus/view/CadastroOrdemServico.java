package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import br.com.janus.controller.ClienteController;
import br.com.janus.controller.ProdutoController;
import br.com.janus.controller.ServicoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.Produto;
import br.com.janus.model.Servico;
import br.com.janus.controller.VeiculoController;
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
	private Cliente clienteAtual;
	private Veiculo veiculoAtual;
	private JRadioButton rdbtnCpf;
	private JRadioButton rdbtnCnpj;
	
	private DefaultTableModel tabelaModeloProduto = new DefaultTableModel();
	private JTable tabelaProduto;
	private DefaultTableModel tabelaModeloServico = new DefaultTableModel();
	private JTable tabelaServico;
	
	private ArrayList<Produto> produtos;
	private ArrayList<Servico> servicos;

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
		textFieldData.setText(java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
		
		JLabel lblRegistroOrdemServico = new JLabel("Registro de Ordem de Servi�o");
		lblRegistroOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroOrdemServico.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblRegistroOrdemServico.setBounds(10, 11, 980, 48);
		add(lblRegistroOrdemServico);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(46, 101, 46, 14);
		add(lblCliente);

		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setBounds(329, 149, 90, 25);
		btnBuscarCliente.addActionListener(a -> {
			String cpf = this.textFieldCpf.getText();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace(" ", "");
			
			String cnpj = this.textFieldCnpj.getText();
			cnpj = cnpj.replace(".", "");
			cnpj = cnpj.replace("/", "");
			cnpj = cnpj.replace("-", "");
			cnpj = cnpj.replace(" ", "");
			try{
				Long.parseLong(cpf);
			}catch (Exception e) {
				cpf = "";
			}
			if (!cpf.isEmpty()) {
						try {
							clienteAtual = new ClienteController().buscaDadosClienteCpf(cpf);
							if (clienteAtual != null) {
								this.preencheDadosCliente(clienteAtual);
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
						clienteAtual = new ClienteController().buscaDadosClienteCnpj(cnpj);
						if (clienteAtual != null) {
							this.preencheDadosCliente(clienteAtual);
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

		JLabel lblVeculo = new JLabel("Ve\u00EDculo:");
		lblVeculo.setBounds(532, 101, 46, 14);
		add(lblVeculo);

		JLabel label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(532, 118, 415, 150);
		add(label);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			//TODO implementar
		});
		btnSalvar.setBounds(373, 560, 89, 23);
		add(btnSalvar);

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

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(422, 525, 46, 25);
		add(lblTotal);

		JLabel lblServico = new JLabel("Serviços:");
		lblServico.setBounds(509, 297, 67, 14);
		add(lblServico);

		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(478, 525, 86, 25);
		add(textFieldTotal);
		textFieldTotal.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(541, 560, 89, 23);
		add(btnCancelar);

		JButton btnBuscarVeiculo = new JButton("Buscar");
		btnBuscarVeiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscarVeiculo.setBounds(774, 150, 88, 25);
		btnBuscarVeiculo.addActionListener(a -> {
			try {
				veiculoAtual = new VeiculoController().buscaDadosVeiculoPlaca(this.textFieldPlaca.getText());
				if (veiculoAtual != null) {
					this.preencheDadosVeiculo(veiculoAtual);
				} else {
					textFieldModelo.setText("");
					textFieldModelo.repaint();
					textFieldAno.setText("");
					textFieldAno.repaint();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		add(btnBuscarVeiculo);
		
		textFieldCnpj = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
		textFieldCnpj.setBounds(140, 165, 179, 25);
		add(textFieldCnpj);
		textFieldCnpj.setColumns(10);

		textFieldCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		textFieldCpf.setColumns(10);
		textFieldCpf.setBounds(139, 131, 180, 25);
		add(textFieldCpf);
		
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
		});
		add(rdbtnCnpj);
		
	    ButtonGroup grupoRadios = new ButtonGroup();
	    grupoRadios.add(rdbtnCpf);
	    grupoRadios.add(rdbtnCnpj);
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
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tabelaModeloServico.addColumn("Selecione");
        tabelaModeloServico.addColumn("Nome");
        tabelaModeloServico.addColumn("Valor/h");
        tabelaModeloServico.addColumn("Quantidade/h");
        tabelaModeloServico.addColumn("Valor total");
        
//        tabelaServico.addMouseListener(new MouseAdapter() {
//        	 public void mouseClicked(MouseEvent e) {
//        		      int column = tabelaServico.getSelectedColumn();
//        		      System.out.println("oi");
//        		      System.out.println(column);
//        	 }
//		});
        tabelaServico.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            	System.out.println("focusGained");
            	if(tabelaServico.getSelectedColumn() == 3){
            		Float total = Float.parseFloat(tabelaModeloServico.getValueAt(tabelaServico.getSelectedRow(), 4).toString());
            		total = Float.parseFloat(tabelaModeloServico.getValueAt(tabelaServico.getSelectedRow(), 2).toString()) * Float.parseFloat(tabelaModeloServico.getValueAt(tabelaServico.getSelectedRow(), 3).toString());
            	}
            }

            // this function successfully provides cell editing stop
            // on cell losts focus (but another cell doesn't gain focus)
            public void focusLost(FocusEvent e) {
            	System.out.println("focusLosti");
//            	if(tabelaServico.getSelectedColumn() == 3){
//            		Float total = Float.parseFloat(tabelaModeloServico.getValueAt(tabelaServico.getSelectedRow(), 4).toString());
//            		total = Float.parseFloat(tabelaModeloServico.getValueAt(tabelaServico.getSelectedRow(), 2).toString()) * Float.parseFloat(tabelaModeloServico.getValueAt(tabelaServico.getSelectedRow(), 3).toString());
//            	}
//                
            }
        });

        tabelaModeloServico.setNumRows(0);
		
        servicos = new ServicoController().buscaServicos();
		for (Servico servico : servicos) {
			tabelaModeloServico.addRow(new Object[]{false, servico.getNome(), servico.getValor(), "0", ""});
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
                        return String.class;
                    case 4:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        tabelaModeloProduto.addColumn("Selecione");
        tabelaModeloProduto.addColumn("Nome");
        tabelaModeloProduto.addColumn("Valor");
        tabelaModeloProduto.addColumn("Quantidade");
        tabelaModeloProduto.addColumn("Valor total");
        tabelaModeloProduto.setNumRows(0);
		
        produtos = new ProdutoController().buscaProdutos();
		for (Produto produto : produtos) {
			tabelaModeloProduto.addRow(new Object[]{false, produto.getNome(), produto.getValor(), "", ""});
		}
		tabelaProduto.getTableHeader().setReorderingAllowed(false);
		tabelaProduto.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabelaProduto.getColumnModel().getColumn(1).setPreferredWidth(165); 
//		tabelaProduto.getColumnModel().getColumn(1).set
		tabelaProduto.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabelaProduto.getColumnModel().getColumn(3).setPreferredWidth(140);
		tabelaProduto.getColumnModel().getColumn(4).setPreferredWidth(75);
	}
	
	public void preencheDadosCliente(Cliente cliente) {
		System.out.println("Cliente : " + cliente.getIdCliente());
		if (cliente != null) {
			this.clienteAtual = cliente;
			textFieldCpf.setText(cliente.getCpf());
			textFieldCnpj.setText(cliente.getCnpj());
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
	
}
