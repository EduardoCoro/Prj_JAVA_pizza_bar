package br.com.pizzaria.view;

//import java.util.ArrayList;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import br.com.pizzaria.model.entidades.cardapio.Adicional;
import br.com.pizzaria.model.entidades.cardapio.Borda;
import br.com.pizzaria.model.entidades.cardapio.Massa;
import br.com.pizzaria.model.entidades.cardapio.Pizza;
import br.com.pizzaria.model.entidades.cardapio.Tamanho;
import br.com.pizzaria.model.entidades.pedido.PedidoPizza;
import br.com.pizzaria.view.tabela.AdicionalColumnModel;
import br.com.pizzaria.view.tabela.AdicionalTableModel;

public class DlgAdicionarPizza extends JDialog {
	private PedidoPizza pedidoPizza = new PedidoPizza();

	private final JButton botaoOk = new JButton("OK");
	private final JButton botaoCancelar = new JButton("Cancelar");
	private final JButton botaoAdicionarAdicional = new JButton("Adicionar adicional");
	
	private final AdicionalColumnModel columnModel = new AdicionalColumnModel();
	private final AdicionalTableModel tableModel = new AdicionalTableModel(pedidoPizza, columnModel);
	private final JTable grid = new JTable(tableModel, columnModel, null);
	private final JScrollPane pane = new JScrollPane(grid);

	private final JComboBox<Pizza> comboPizzaUnica = new JComboBox<>();

	private final JCheckBox checkSaborUnico = new JCheckBox();
	
	private final JComboBox<Pizza> comboPizzaSabor1 = new JComboBox<>();
	private final JComboBox<Pizza> comboPizzaSabor2 = new JComboBox<>();
	private final JComboBox<Tamanho> comboTamanho = new JComboBox<>();
	private final JComboBox<Borda> comboBorda = new JComboBox<>();
	private final JComboBox<Massa> comboMassa = new JComboBox<>();

	private boolean pressionouOk = false;

	public DlgAdicionarPizza() {
		setSize(500, 470);
		setLayout(null);
		setModal(true);
		
		setTitle("Adicionar pizza");
		
		adicionaColunasGrid();
		
		grid.getTableHeader().setReorderingAllowed(false);

		botaoOk.addActionListener(new ActionOk());
		botaoCancelar.addActionListener(new ActionCancelar());
		botaoAdicionarAdicional.addActionListener(new ActionAdicionarAdicionais());

		botaoOk.setSize(100, 30);
		botaoOk.setLocation(15, 390);

		botaoCancelar.setSize(100, 30);
		botaoCancelar.setLocation(150, 390);
		
		botaoAdicionarAdicional.setSize(200, 30);
		botaoAdicionarAdicional.setLocation(250, 390);

		checkSaborUnico.setLocation(100, 10);
		checkSaborUnico.setSize(30, 30);
		checkSaborUnico.addActionListener(new ActionSaborUnico());
		checkSaborUnico.setSelected(true);
		add(criaLabel("Sabor único", 10));
		
		comboPizzaUnica.setLocation(100, 40);
		comboPizzaUnica.setSize(250, 25);
		add(criaLabel("Pizza", 40));
		
		comboPizzaSabor1.setLocation(100, 70);
		comboPizzaSabor1.setSize(250, 25);
		add(criaLabel("Pizza sabor 1", 70));
		
		comboPizzaSabor2.setLocation(100, 100);
		comboPizzaSabor2.setSize(250, 25);
		add(criaLabel("Pizza sabor 2", 100));
		
		comboTamanho.setLocation(100, 130);
		comboTamanho.setSize(250, 25);
		add(criaLabel("Tamanho", 130));
		
		comboBorda.setLocation(100, 160);
		comboBorda.setSize(250, 25);
		add(criaLabel("Borda", 160));
		
		comboMassa.setLocation(100, 190);
		comboMassa.setSize(250, 25);
		add(criaLabel("Massa", 190));
		
		pane.setLocation(0, 220);
		pane.setSize(480, 155);
		
		add(checkSaborUnico);
		add(comboPizzaUnica);
		add(comboPizzaSabor1);
		add(comboPizzaSabor2);
		add(comboTamanho);
		add(comboBorda);
		add(comboMassa);

		add(pane);
		
		add(botaoOk);
		add(botaoCancelar);
		add(botaoAdicionarAdicional);

		for (Pizza pizza : Pizza.values()) {
			comboPizzaUnica.addItem(pizza);
			comboPizzaSabor1.addItem(pizza);
			comboPizzaSabor2.addItem(pizza);
		}
		
		for (Borda borda : Borda.values()) {
			comboBorda.addItem(borda);
		}
		
		for (Massa massa : Massa.values()) {
			comboMassa.addItem(massa);
		}
		
		for (Tamanho tamanho : Tamanho.values()) {
			comboTamanho.addItem(tamanho);
		}
		
		setPizzaSaborUnico(checkSaborUnico.isSelected());
	}
	
	private void adicionaColunasGrid() {
		columnModel.addColumn(criaColuna(columnModel, "Adicional", 480));
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

	private JLabel criaLabel(String string, int linha) {

		JLabel label = new JLabel(string);
		label.setLocation(10, linha);
		label.setSize(100, 25);

		return label;
	}

	public PedidoPizza getPedidoPizza() {

		return pedidoPizza;
	}

	public boolean pressionouOk() {

		return pressionouOk;
	}
	
	
	public double AtualizarpedidoPizza(PedidoPizza pedido) {
		   
	    // iniciação da variavel e depois para ser utilizada como somatório dos valores
	    double valor = 0;
	    
	    // checando se é somente um único sabor na pizza, atraves do chek selecionado
	    if (checkSaborUnico.isSelected()) 
	    { 
	        // calculando os valores de acordo com o tamanho fornecido
	        // com o valor inserido em pedido
	        
	        if( pedido.getTamanho().toString().trim() == "BROTO" ) 
	        {
	        	// calculando o valor da pizza, conforme o adicionado
	            valor += pedido.getPizzaUnica().getValorBroto();
	            
	                valor+= pedido.getBorda().getValorBroto();
	                valor+= pedido.getMassa().getValorBroto();
	                
	            
	        }
	        else if (pedido.getTamanho().toString().trim() == "MEDIA") 
	        {
	        	// calculando o valor da pizza, conforme o adicionado
	            valor += pedido.getPizzaUnica().getValorMedia();
	            valor+= pedido.getBorda().getValorMedia();
	            valor+= pedido.getMassa().getValorMedia();
	        
	        }
	        else if (pedido.getTamanho().toString().trim() == "GRANDE") 
	        {
	        	// calculando o valor da pizza, conforme o adicionado
	            valor += pedido.getPizzaUnica().getValorGrande();
	            valor+= pedido.getBorda().getValorGrande();
	            valor+= pedido.getMassa().getValorGrande();
	        
	        }
	        else if(pedido.getTamanho().toString().trim() == "BIG") 
	        {
	        	// calculando o valor da pizza, conforme o adicionado
	            valor += pedido.getPizzaUnica().getValorBig();
	            valor+= pedido.getBorda().getValorBig();
	            valor+= pedido.getMassa().getValorBig();
	            
	        }
	    }
	    else {
	             // a variavel numerador será utilizada para gravar o valor '0' ou '1', que são as únicas possição da Lista de Sabores da pizza.
	            int numerador = 0;
	            
	            // Descobrindo qual dos dois sabores é o maior valor, conforme a regra de negócio, pegando somente a posição que se encontra na lista
	            if(pedido.getPizzasSabores().get(0).getValorBroto() > pedido.getPizzasSabores().get(1).getValorBroto()) 
	            {
	                numerador = 0;
	            }
	            else if(pedido.getPizzasSabores().get(0).getValorMedia() > pedido.getPizzasSabores().get(1).getValorMedia()) 
	            {
	                numerador = 0;
	            }
	            else if(pedido.getPizzasSabores().get(0).getValorGrande() > pedido.getPizzasSabores().get(1).getValorGrande())
	            {
	                numerador = 0;
	            }
	            else if (pedido.getPizzasSabores().get(0).getValorBig() > pedido.getPizzasSabores().get(1).getValorBig()) 
	            {
	                numerador = 0;
	            }
	            else {
	                numerador = 1;
	            }
	            
	            
	            /// calculando... conforme o tamanho da pizza selecionado , é dos dois sabores de pizza utilizado a variavel 'numerador' como indice
	            if( pedido.getTamanho().toString().trim() == "BROTO" ) 
	            {
	            	// calculando o valor da pizza, conforme o adicionado
	                    valor += pedido.getPizzasSabores().get(numerador).getValorBroto();
	                    valor+= pedido.getBorda().getValorBroto();
	                    valor+= pedido.getMassa().getValorBroto();
	                    
	                
	            }
	            else if (pedido.getTamanho().toString().trim() == "MEDIA")
	            {
	            	// calculando o valor da pizza, conforme o adicionado
	                valor += pedido.getPizzasSabores().get(numerador).getValorMedia();
	                valor+= pedido.getBorda().getValorMedia();
	                valor+= pedido.getMassa().getValorMedia();
	            
	            }
	            else if (pedido.getTamanho().toString().trim() == "GRANDE") 
	            {
	            	// calculando o valor da pizza, conforme o adicionado
	                valor += pedido.getPizzasSabores().get(numerador).getValorGrande();
	                valor+= pedido.getBorda().getValorGrande();
	                valor+= pedido.getMassa().getValorGrande();
	            
	            }
	            else if(pedido.getTamanho().toString().trim() == "BIG") 
	            {
	            	// calculando o valor da pizza, conforme o adicionado
	                valor += pedido.getPizzasSabores().get(numerador).getValorBig();
	                valor+= pedido.getBorda().getValorBig();
	                valor+= pedido.getMassa().getValorBig();
	                
	            
	            }    		
	         }
	    
	    // Calcular os adicionais para somar os valores do pedido
	    
	    
	    List<Adicional> valordoAdicional = pedido.getAdicionais();
	    
	    // a realização do laço de repetição foi feita para percorrer a Lista de adicionais atraves dos índices
	    for(int i=0; i < valordoAdicional.size(); i++) 
	    {
	        
	        
	        // estruta para identificar qual tamanho a ser calculado
	        if( pedido.getTamanho().toString().trim() == "BROTO" ) 
	        {
	            valor += valordoAdicional.get(i).getValorBroto();
	        }
	        else if (pedido.getTamanho().toString().trim() == "MEDIA")
	        {
	            valor += valordoAdicional.get(i).getValorMedia();
	        }
	        else if (pedido.getTamanho().toString().trim() == "GRANDE") 
	        {
	            valor += valordoAdicional.get(i).getValorGrande();
	        }
	        else if(pedido.getTamanho().toString().trim() == "BIG") 
	        {
	            valor += valordoAdicional.get(i).getValorBig();
	        }
	    }
	    
	    // retornando a função com o valor do pedido da pizza 
	    return valor;
	}

	private class ActionOk implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
         try {
        	 
        	// inserindo as informações que o usuario informou
        	pedidoPizza.setBorda((Borda) comboBorda.getSelectedItem());
 			pedidoPizza.setMassa((Massa) comboMassa.getSelectedItem());
 			pedidoPizza.setTamanho((Tamanho) comboTamanho.getSelectedItem());
 			
 			// checando se o campo está selecionado
			if (checkSaborUnico.isSelected()) {
				pedidoPizza.setPizzaUnica((Pizza) comboPizzaUnica.getSelectedItem());
				
				// Verificação da pizza vegetariana Bordas: sem borda / requeijão / queijo/ Adicionais: requeijão / milho / azeitona preta
				if(pedidoPizza.getPizzaUnica().getCategoria().toString() == "VEGETARIANA")
				{
					if(pedidoPizza.getBorda().getDescricao().toString().trim() == "SEM BORDA" || pedidoPizza.getBorda().getDescricao().toString().trim() == "REQUEIJÃO" || pedidoPizza.getBorda().getDescricao().toString().trim() == "QUEIJO")
					{
						for(int i=0; i < grid.getRowCount(); i++ ) 
						{
							if(pedidoPizza.getAdicionais().get(i).getDescricao().toString().trim() == "REQUEIJÃO" || pedidoPizza.getAdicionais().get(i).getDescricao().toString().trim() == "MILHO" || pedidoPizza.getAdicionais().get(i).getDescricao().toString().trim() == "AZEITONA PRETA")
							{
							}
							else {
								JOptionPane.showMessageDialog(botaoOk, "Atenção! Na pizza Vegetariana Somente adicional: Requeijão / Milho / Azeitona preta");
								return;
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(botaoOk, "Atenção! Na pizza Vegetariana Somente as bordas na opção: Sem borda / Requeijão / Queijo");
						return;
					}
				}
				
				
			} else {
				if (!testaSaboresIguais(comboPizzaSabor1, comboPizzaSabor2)) {
					return;
				}
				
				// se for pizza de 2 sabores é salva na lista de PizzaSabores
				pedidoPizza.adicionaSabor1((Pizza) comboPizzaSabor1.getSelectedItem());
				pedidoPizza.adicionaSabor2((Pizza) comboPizzaSabor2.getSelectedItem());
				
				
				// Verificação da pizza vegetariana Bordas: sem borda / requeijão / queijo/ Adicionais: requeijão / milho / azeitona preta
				if(pedidoPizza.getPizzasSabores().get(0).getCategoria().toString() == "VEGETARIANA" || pedidoPizza.getPizzasSabores().get(1).getCategoria().toString() == "VEGETARIANA" )
				{
					if(pedidoPizza.getBorda().getDescricao().toString().trim() == "SEM BORDA" || pedidoPizza.getBorda().getDescricao().toString().trim() == "REQUEIJÃO" || pedidoPizza.getBorda().getDescricao().toString().trim() == "QUEIJO")
					{
						for(int i=0; i < grid.getRowCount(); i++ ) 
						{
							if(pedidoPizza.getAdicionais().get(i).getDescricao().toString().trim() == "REQUEIJÃO" || pedidoPizza.getAdicionais().get(i).getDescricao().toString().trim() == "MILHO" || pedidoPizza.getAdicionais().get(i).getDescricao().toString().trim() == "AZEITONA PRETA")
							{
							}
							else {
								JOptionPane.showMessageDialog(botaoOk, "Atenção! Na pizza Vegetariana Somente adicional: Requeijão / Milho / Azeitona preta");
								return;
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(botaoOk, "Atenção! Na pizza Vegetariana Somente as bordas na opção: Sem borda / Requeijão / Queijo");
						return;
					}
				}
				
			}
			
			
			
			// adicionar o valor do pedido pizza
			pedidoPizza.setValorTotal(AtualizarpedidoPizza(pedidoPizza));
			
			
			
			
			
			DlgAdicionarPizza.this.pressionouOk = true;
			DlgAdicionarPizza.this.pedidoPizza = pedidoPizza;

			DlgAdicionarPizza.this.setVisible(false);
         
		}
        catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(botaoOk, ex.getLocalizedMessage());
			
		}
	}
	}
	
	private class ActionSaborUnico implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox check = (JCheckBox) e.getSource();
			if (check.isSelected()) {
				comboPizzaUnica.setEnabled(true);
				
				comboPizzaSabor1.setEnabled(false);
				comboPizzaSabor2.setEnabled(false);
			} else {
				comboPizzaUnica.setEnabled(false);

				comboPizzaSabor1.setEnabled(true);
				comboPizzaSabor2.setEnabled(true);
			}
		}
	}
	
	private void setPizzaSaborUnico(boolean saborUnico) {
		if (saborUnico) {
			comboPizzaUnica.setEnabled(true);
			
			comboPizzaSabor1.setEnabled(false);
			comboPizzaSabor2.setEnabled(false);
		} else {
			comboPizzaUnica.setEnabled(false);
			
			comboPizzaSabor1.setEnabled(true);
			comboPizzaSabor2.setEnabled(true);
		}
	}

	private boolean testaSaboresIguais(JComboBox<Pizza> comboPizzaSabor1, JComboBox<Pizza> comboPizzaSabor2) {
		Pizza pizzaSabor1 = (Pizza) comboPizzaSabor1.getSelectedItem();
		Pizza pizzaSabor2 = (Pizza) comboPizzaSabor2.getSelectedItem();
		
		if (pizzaSabor1 != pizzaSabor2) {
			return true;
		}

		JOptionPane.showMessageDialog(botaoOk, "Sabores não podem ser iguais!");
		return false;
	}

	private class ActionCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			DlgAdicionarPizza.this.setVisible(false);
		}

	}
	
	private class ActionAdicionarAdicionais implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				DlgAdicionarAdicionais dialog = new DlgAdicionarAdicionais();
				dialog.setVisible(true);

				if (!dialog.pressionouOk()) {
					return;
				}

				// Tratamento de somente adicionar  3  adicionais 
				if(grid.getRowCount() > 2) {
				    JOptionPane.showMessageDialog(botaoAdicionarAdicional, "É permitido adicionar somente 3 adicionais!");
				    return;
				}
				
				Adicional adicional = dialog.getAdicional();

				pedidoPizza.adicionaAdicional(adicional);

				tableModel.fireTableDataChanged();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoAdicionarAdicional, ex.getLocalizedMessage());
			}
		}
	}
}
