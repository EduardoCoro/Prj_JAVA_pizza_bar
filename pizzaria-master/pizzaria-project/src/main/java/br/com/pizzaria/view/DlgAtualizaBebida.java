package br.com.pizzaria.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.pizzaria.model.entidades.cardapio.Bebida;
//import br.com.pizzaria.model.entidades.pedido.Pedido;
import br.com.pizzaria.model.entidades.pedido.PedidoBebida;

public class DlgAtualizaBebida extends JDialog {
	//private Pedido pedido = new Pedido();
	
	private PedidoBebida pedidoBebida;

	private final JButton botaoOk = new JButton("OK");
	private final JButton botaoCancelar = new JButton("Cancelar");
	private final JButton botaoMostraSaldo = new JButton("Mostrar Saldo");

	private final JComboBox<Bebida> comboCardapio = new JComboBox<>();
	private final JTextField textQuantidade;

	private boolean pressionouOk = false;

	public DlgAtualizaBebida() {
		setSize(500, 300);
		setLayout(null);
		setModal(true);
		
		setTitle("Atualizar saldo em bebida");

		botaoOk.addActionListener(new ActionOk());
		botaoCancelar.addActionListener(new ActionCancelar());
		botaoMostraSaldo.addActionListener(new ActionMostraSaldo());

		botaoOk.setSize(100, 30);
		botaoOk.setLocation(15, 210);

		botaoCancelar.setSize(100, 30);
		botaoCancelar.setLocation(150, 210);
		
		botaoMostraSaldo.setSize(150,30);
		botaoMostraSaldo.setLocation(50,100);

		comboCardapio.setSize(250, 25);
		comboCardapio.setLocation(150, 10);
		
		add(criaLabel("Selecione a bebida: ", 10));

		textQuantidade = criaTextField("Atualizar saldo: ", 40, 5);

		add(comboCardapio);
		add(textQuantidade);
		add(botaoMostraSaldo);
		add(botaoOk);
		add(botaoCancelar);

		for (Bebida bebida : Bebida.values()) {
			comboCardapio.addItem(bebida);
		}
	}

	private JTextField criaTextField(String string, int linha, int tamanho) {

		JTextField txt = new JTextField("");
		txt.setLocation(100, linha);
		txt.setSize(tamanho * 10, 25);

		add(criaLabel(string, linha));

		return txt;
	}

	private JLabel criaLabel(String string, int linha) {

		JLabel label = new JLabel(string);
		label.setLocation(10, linha);
		label.setSize(130, 25);

		return label;
	}

	public PedidoBebida getPedidoBebida() {

		return pedidoBebida;
	}
	

	public boolean pressionouOk() {

		return pressionouOk;
	}
	

	
	
	private class ActionMostraSaldo implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
			
			try {
				
				// criação do tipo Pedido Bebida para pegar o tipo da classe Bebida e suas características				
				PedidoBebida pedidoBebida = new PedidoBebida ((Bebida) comboCardapio.getSelectedItem(), 0);
							
				// Mostrar ao usuário o valor correspondido
				JOptionPane.showMessageDialog( botaoMostraSaldo, "Verifica Saldo: \n\n O saldo atual da bebida "+ pedidoBebida.getBebida().getDescricao().toString() + " é de: " + pedidoBebida.getBebida().getSaldo() + " unidade(s) \n\n\n Obs: Utiliza o campo Bebida , para selecionar a bebida!");
				return;
					
					
					
			}catch(Exception ex) 
		    {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoMostraSaldo , ex.getLocalizedMessage());	
				
			}
			
			
		}
		
		
		
	}

	private class ActionOk implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
		 try {

			 // testa se o campo está vazio
			if (testaVazio(textQuantidade, "quantidade")) {
				return;
			}
			if (!testaNaoInteiro(textQuantidade, "quantidade")) {
				return;
			}
			
			// Tratamento de uma bebida com a quantidade menor ou igual a zero
			
			if( Integer.parseInt(textQuantidade.getText().trim()) <= 0) {
			    JOptionPane.showMessageDialog(botaoOk, "Não pode ter quantidade de 0 ou menor que 0");
			    return;
			}

			pressionouOk = true;

			PedidoBebida pedidoBebida = new PedidoBebida ((Bebida) comboCardapio.getSelectedItem(), 0);
			
			// usuario terá que decidir e selecionar um dos 3 botoes 
			int sim = JOptionPane.showConfirmDialog(botaoOk, "Você tem certeza que quer alterar o valor do Saldo atual: "+ pedidoBebida.getBebida().getSaldo() + " unidade(s) \n\n de " + pedidoBebida.getBebida().getDescricao().toString() + " para o valor de: "+ Integer.parseInt(textQuantidade.getText().trim()) + " unidade(s) ?");
			
			if(sim == 1 )
			{
				// Usuario apertou o botao, aperta não
			  return;
			  
			}else if ( sim == 0)
			{
				
				// quando aperta sim
				// Atualizando o Saldo atual
				pedidoBebida.getBebida().setSaldo(Integer.parseInt(textQuantidade.getText().trim()));
				
				JOptionPane.showMessageDialog(botaoOk, "Valor Atualizado!");
				
				
			}
			else {
				// botao cancelar
				
				JOptionPane.showMessageDialog(botaoOk, "Alteração cancelada!");

			}
			
			
			
			//DlgAdicionarBebida.this.pedidoBebida = pedidoBebida;

			//DlgAdicionarBebida.this.setVisible(false);
			
		  }catch(Exception ex) 
		   {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(botaoOk , ex.getLocalizedMessage());	
		   }
	   }

	}

	private boolean testaVazio(JTextField field, String label) {

		if (field.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(botaoOk, "Necessário preencher " + label);
			return true;
		}
		return false;
	}

	private boolean testaNaoInteiro(JTextField field, String label) {

		if (isInteger(field.getText())) {
			return true;
		}
		JOptionPane.showMessageDialog(botaoOk, label + " precisa ser um numero inteiro");
		return false;

	}

	private boolean isInteger(String text) {

		text = text.trim();
		try {
			Integer.parseInt(text);
			return true;
		} catch (Throwable ex) {
			return false;
		}

	}

	private class ActionCancelar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			DlgAtualizaBebida.this.setVisible(false);
		}

	}
}
