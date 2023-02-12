package br.com.pizzaria.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import br.com.pizzaria.model.business.PizzariaBusiness;
import br.com.pizzaria.model.entidades.cliente.Cliente;
import br.com.pizzaria.model.entidades.pedido.Pedido;
import br.com.pizzaria.model.entidades.pedido.PedidoBebida;
import br.com.pizzaria.model.entidades.pedido.PedidoPizza;
import br.com.pizzaria.model.entidades.pedido.PedidoTelaPrincipal;
import br.com.pizzaria.view.tabela.PedidosColumnModel;
import br.com.pizzaria.view.tabela.PedidosTableModel;

public class ViewPizzaria extends JFrame {
	
	private PedidoTelaPrincipal tela = new PedidoTelaPrincipal();

	private final PizzariaBusiness business = new PizzariaBusiness();

	private final PedidosColumnModel columnModel = new PedidosColumnModel();
	private final PedidosTableModel tableModel = new PedidosTableModel(business, columnModel);
	private final JTable gridPedidos = new JTable(tableModel, columnModel, null);

	private final JScrollPane pane = new JScrollPane(gridPedidos);

	private final JButton botaoAdicionarPedido = new JButton("Adicionar pedido");
	private final JButton botaoMediaDiaria = new JButton("Média diária");
	private final JButton botaoMaisConsumido = new JButton("Mais consumido por categoria");
	//private final JButton botaoRemoverDiario = new JButton("Remover diário");
	private final JButton botaoAddBebidaSaldo = new JButton("Saldo Bebida");

	

	public ViewPizzaria() {

		setTitle("Lanches!");
		setLayout(null);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		pane.setSize(860, 500);
		pane.setLocation(0, 0);

		adicionaColunasGrid();

		gridPedidos.getTableHeader().setReorderingAllowed(false);

		add(pane);

		configuraBotoes();

		add(botaoAdicionarPedido);
		add(botaoMediaDiaria);
		add(botaoMaisConsumido);
		//add(botaoRemoverDiario);
		add(botaoAddBebidaSaldo);
		
		atualizaTitulo();
	}

	private void atualizaTitulo() {

		setTitle("Sistema de controle da Pizzaria!!!");
	}

	private void configuraBotoes() {

		botaoAdicionarPedido.setSize(140, 30);
		botaoAdicionarPedido.setLocation(1, 520);
		botaoAdicionarPedido.addActionListener(new ActionAdicionarPedido());
		
		botaoMediaDiaria.setSize(140, 30);
		botaoMediaDiaria.setLocation(150, 520);
		botaoMediaDiaria.addActionListener(new ActionMediaDiaria());
		
		botaoMaisConsumido.setSize(200, 30);
		botaoMaisConsumido.setLocation(300, 520);
		botaoMaisConsumido.addActionListener(new ActionMaisConsumido());
		
		// Remover diario / incompleto
		/*
		botaoRemoverDiario.setSize(140, 30);
		botaoRemoverDiario.setLocation(500, 520);
		botaoRemoverDiario.addActionListener(new ActionRemoverDiario());
		*/
		botaoAddBebidaSaldo.setSize(120,30);
		botaoAddBebidaSaldo.setLocation(650, 520);
		botaoAddBebidaSaldo.addActionListener(new ActionAddBebidaSaldo());
		
	}

	private void adicionaColunasGrid() {
		columnModel.addColumn(criaColuna("Data", 260));
		columnModel.addColumn(criaColuna("Quantidade de pedidos", 260));
		columnModel.addColumn(criaColuna("Valor total", 240));
	}

	private TableColumn criaColuna(String titulo, int tamanho) {
		TableColumn column = new TableColumn();
		column.setHeaderValue(titulo);
		column.setResizable(false);
		column.setWidth(tamanho);
		column.setMaxWidth(tamanho);
		column.setMinWidth(tamanho);
		column.setModelIndex(columnModel.getColumnCount());
		return column;
	}
	
	private class ActionAddBebidaSaldo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			
			try {
				
				// chama o formulario de saldo bebida
				
				DlgAtualizaBebida dialog = new DlgAtualizaBebida();
				dialog.setVisible(true);

				if (!dialog.pressionouOk()) {
					return;
				}
				
				
			}catch (Exception ex) 
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoAddBebidaSaldo, ex.getLocalizedMessage());
			}
		}
			
			
	}
		
		
	

	private class ActionAdicionarPedido implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				DlgAdicionarPedido dialog = new DlgAdicionarPedido(business);
				dialog.setVisible(true);

				if (!dialog.pressionouOk()) {
					return;
				}

				Cliente cliente = dialog.getCliente();
				Pedido pedido = dialog.getPedido();

				business.adicionarPedido(cliente, pedido);

				tableModel.fireTableDataChanged();

				atualizaTitulo();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoAdicionarPedido, ex.getLocalizedMessage());
			}

		}
	}
	
	
	// função criada para aredondar o valor
	// será usado no ato do valor da porcentagem da media diaria, para evitar exibição de números longos
	private double arredondar(double media) 
	{
		   return Math.round(media * 100.0)/100.0;
	}

	private class ActionMediaDiaria implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				// pega a posição na grid que o usuario selecionou
				int linhaSelecionada = gridPedidos.getSelectedRow();
				
				// verificação se foi selecionado a grid na tela inicial
				if(linhaSelecionada < 0) {
					JOptionPane.showMessageDialog(botaoMediaDiaria, "Necessário selecionar uma linha na lista de pedidos! ");
					return;
				}
				
				PedidoTelaPrincipal pedidoTela = business.getPedidosTelaPrincipal().get(linhaSelecionada);
				
				// Calcula da média diária.
				double media = (pedidoTela.getValorTotal() * 100) / 150;
				// mensagem exibindo o resultado do calculo
				
				if(media >= 100) 
				{
					JOptionPane.showMessageDialog(botaoMediaDiaria, "Meta diária cumprida! pois está em :"+ arredondar(media) +"% atingida");
				}
				else
				{
					JOptionPane.showMessageDialog(botaoMediaDiaria, "A meta diária está em :"+ arredondar(media) +"% atingida");
				}
				
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoAdicionarPedido, ex.getLocalizedMessage());
			}
		}
	}
	
	
	/* Comando que seria a ser utilizado no botão de remover os  pedidos no diario da tela Pedidos
	 *  Função está comentada
	 * 
	private class ActionRemoverDiario implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			try {
				int linhaSelecionada = gridPedidos.getSelectedRow();
				
				// verificação se foi selecionado a grid na tela inicial
				if(linhaSelecionada < 0) {
					JOptionPane.showMessageDialog(botaoRemoverDiario, "Necessário selecionar uma linha na lista de pedidos! \n Para poder remover o Pedido ");
					return;
				}
				
				//List<PedidoCliente> pedidoCliente =  business.getPedidoCliente().g;
				
				PedidoTelaPrincipal pedidoTela = business.getPedidosTelaPrincipal().get(linhaSelecionada);
				
				
				
				

				//removerPedidoTelaPrincipal(business.getPedidosTelaPrincipal().get(linhaSelecionada), linhaSelecionada);
				
				
				//List<PedidoTelaPrincipal> pedidoTela = (List<PedidoTelaPrincipal>) business.getPedidosTelaPrincipal().get(linhaSelecionada);
				      
				tela.removerPedidoTelaPrincipal((List<PedidoTelaPrincipal>) pedidoTela, linhaSelecionada);
				
				// remove(linhaSelecionada);
				
				 //      listaContato.remove(r); //Passe o contato que vc recebeu como parametro para frente..  
				  
				
				
				
				tableModel.fireTableDataChanged();
				
				
				
			}catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoRemoverDiario, ex.getLocalizedMessage());
			
			}
		
		
		}

		
	}
   */
	
	
	
	/*
	 * 
	 * Na ação do botão ‘Mais consumido’, implementar uma nova funcionalidade.
Este botão deverá imprimir (via console) os detalhes dos pedidos de um determinado cliente.

A PizzariaBusiness deve retornar a lista de pedido do cliente e a impressão deve ocorrer na Action
 
Deverá imprimir:
• Para cada objeto de pedido cliente, imprimir:
   ◦ Nome do cliente
   ◦ Valor total do pedido
   ◦ Pizzas (tratar somente as pizzas de 1 sabor):
     ▪ Nome da pizza
     ▪ Tamanho da pizza
     ▪ Borda da pizza
 
     ▪ Tipo da massa da pizza
     ▪ Quantidade de adicionais
     
     
   ◦ Bebidas
     ▪ Nome da bebida
     ▪ Quantidade
	 * 
	 */
	
	private class ActionMaisConsumido implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// tratamento de erro
			try{
				
				
				
				
				// verifica se os pedidos está vazia!
				if(business.getPedidoCliente().size() <= 0 )
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "Nenhum pedido cadastrado!   \n\n Obs: somente se houver um pedido cadastrado!");
					return;
				}
				
				
				// inserção noo console
				
				
				//Pedido 
				
				
				 for( int i=0;  i <  business.getPedidoCliente().size(); i++)
				 {
					 
					 // Nome do cliente 
					 System.out.println("Cliente : " + business.getPedidoCliente().get(i).getCliente().toString());
					 
					 // valor do pedido 
					 
					 System.out.println("Valor: R$" + business.getPedidoCliente().get(i).getPedidos().get(i).getValorTotal());
					 
					 
					  List<Pedido> pedidoscliente = business.getPedidoCliente().get(i).getPedidos();
					 
					 // lista de pedidos de cliente
					 
					 
					 
					 for( int pedido=0; pedido < pedidoscliente.size(); pedido++)
					 {
						 
						
						 
						 // lista de pizzas
						 
						 for( int p=0;   p < pedidoscliente.get(pedido).getPedidoPizzas().size();  p++)
						 {
							 
							 PedidoPizza ReadForpizza = pedidoscliente.get(pedido).getPedidoPizzas().get(p);
							 
							 // sabor
							  System.out.println("Sabor: "+ ReadForpizza.getPizzaUnica().getDescricao().toString());
							 
							 // tamanho
							 
							  System.out.println("Tamanho: "+ ReadForpizza.getTamanho().toString());
							 
							 /// borda
							  
							  System.out.println("Borda: "+ ReadForpizza.getBorda().toString());
							  
							  
							  // Massa 
							  System.out.println("Massa: "+ ReadForpizza.getMassa().toString());
							  
							  //Quandidate Adicionais
							  
							  System.out.println("Quantidade: "+ ReadForpizza.getQuantidadeAdicionais());
							 
						 }
							
						 
						 
						 // lista de bebidas
						 for( int p=0;   p < pedidoscliente.get(pedido).getPedidoBebidas().size();  p++)
						 {
						   
							 PedidoBebida ReadForbebida = pedidoscliente.get(pedido).getPedidoBebidas().get(p);
							 
							 // nome da bebida
							 
							 System.out.println("Bebida :" +  ReadForbebida.getBebida().getDescricao().toString());
							 
							 
							 // Quantidade
							 System.out.println("Qdt:"+ ReadForbebida.getQuantidade());
							 
						 }
						 
						 
						 
					 }
					
					 
					
					 
				 }
				
				
				/*
				
				//categorias existentes (clássica, doce ou vegetariana).
				int classica = 0 , doce = 0 , vegetariana = 0;
				
				// variavel que será utilizada para gravar os sabores da pizza mais consumido
				String maisconsumidapizzaunica="";
				String maisconsumidapizzsabor1="";
				String maisconsumidapizzsabor2="";
				
					
				// chamando a função em PizzaBusiness pegando os pedidos dos clientes
				for(int i=0; i < business.getPedidoCliente().size(); i++)
				{
					
					for(int k = 0; k < business.getPedidoCliente().get(i).getPedidos().size(); k++) 
					{
						Pedido pedidoReadFor = business.getPedidoCliente().get(i).getPedidos().get(k);
						
						// variavel auxiliar, que carregará o valor de sabor
						String aux ="";
						
						// Utilizado para descobrir o sabor da categoria Classica
						int somc=0, difc=0;
						
						// Utilizado para descobrir o sabor da categoria Doce
						int somd=0, difd=0;
						
						// Utilizado para descobrir o sabor da categoria vegetariana
						int somv=0, difv=0;
						
						/// buscar categoria de pizza
						for(int p=0; p < pedidoReadFor.getPedidoPizzas().size(); p++)
						{
							
							if(pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica() != null)
							{
								// pizza unica
								// realizar pontuação de categorias
								if(pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getCategoria().toString() == "CLASSICA")
								{
									classica+=1;
								}
								else if(pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getCategoria().toString() == "DOCE")
								{
									doce+=1;
								}
								else if(pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getCategoria().toString() == "VEGETARIANA")
								{
									vegetariana+=1;
								}
								
								
								// Verificação da lógica, para descobrir o sabor que mais sendo usado
								
								if(classica > doce && classica > vegetariana) 
								{
									if(aux == "")
									{
										aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString();
										somc+=1;
									}
									else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString())
									{
										somc+=1;
									}
									else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString())
									{
										difc+=1;
										if(difc > somc)
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString();
											somc=1;
											difc=0;
										}
									}
								}
								else if(classica < doce && doce > vegetariana)
								{
									if(aux == "")
									{
										aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString();
										somd+=1;
									}
									else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString())
									{
										somd+=1;
									}
									else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString())
									{
										difd+=1;
										if(difd > somd)
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString();
											somd=1;
											difd=0;
										}
									}
								}
								else if(classica < vegetariana && doce < vegetariana)
								{
									
									if(aux == "")
									{
										aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString();
										somv+=1;
									}
									else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString())
									{
										somv+=1;
									}
									else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString())
									{
										difv+=1;
										if(difv > somv)
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzaUnica().getDescricao().toString();
											somv=1;
											difv=0;
										}
									}
								}
								
								maisconsumidapizzaunica = aux;
								
							}
							else
							{
								// pizza de 2 sabores
								
								if(pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getCategoria().toString() == "CLASSICA" || pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getCategoria().toString() == "CLASSICA")
								{
									classica+=1;
								}
								else if(pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getCategoria().toString() == "DOCE" || pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getCategoria().toString() == "DOCE")
								{
									doce+=1;
								}
								else if(pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getCategoria().toString() == "VEGETARIANA" || pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getCategoria().toString() == "VEGETARIANA")
								{
									vegetariana+=1;
								}
								
								
								// realizar pontuação de categorias

                                // Verificação da lógica, para descobrir o sabor que mais sendo usado
								
								if(pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores() != null)
								{
									
									if(classica > doce && classica > vegetariana) 
									{
										if(aux == "")
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString();
											somc+=1;
										}
										else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString())
										{
											somc+=1;
										}
										else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString())
										{
											difc+=1;
											if(difc > somc)
											{
												aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString();
												somc=1;
												difc=0;
											}
										}
									}
									else if(classica < doce && doce > vegetariana)
									{
										if(aux == "")
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString();
											somd+=1;
										}
										else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString())
										{
											somd+=1;
										}
										else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString())
										{
											difd+=1;
											if(difd > somd)
											{
												aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString();
												somd=1;
												difd=0;
											}
										}
									}
									else if(classica < vegetariana && doce < vegetariana)
									{
										
										if(aux == "")
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString();
											somv+=1;
										}
										else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString())
										{
											somv+=1;
										}
										else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString())
										{
											difv+=1;
											if(difv > somv)
											{
												aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(0).getDescricao().toString();
												somv=1;
												difv=0;
											}
										}
									}
									
									maisconsumidapizzsabor1 = aux;
									
								}
								else if(pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores() != null)
								{
									// verifica sabor
									
									if(classica > doce && classica > vegetariana) 
									{
										if(aux == "")
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString();
											somc+=1;
										}
										else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString())
										{
											somc+=1;
										}
										else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString())
										{
											difc+=1;
											if(difc > somc)
											{
												aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString();
												somc=1;
												difc=0;
											}
										}
									}
									else if(classica < doce && doce > vegetariana)
									{
										if(aux == "")
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString();
											somd+=1;
										}
										else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString())
										{
											somd+=1;
										}
										else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString())
										{
											difd+=1;
											if(difd > somd)
											{
												aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString();
												somd=1;
												difd=0;
											}
										}
									}
									else if(classica < vegetariana && doce < vegetariana)
									{
										
										if(aux == "")
										{
											aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString();
											somv+=1;
										}
										else if(aux == pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString())
										{
											somv+=1;
										}
										else if(aux != pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString())
										{
											difv+=1;
											if(difv > somv)
											{
												aux = pedidoReadFor.getPedidoPizzas().get(p).getPizzasSabores().get(1).getDescricao().toString();
												somv=1;
												difv=0;
											}
										}
									}
									
									maisconsumidapizzsabor2 = aux;
								}
								
								
								
							}
						}
					}
				}
				
				
				
				
				// depois de ter corrido em todos os clientes e seus respectivos pedidos de pizza
				// para somar pontuação das 3 categorias, realizado a estrutura abaixo para poder exibir a categoria mais consumida
				//  e também o sabor pizza 
				

				if(classica > doce && classica > vegetariana)
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "A categoria da pizza mais consumido é a 'Classica' \n Os sabores mais usados em : \n Pizza Única: "+ maisconsumidapizzaunica+ "\n  De 2 sabores: "+ maisconsumidapizzsabor1 + " / "+ maisconsumidapizzsabor2);
					return;
				}
				else if(doce > classica && doce > vegetariana)
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "A categoria da pizza mais consumido é a 'Doce' \n Os sabores mais usados em : \n Pizza Única: "+ maisconsumidapizzaunica+ "\n  De 2 sabores: "+ maisconsumidapizzsabor1 + " / "+ maisconsumidapizzsabor2);
					return;
				}
				else if(classica < vegetariana && doce < vegetariana)
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "A categoria da pizza mais consumido é a 'Vegetariana' \n Os sabores mais usados em : \n Pizza Única: "+ maisconsumidapizzaunica+ "\n  De 2 sabores: "+ maisconsumidapizzsabor1 + " / "+ maisconsumidapizzsabor2);
					return;
				}
				else if(classica == vegetariana && classica == doce) 
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "As três categoria da pizza são as mais consumidas( 'Classica', 'Doce' e 'Vegetariana') \n Pizza Única: "+ maisconsumidapizzaunica+ "\n  De 2 sabores: "+ maisconsumidapizzsabor1 + " / "+ maisconsumidapizzsabor2 );
					return;
				}
				else if(classica == vegetariana) 
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "As duas categoria da pizza são as mais consumidas( 'Classica' e 'Vegetariana') \n Pizza Única: "+ maisconsumidapizzaunica+ "\n  De 2 sabores: "+ maisconsumidapizzsabor1 + " / "+ maisconsumidapizzsabor2 );
					return;
				}
				else if(classica == doce) 
				{ 
					JOptionPane.showMessageDialog(botaoMaisConsumido, "As duas categoria da pizza são as mais consumidas( 'Classica' e 'Doce') \n Pizza Única: "+ maisconsumidapizzaunica+ "\n  De 2 sabores: "+ maisconsumidapizzsabor1 + " / "+ maisconsumidapizzsabor2 );
					return;
				}
				else if(vegetariana == doce) 
				{
					JOptionPane.showMessageDialog(botaoMaisConsumido, "As duas categoria da pizza são as mais consumidas( 'Vegetariana' e 'Doce')");
					return;
				}
				
				
				*/
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog( botaoMaisConsumido, ex.getLocalizedMessage());
			}
			
		}
		
		
	}
	
}
