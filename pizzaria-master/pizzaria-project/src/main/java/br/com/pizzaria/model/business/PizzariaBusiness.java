package br.com.pizzaria.model.business;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.pizzaria.model.entidades.cardapio.Bebida;
import br.com.pizzaria.model.entidades.cliente.Cliente;
import br.com.pizzaria.model.entidades.pedido.Pedido;
import br.com.pizzaria.model.entidades.pedido.PedidoBebida;
import br.com.pizzaria.model.entidades.pedido.PedidoCliente;
import br.com.pizzaria.model.entidades.pedido.PedidoPizza;
import br.com.pizzaria.model.entidades.pedido.PedidoTelaPrincipal;

public class PizzariaBusiness {

	private List<PedidoCliente> clientes = new ArrayList<>();
	private List<PedidoTelaPrincipal> pedidosTela = new ArrayList<>();

	public void adicionarPedido(Cliente cliente, Pedido pedido) {

		boolean achou = false;
		for (PedidoCliente pedidoCliente : clientes) {
			if (cliente.getNome().equalsIgnoreCase(pedidoCliente.getCliente().getNome())) {
				achou = true;
				aplicaValorTotalPedido(pedido);
				aplicaComboPromocao(pedido);
				aplicaPromocaoBebidas(pedido);
				removeSaldoBebidas(pedido);

				pedidoCliente.adicionarPedido(pedido);
			}
		}

		if (!achou) {
			aplicaValorTotalPedido(pedido);
			aplicaDescontoPrimeiraCompra(pedido);
			aplicaComboPromocao(pedido);
			aplicaPromocaoBebidas(pedido);
			removeSaldoBebidas(pedido);

			PedidoCliente pedidoCliente = new PedidoCliente();
			pedidoCliente.setCliente(cliente);
			pedidoCliente.adicionarPedido(pedido);

			clientes.add(pedidoCliente);
		}
	}

	public void removePedidoPizza(Pedido pedido, PedidoPizza pedidoPizza) {
		pedido.removePizzaPedido(pedidoPizza);
	}

	public void removePedidoBebida(Pedido pedido, PedidoBebida pedidoBebida) {
		pedido.removeBebida(pedidoBebida);
		// ação que remove o pedido quando o usuário solicitar
	}
	
	

	private void aplicaPromocaoBebidas(Pedido pedido) {

		// Utilizado o laço de repetiçao para percorrer a lista de Pedido de Bebidas
		for(int i=0; i < pedido.getPedidoBebidas().size(); i++)
		{
			
			// se o item e a quantidade estiver de acordo com a promoção, executará o escopo
			if( pedido.getPedidoBebidas().get(i).getBebida().getDescricao().toString() == "CERVEJA 1 LITRO" && pedido.getPedidoBebidas().get(i).getQuantidade() == 6 )
			{
				// iniciação do tipo bebida
				Bebida aguaBebida = null;
				
				// iniciação do tipo Pedido Bebida 
				PedidoBebida pedidoBebida = new PedidoBebida (aguaBebida.AGUA, 1);
				
				
				//verifica saldo de agua
				if(pedidoBebida.getBebida().getSaldo() <= 0  )
				{
					// Se o saldo for 0 ou negativo de bebida, o escopo será executado
					
					double valor = pedido.getValorTotal();
					
					
					// realizando o desconto do valor de agua
					pedido.setValorTotal( pedido.getValorTotal()  - pedidoBebida.getBebida().getPreco() );
					
					// Mensagem ao usuário sobre o que procedeu.
					JOptionPane.showMessageDialog(null, "Promoção Bebidas \n\n A bebida 'Agua' não possui saldo para brinde, valor será automaticamente descontado no valor do pedido \n\n Valor: R$"+ valor + "\n\n Valor descontado: R$" + pedido.getValorTotal());

					

				}else
				{
					// se o saldo estiver positivo este escopo será executado
					
					// adicionar a agua no Pedido Bebida do cliente
					pedido.getPedidoBebidas().add(pedidoBebida);
					
					// Mensagem ao usuário sobre o que procedeu.
					JOptionPane.showMessageDialog(null,"Promoção Bebida \n\n A bebiba de brinde - 'Agua' \n\n Foi inserida no pedido!");
				}
				
				
				 
			}
			{
				
			}
		}
		
	}

	private void removeSaldoBebidas(Pedido pedido) {

		
		// Usado o laço de repetição para buscar na lista de pedidos de bebidas
		for(int i=0; i < pedido.getPedidoBebidas().size(); i++)
		{
			
			//System.out.println("Saldo atual - "+ pedido.getPedidoBebidas().get(i).getBebida().getSaldo());
			// buscando a bebida e adicionando o saldo atual na variavel
			int saldo = pedido.getPedidoBebidas().get(i).getBebida().getSaldo();
			
			// calculando o saldo atual
			saldo = saldo - pedido.getPedidoBebidas().get(i).getQuantidade();
			
			// se o saldo  estiver  0 ou negativo no ato de adicionar o pedido executará o escopo
			if(saldo < 0)
			{
				// Mensagem informando que o pedido não será adicionado
				JOptionPane.showMessageDialog( null, "Naõ é possível adicionar o pedido, pois a quantidade pedida supera o saldo atual da bebida");
				return;
				
			}
			else {
				// se o saldo continuar positivo
				// atualiza o valor do saldo
				pedido.getPedidoBebidas().get(i).getBebida().setSaldo(saldo);
				//System.out.println("Saldo atualizado - "+ pedido.getPedidoBebidas().get(i).getBebida().getSaldo());
			}
			
		}
		
		
	}
	
	public List<PedidoCliente> getPedidoCliente() {
		
		return clientes;
	}

	private void aplicaComboPromocao(Pedido pedido) {
			// inicia com a variavel verifica para realizar a somatoria de itens do combo
			
			int verifica =0;
			
			// utilizado o comando na linha  abaixo para teste
			//System.out.println("verrifica valor : " + pedido.getValorTotal());
			
			
			// verifica os itens no pedido de Pizza se estão de acordo com o combo de 25 %
			List<PedidoPizza> itenspizza = pedido.getPedidoPizzas();
			
			// percorre a lista de Pedidos para buscar as suas categoria
			for(int i=0; i < itenspizza.size(); i++) 
			{
				PedidoPizza pizzaReadFor = itenspizza.get(i);
				
				// estrutura de decisao para saber se no item é uma pizza unica ou de 2 sabores
				if(pizzaReadFor.getPizzaUnica() != null)
				{
					
					// Estrutura lógica para identificar o tipo de pizza de seu tamanho e categoria
					//System.out.println("passou 1"); utilizado para teste
					
					if(pizzaReadFor.getTamanho().toString().trim() == "GRANDE" && ( pizzaReadFor.getPizzaUnica().getCategoria().toString() == "CLASSICA" || pizzaReadFor.getPizzaUnica().getCategoria().toString() == "VEGETARIANA" ) )
					{
						verifica +=1;
					}
					if(pizzaReadFor.getTamanho().toString().trim() == "BROTO" && pizzaReadFor.getPizzaUnica().getCategoria().toString() == "DOCE")
					{
						verifica +=1;
					}
				}
				else 
				{
					// Estrutura lógica para identificar o tipo de pizza de seu tamanho e categoria
					//System.out.println("passou else"); utilizado para testes
					
					if(pizzaReadFor.getTamanho().toString().trim() == "GRANDE" && ( pizzaReadFor.getPizzasSabores().get(0).getCategoria().toString() == "CLASSICA" || pizzaReadFor.getPizzasSabores().get(0).getCategoria().toString() == "VEGETARIANA" ) || ( pizzaReadFor.getPizzasSabores().get(1).getCategoria().toString() == "CLASSICA" || pizzaReadFor.getPizzasSabores().get(1).getCategoria().toString() == "VEGETARIANA" ) )
					{
						verifica +=1;
					}
					if(pizzaReadFor.getTamanho().toString().trim() == "BROTO" && pizzaReadFor.getPizzasSabores().get(0).getCategoria().toString() == "DOCE" || pizzaReadFor.getPizzasSabores().get(1).getCategoria().toString() == "DOCE")
					{
						verifica +=1;
					}
				}
				
				
				
			}
			
			//etapa de verificar os pedidos de bebida
			
			List<PedidoBebida> itensBebida = pedido.getPedidoBebidas();
			
			// será necessário percorrer a lista de bebidas para buscar o produto correspondente ao combo
			for(int i=0; i < itensBebida.size(); i++) 
			{
				PedidoBebida bebidaReadFor = itensBebida.get(i);
				
				// se for verdadeira a estrutura de decissao, o item correspondente ao combo foi encontrado
				if(bebidaReadFor.getBebida().getDescricao().toString() == "REFIGERANTE LATA")
				{
					verifica += 1;
					//System.out.println("passou bebida");
				}
			}
			
			 //verifica é de 3 ou 4 por serem os itens necessários do combo, sendo 3 para pizza unica e 4 para pizza de 2 sabores.
			if(verifica >=3 && verifica <=4 )
			{
				//inicializa a variavel dentro do escopo pois só será utilizada aqui
				double valor = 0 ;
				valor = pedido.getValorTotal();
				valor = valor - ( valor * 0.25 );
				
				// passagem de valor, atualizando o valor do pedido adicionado, conforme de acordo ao combo promocional
				pedido.setValorTotal(valor);
				//System.out.println("passou "+ valor);
			}
			
	            
	}

	private void aplicaDescontoPrimeiraCompra(Pedido pedido) {
		
		// Essa função só será chamada se for o primeiro pedido
		// utilizado a variavel para realizar o calculo
	    double valor  = pedido.getValorTotal();
	    valor = valor - (valor *  0.1 );
	    
	    pedido.setValorTotal(valor);

	}

	public double getValorTotalPedido(Pedido pedido) {
		// na variavel foi inserido o valor total do pedido
	    double valorTotal = pedido.getValorTotal();
	    
	    //calcular pizza
	    for(int i=0; i < pedido.getPedidoPizzas().size(); i++) {
	        PedidoPizza pizzaReadFor = pedido.getPedidoPizzas().get(i);
	        
	        valorTotal += pizzaReadFor.getValorTotal();
	        
	    }
	    
	    
	    // Calcular bebida
	    for( int k=0; k < pedido.getPedidoBebidas().size(); k++) {
	        PedidoBebida bebidaReadFor = pedido.getPedidoBebidas().get(k);
	        valorTotal += bebidaReadFor.getBebida().getPreco() * bebidaReadFor.getQuantidade();
	    }
	    
	    
	    return valorTotal;
	}

	private void aplicaValorTotalPedido(Pedido pedido) {
		pedido.setValorTotal(getValorTotalPedido(pedido));
	}
	

	private void aplicaValorTotalPedidoTelaPrincipal(PedidoTelaPrincipal pedidoTelaPrincipal, double valor) {
		pedidoTelaPrincipal.setValorTotal(valor +  pedidoTelaPrincipal.getValorTotal() );
		// será adicionado o valor total do pedido na tela inicial e incrementando os pedidos do mesmo dia
	}
	
	
	// função para pegar e buscar o PedidosdaTelaPrincipal selecionado
	public List<PedidoTelaPrincipal> getPedidosTelaPrincipal() {
		pedidosTela.clear();

		for (PedidoCliente cliente : clientes) {
			List<Pedido> pedidosCliente = cliente.getPedidos();
		
			for (Pedido pedidoCliente : pedidosCliente) {
				
				boolean achouData = false;
				PedidoTelaPrincipal pedidoTelaData = null;
				
				for (PedidoTelaPrincipal pedidoTela : pedidosTela) {
					if (pedidoCliente.getData().equals(pedidoTela.getData())) {
						achouData = true;
						pedidoTelaData = pedidoTela;
					}
				}
				
				PedidoTelaPrincipal pedidoTelaPrincipal = null;
				
				if (achouData) {
					pedidoTelaPrincipal = pedidoTelaData;
				} else {
					pedidoTelaPrincipal = new PedidoTelaPrincipal();
					pedidosTela.add(pedidoTelaPrincipal);
					pedidoTelaPrincipal.setData(pedidoCliente.getData());
				}
				
				pedidoTelaPrincipal.setQuantidadePedidos(pedidoTelaPrincipal.getQuantidadePedidos() + pedidoCliente.getQuantidadePedidos());
				aplicaValorTotalPedidoTelaPrincipal(pedidoTelaPrincipal, pedidoCliente.getValorTotal());
			}
		}

		return new ArrayList<>(pedidosTela);
	}
	
	
}
