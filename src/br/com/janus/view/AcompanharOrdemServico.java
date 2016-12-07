package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

public class AcompanharOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldOrdemServico;
	private JTextField textFieldDataCriacao;
	private JTextField textFieldCpfCnpj;
	private JTextField textFieldNome;
	private JTextField textFieldTelefone;
	private JTextField textFieldPlaca;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JTextField textFieldTotal;
	private JTextField textFieldStatus;
	private JButton btnSalvar;
	
	private JTable tabelaProduto;
	private JTable tabelaServico;

	private Float parcialFloatServico = 0.00f;
	private Float parcialFloatProduto = 0.00f;
	private Float valorFloatTotal = 0.00f;
	
	private OrdemServico ordemServico;
	private ClienteFisico clienteFisicoAtual;
	private ClienteJuridico clienteJuridicoAtual;
	private Veiculo veiculoAtual;
	private ArrayList<Produto> produtos;
	private ArrayList<Servico> servicos;
	
	public AcompanharOrdemServico() throws ParseException {
		setLayout(null);

		JLabel lblRegistroOrdemServico = new JLabel("Acompanhar Ordem de Serviço");
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

		textFieldDataCriacao = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldDataCriacao.setEditable(false);
		textFieldDataCriacao.setBounds(644, 70, 100, 25);
		add(textFieldDataCriacao);
		textFieldDataCriacao.setColumns(10);

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

		textFieldStatus = new JTextField();
		textFieldStatus.setEditable(false);
		textFieldStatus.setBounds(85, 106, 126, 25);
		add(textFieldStatus);
		textFieldStatus.setColumns(10);
		
		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(478, 525, 86, 25);
		add(textFieldTotal);
		textFieldTotal.setColumns(10);
		
		btnSalvar = new JButton("");
		btnSalvar.addActionListener(a -> {
			String dataAtual = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
			salvarAlteracoes(dataAtual);
			try {
				GerenciadorDeInterface.setPanel(new AcompanharOrdemServico());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		btnSalvar.setBounds(373, 560, 89, 23);
		add(btnSalvar);
		btnSalvar.setEnabled(false);
		

		
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

		JLabel lblNDaOs = new JLabel("Número OS:");
		lblNDaOs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNDaOs.setBounds(20, 70, 60, 25);
		add(lblNDaOs);

		textFieldOrdemServico = new JTextField();
		textFieldOrdemServico.setBounds(85, 70, 126, 25);
		add(textFieldOrdemServico);
		textFieldOrdemServico.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(a -> {
			if( !textFieldOrdemServico.getText().equals("") ){
				int idOrdemServico = Integer.parseInt(textFieldOrdemServico.getText());
				buscaOrdemServico(idOrdemServico);
			}
		});
		btnBuscar.setBounds(221, 70, 90, 25);
		add(btnBuscar);
	}
	
	private boolean podeAprovarOS() {
		return textFieldStatus.getText().equals("Aberto");
	}
	
	public boolean podeAtualizarOS() {
		return ( textFieldStatus.getText().equals("Aprovado") || textFieldStatus.getText().equals("Execucao") );
	}
	
	private void aprovaOrdemServico(String dataAtual) {
		ArrayList<OsServicos> osServicos = constroiServicos();
		ArrayList<OsProdutos> osProdutos = constroiProdutos();
		boolean salvou = new OrdemServicoController().aprovaOrdemServico(ordemServico,osServicos,osProdutos, dataAtual);
		if(salvou){
			mostraMensagem("Ordem de servico aprovada com sucesso");
			preencherCampoStatus("Aprovado");
			permiteAtualizarOS();
		}else{
			mostraMensagem("Erro! Não foi possível realizar a operação");

		}
	}
	
	private void  mostraMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}
	
	private void atualizaOrdemServico() {
		ArrayList<OsServicos> osServicos = constroiServicos();
		ArrayList<OsProdutos> osProdutos = constroiProdutos();
		boolean atualizou = new OrdemServicoController().atualizaOrdemServico(ordemServico,osServicos,osProdutos);
		if(atualizou){
			mostraMensagem("Ordem de servico alterada com sucesso");
		}else{
			mostraMensagem("Erro! Não foi possível realizar a operação");
		}
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
	
	private DefaultTableModel tabelaModeloProduto = new DefaultTableModel() {
        boolean[] selecionado = new boolean[]{true, false, false, true, false};
        boolean[] naoSelecionado = new boolean[]{true, false, false, false, false};
                
        public boolean isCellEditable(int rowIndex, int columnIndex) {
        	if( podeAtualizarOS() ) {
	        	if (((Boolean) getValueAt(rowIndex, 0)).booleanValue()) {
	            	return selecionado[columnIndex];
	            }
	            else {
	            	setValueAt("0", rowIndex, 3);
	            	return naoSelecionado[columnIndex];
	            }
        	} else {
            	return false;
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
                	float valor = Float.parseFloat(valorStr);
                	Integer quantidade = Integer.parseInt(getValueAt(row, 3).toString());
                    float resultado = 0.00f;
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
    		String valorStrTotal = valorFloatTotal.toString();
    		valorStrTotal = valorStrTotal.replace(".", ",");
    		textFieldTotal.setText(valorStrTotal);

            
            tabelaModeloProduto.fireTableCellUpdated(row, 4);
        }
	};
	
	private DefaultTableModel tabelaModeloServico = new DefaultTableModel() {
        boolean[] selecionado = new boolean[]{true, false, false, false, true, false};
        boolean[] selecionadoPorHora = new boolean[]{true, false, false, true, true, false};
        boolean[] naoSelecionado = new boolean[]{true, false, false, false, false, false};
                
        public boolean isCellEditable(int rowIndex, int columnIndex) {
        	
        	if ( podeAtualizarOS() ) {
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
        	} else {
            	return false;
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
    		String valorStrTotal = valorFloatTotal.toString();
    		valorStrTotal = valorStrTotal.replace(".", ",");
    		textFieldTotal.setText(valorStrTotal);

           	fireTableCellUpdated(row, 5);
        }   
	};
	
	private void preencheDados() {
		if(ordemServico != null){
			String dataCriacao;
			String total;
			String cnpj;
			String cpf;
			String nome;
			String telefone;
			String placa;
			String modelo;
			String ano;
			boolean estaExpirado;

			dataCriacao = ordemServico.getDataCriacao();
			textFieldDataCriacao.setText(dataCriacao);

			total = ordemServico.getTotal();
			textFieldTotal.setText(total);

			if (clienteJuridicoAtual != null) {
				cnpj = clienteJuridicoAtual.getCnpj();
				textFieldCpfCnpj.setText(cnpj);
				nome = clienteJuridicoAtual.getNome();
				textFieldNome.setText(nome);
				telefone = clienteJuridicoAtual.getTelefone();
				textFieldTelefone.setText(telefone);
			} else {
				cpf = clienteFisicoAtual.getCpf();
				textFieldCpfCnpj.setText(cpf);
				nome = clienteFisicoAtual.getNome();
				textFieldNome.setText(nome);
				telefone = clienteFisicoAtual.getTelefone();
				textFieldTelefone.setText(telefone);
			}
			placa = veiculoAtual.getPlaca();
			textFieldPlaca.setText(placa);
			modelo = veiculoAtual.getModelo();
			textFieldModelo.setText(modelo);
			ano = veiculoAtual.getAno();
			textFieldAno.setText(ano);

			estaExpirado = ordemServico.getEstaExpirado();
			bloqueiaEdicaoOS();
			if(estaExpirado){
				textFieldStatus.setText("Expirado!");
				textFieldStatus.setForeground(Color.RED);
			}else {
				verificaStatusOrdemServico();

				textFieldStatus.setForeground(Color.BLACK);
			}

		}
	}

	private void verificaStatusOrdemServico() {
		boolean estaCancelado = ordemServico.getDataCancelado()!= null;
		boolean estaFinalizado = ordemServico.getDataFinalizado() != null;
		boolean estaExecutando = ordemServico.getDataExecucao() != null;
		boolean estaAprovado = ordemServico.getDataAprovado() != null;
			
		if (estaCancelado) {
			preencherCampoStatus("Cancelado");
		}else if (estaFinalizado) {
			preencherCampoStatus("Finalizado");
		}else if (estaExecutando) {
			preencherCampoStatus("Execução");
			permiteAtualizarOS();
		}else if(estaAprovado) {
			preencherCampoStatus("Aprovado");
			permiteAtualizarOS();
		}else {
			preencherCampoStatus("Aberto");
			permiteAprovarOS();
		}
	}
	
	private void preencherCampoStatus(String status) {
		textFieldStatus.setText(status);
	}

	private void bloqueiaEdicaoOS() {
		btnSalvar.setText("");
		btnSalvar.setEnabled(false);
		btnSalvar.setVisible(false);
	}


	private void permiteAtualizarOS() {
		btnSalvar.setText("Atualizar");
		btnSalvar.setEnabled(true);
		btnSalvar.setVisible(true);
	}

	private void permiteAprovarOS() {
		btnSalvar.setText("Aprovar OS");
		btnSalvar.setEnabled(true);
		btnSalvar.setVisible(true);
	}
	

	public void salvarAlteracoes(String dataAtual) {
		boolean podeAprovar = podeAprovarOS();
		if (podeAprovar) {
			aprovaOrdemServico(dataAtual);
		} else {
			boolean podeAtualizar = podeAtualizarOS();
			if (podeAtualizar) {
				atualizaOrdemServico();
			}
		}
	}

	private void buscaOrdemServico(Integer idOrdemServico) {
		try {
			ordemServico = new OrdemServicoController().buscaOrdemServico(idOrdemServico);
			if(ordemServico != null){
				int idOrdemDeServico = ordemServico.getIdOrdemDeServico();
				int idCliente = ordemServico.getIdCliente();
				int idVeiculo = ordemServico.getIdVeiculo();
				ArrayList<OsProdutos> osProdutos = new OrdemServicoController().buscaProdutosOrdemServico(idOrdemDeServico);
				ArrayList<OsServicos> osServicos = new OrdemServicoController().buscaServicosOrdemServico(idOrdemDeServico);
				populaTabelaProduto(osProdutos);
				populaTabelaServico(osServicos);
				boolean ehClienteFisico = new ClienteController().verificaClienteFisico(idCliente);
				if(ehClienteFisico){
					clienteFisicoAtual = new ClienteController().buscaDadosclienteFisicoId(idCliente);
				}else{
					clienteJuridicoAtual = new ClienteController().buscaDadosclienteJuridicoId(idCliente);
				}
				veiculoAtual = new VeiculoController().buscaDadosVeiculoId(idVeiculo);
				verificaStatusOrdemServico();
				preencheDados();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void preencheDadosVeiculo(Veiculo veiculo) {
		if (veiculo != null) {
			this.veiculoAtual = veiculo;
			textFieldPlaca.setText(veiculo.getPlaca());
			textFieldModelo.setText(veiculo.getModelo());
			textFieldAno.setText(veiculo.getAno());
		}
	}


	private void populaTabelaProduto(ArrayList<OsProdutos> osProdutos) {
		while (tabelaModeloProduto.getRowCount() > 0) {
			tabelaModeloProduto.removeRow(0);
		}
		
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
		while (tabelaModeloServico.getRowCount() > 0) {
			tabelaModeloServico.removeRow(0);
		}
		
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
