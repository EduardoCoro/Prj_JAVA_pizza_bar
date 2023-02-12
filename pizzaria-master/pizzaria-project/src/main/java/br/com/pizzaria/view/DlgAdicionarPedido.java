package br.com.pizzaria.view;

//import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import br.com.pizzaria.model.business.PizzariaBusiness;
import br.com.pizzaria.model.entidades.cliente.Cliente;
import br.com.pizzaria.model.entidades.pedido.Pedido;
import br.com.pizzaria.model.entidades.pedido.PedidoBebida;
import br.com.pizzaria.model.entidades.pedido.PedidoPizza;
import br.com.pizzaria.view.tabela.BebidaColumnModel;
import br.com.pizzaria.view.tabela.BebidaTableModel;
import br.com.pizzaria.view.tabela.PizzaColumnModel;
import br.com.pizzaria.view.tabela.PizzaTableModel;

public class DlgAdicionarPedido extends JDialog {
	private Pedido pedido = new Pedido();
	private Cliente cliente;
	
	private PizzariaBusiness business;

	private final JButton botaoOk = new JButton("OK");
	private final JButton botaoCancelar = new JButton("Cancelar");

	private final JButton botaoAdicionarPizza = new JButton("Adic. pizza");
	private final JButton botaoRemoverPizza = new JButton("Remov. pizza");
	private final JButton botaoAdicionarBebida = new JButton("Adic. bebida");
	private final JButton botaoRemoverBebida = new JButton("Remov. bebida");
	private final JButton botaoDetalhesPizza = new JButton("Detalhes pizza");
	
	private final PizzaColumnModel pizzaColumnModel = new PizzaColumnModel();
	private final PizzaTableModel pizzaTableModel = new PizzaTableModel(pedido, pizzaColumnModel);
	private final JTable pizzaGrid = new JTable(pizzaTableModel, pizzaColumnModel, null);
	private final JScrollPane pizzaPane = new JScrollPane(pizzaGrid);

	private final BebidaColumnModel bebidaColumnModel = new BebidaColumnModel();
	private final BebidaTableModel bebidaTableModel = new BebidaTableModel(pedido, bebidaColumnModel);
	private final JTable bebidaGrid = new JTable(bebidaTableModel, bebidaColumnModel, null);
	private final JScrollPane bebidaPane = new JScrollPane(bebidaGrid);

	private JComboBox<Cliente> comboCliente;
	private JTextField textData;
	private JTextField textValorTotal;
	private boolean pressionouOk = false;

	public DlgAdicionarPedido(PizzariaBusiness business) {
		this.business = business;

		setSize(710, 400);
		setLayout(null);
		setModal(true);

		setTitle("Adicionar pedido");

		adicionaColunasGrid();
		configuraBotoes();

		pizzaGrid.getTableHeader().setReorderingAllowed(false);
		bebidaGrid.getTableHeader().setReorderingAllowed(false);

		comboCliente = criaComboClientes(10, 30);
		textData = criaTextField("Data", 40, 100, 10);
		
		textValorTotal = criaTextField("Valor total", 40, 300, 10, 220);
		textValorTotal.setEditable(false);
		textValorTotal.setText("0");

		pizzaPane.setLocation(0, 70);
		pizzaPane.setSize(550, 115);

		bebidaPane.setLocation(0, 190);
		bebidaPane.setSize(550, 115);

		add(comboCliente);
		add(textData);
		add(textValorTotal);

		add(pizzaPane);
		add(bebidaPane);

		add(botaoOk);
		add(botaoCancelar);

		add(botaoAdicionarPizza);
		add(botaoRemoverPizza);
		add(botaoAdicionarBebida);
		add(botaoRemoverBebida);
		add(botaoDetalhesPizza);
	}

	private void configuraBotoes() {
		botaoOk.addActionListener(new ActionOk());
		botaoCancelar.addActionListener(new ActionCancelar());

		botaoOk.setSize(100, 30);
		botaoOk.setLocation(0, 320);

		botaoCancelar.setSize(100, 30);
		botaoCancelar.setLocation(100, 320);

		botaoAdicionarPizza.setSize(120, 30);
		botaoAdicionarPizza.setLocation(560, 70);
		botaoAdicionarPizza.addActionListener(new ActionAdicionarPizza());
		
		botaoRemoverPizza.setSize(120, 30);
		botaoRemoverPizza.setLocation(560, 100);
		botaoRemoverPizza.addActionListener(new ActionRemoverPizza());
		
		botaoDetalhesPizza.setSize(120, 30);
		botaoDetalhesPizza.setLocation(560, 130);
		botaoDetalhesPizza.addActionListener(new ActionDetalhes());
		
		botaoAdicionarBebida.setSize(120, 30);
		botaoAdicionarBebida.setLocation(560, 190);
		botaoAdicionarBebida.addActionListener(new ActionAdicionarBebida());
		
		botaoRemoverBebida.setSize(120, 30);
		botaoRemoverBebida.setLocation(560, 220);
		botaoRemoverBebida.addActionListener(new ActionRemoverBebida());
	}

	private void adicionaColunasGrid() {
		pizzaColumnModel.addColumn(criaColuna(pizzaColumnModel, "Pizza", 140));
		pizzaColumnModel.addColumn(criaColuna(pizzaColumnModel, "Borda", 140));
		pizzaColumnModel.addColumn(criaColuna(pizzaColumnModel, "Massa", 90));
		pizzaColumnModel.addColumn(criaColuna(pizzaColumnModel, "Tamanho", 70));
		pizzaColumnModel.addColumn(criaColuna(pizzaColumnModel, "Qtd. adicionais", 100));

		bebidaColumnModel.addColumn(criaColuna(bebidaColumnModel, "Bebida", 280));
		bebidaColumnModel.addColumn(criaColuna(bebidaColumnModel, "Quantidade", 280));
	}

	private TableColumn criaColuna(DefaultTableColumnModel columnModel, String titulo, int tamanho) {

		TableColumn column = new TableColumn();
		column.setHeaderValue(titulo);
		column.setResizable(false);
		column.setWidth(tamanho);
		column.setMaxWidth(tamanho);
		column.setMinWidth(tamanho);
		column.setModelIndex(columnModel.getColumnCount());
		return column;
	}

	private JComboBox<Cliente> criaComboClientes(int linha, int tamanho) {

		JComboBox<Cliente> combo = new JComboBox<>(Cliente.values());
		combo.setLocation(100, linha);
		combo.setSize(tamanho * 10, 25);

		add(criaLabel("Cliente", linha, 10));

		return combo;
	}

	private JTextField criaTextField(String string, int linha, int coluna, int tamanho) {
		return criaTextField(string, linha, coluna, tamanho, 10);
	}
	
	private JTextField criaTextField(String string, int linha, int coluna, int tamanho, int colunaLabel) {

		JTextField txt = new JTextField("");
		txt.setLocation(coluna, linha);
		txt.setSize(tamanho * 10, 25);

		add(criaLabel(string, linha, colunaLabel));

		return txt;
	}

	private JLabel criaLabel(String string, int linha, int coluna) {

		JLabel label = new JLabel(string);
		label.setLocation(coluna, linha);
		label.setSize(100, 25);

		return label;
	}

	public Pedido getPedido() {

		return pedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public boolean pressionouOk() {

		return pressionouOk;
	}

	private boolean testaVazio(JTextField field, String label) {

		if (field.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(botaoOk, "Necessário preencher " + label);
			return true;
		}
		return false;
	}
	
	private boolean testaDataValida(JTextField field) {
		String data = field.getText().trim();
		
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		sdf.setLenient(false);
        try {
            sdf.parse(data);
        } catch (ParseException e) {
        	String message = "Data não está no formato dia/mês/ano ou não é uma data válida!";
        	JOptionPane.showMessageDialog(botaoOk, message);
            return true;
        }
        return false;
	}
	
	private void atualizaValorTotal() {
		double valorTotal = this.business.getValorTotalPedido(pedido);

		// comando atualiza e mostra ao usuario no campo o valor total
		textValorTotal.setText(NumberFormat.getCurrencyInstance().format(valorTotal));
	}

	private class ActionOk implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (testaVazio(textData, "data")) {
				return;
			}
			
			if (testaDataValida(textData)) {
				return;
			}
			
			
			//Tratamento de Pelo menos uma pizza ou uma bebida no pedido
		    // verificando se pelo menos uma das grids possui conteudo
			if(pizzaGrid.getRowCount() == 0 && bebidaGrid.getRowCount() == 0 ){
			    JOptionPane.showMessageDialog(botaoOk, "Necessário no pedido pelo menos uma Pizza ou bebida!");
			    return;
			}
			
			pressionouOk = true;

			pedido.setData(textData.getText().trim());
			cliente = (Cliente) comboCliente.getSelectedItem();
			
			
			// Verificação de não seleção de clientes
			if(cliente.getNome() == "" ) 
			{
				// mensagem ao usuario
				 JOptionPane.showMessageDialog(botaoOk, "Necessário informar o Cliente!");	
				 return;
			}
			
			
			DlgAdicionarPedido.this.pedido = pedido;

			DlgAdicionarPedido.this.setVisible(false);
		}
	}

	private class ActionCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			DlgAdicionarPedido.this.setVisible(false);
		}
	}

	private class ActionAdicionarBebida implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				DlgAdicionarBebida dialog = new DlgAdicionarBebida();
				dialog.setVisible(true);

				if (!dialog.pressionouOk()) {
					return;
				}

				PedidoBebida pedidoBebida = dialog.getPedidoBebida();

				pedido.adicionaBebida(pedidoBebida);

				bebidaTableModel.fireTableDataChanged();
				DlgAdicionarPedido.this.atualizaValorTotal();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoAdicionarBebida, ex.getLocalizedMessage());
			}
		}
	}

	private class ActionAdicionarPizza implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				DlgAdicionarPizza dialog = new DlgAdicionarPizza();
				dialog.setVisible(true);

				if (!dialog.pressionouOk()) {
					return;
				}

				PedidoPizza pedidoPizza = dialog.getPedidoPizza();

				pedido.adicionaPizzaPedido(pedidoPizza);

				pizzaTableModel.fireTableDataChanged();
				DlgAdicionarPedido.this.atualizaValorTotal();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoAdicionarPizza, ex.getLocalizedMessage());
			}
		}
	}
	
	private class ActionRemoverPizza implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				// pega valor selecionado na grid ou lista
				int linhaSelecionada = pizzaGrid.getSelectedRow();
				
				// verifica a não seleção
				if(linhaSelecionada < 0) {
					JOptionPane.showMessageDialog(botaoRemoverPizza, "Necessário selecionar uma linha na lista de Pizza! ");
					return;
				}
				
				// pega elemnto selecionado
				PedidoPizza pedidoPizza = pedido.getPedidoPizzas().get(linhaSelecionada);
				
				
				// realiza a remoção
				business.removePedidoPizza(pedido, pedidoPizza);
				pizzaTableModel.fireTableDataChanged();
				
				// atualiza o valor total do pedido
				DlgAdicionarPedido.this.atualizaValorTotal();
				
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoRemoverPizza, ex.getLocalizedMessage());
			}
		}
	}
	
	private class ActionRemoverBebida implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				int linhaSelecionada = bebidaGrid.getSelectedRow();
				
				// Verifica não seleção
				if(linhaSelecionada < 0) {
					JOptionPane.showMessageDialog(botaoRemoverBebida, "Necessário selecionar uma linha na lista de Bebida! ");
					return;
				}
				
				// pega elemento selecionado
				PedidoBebida pedidoBebiba = pedido.getPedidoBebidas().get(linhaSelecionada);
				
				
				// realiza a remoção
				business.removePedidoBebida(pedido, pedidoBebiba);
				bebidaTableModel.fireTableDataChanged();
				
				// atualiza valor total
				DlgAdicionarPedido.this.atualizaValorTotal();
				
				
			} catch (Exception ex) {
				// tratamento de erro
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoRemoverBebida, ex.getLocalizedMessage());
			}
			
		}
	}
	private class ActionDetalhes implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				int linhaSelecionada = pizzaGrid.getSelectedRow();
				
				// Verifica não seleção
				if(linhaSelecionada < 0) {
					JOptionPane.showMessageDialog(botaoDetalhesPizza, "Seleciona uma linha na lista de Pizza!");
					return;
				}
				
				
				// pega elemnto selecionado
				PedidoPizza pedidoPizza = pedido.getPedidoPizzas().get(linhaSelecionada);
				
				
				if(pedidoPizza.getPizzaUnica() !=  null )
				{
					//
					// pizza unica
					JOptionPane.showMessageDialog(botaoDetalhesPizza, "A pizza de sabor '"+ pedidoPizza.getPizzaUnica().getDescricao().toString() +"' \n Os ingredientes: "+ pedidoPizza.getPizzaUnica().getIngredientes().toString());
				
					
				}else
				{
					//System.out.println("entrou else");
					
					JOptionPane.showMessageDialog(botaoDetalhesPizza, "Pizza de dois sabores! \n\n A pizza de sabor 1 -  '"+ pedidoPizza.getPizzasSabores().get(0).getDescricao().toString().trim()  +"' \n Os ingredientes: " + pedidoPizza.getPizzasSabores().get(0).getIngredientes().toString() + "\n\n A pizza de sabor 2 - '"+ pedidoPizza.getPizzasSabores().get(1).getDescricao().toString() +"' \n Os ingredientes: "+ pedidoPizza.getPizzasSabores().get(1).getIngredientes().toString());

				}
				
				
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoDetalhesPizza, ex.getLocalizedMessage());
			}
		}
	}
}
