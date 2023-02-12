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
import br.com.pizzaria.model.entidades.pedido.Pedido;
import br.com.pizzaria.model.entidades.pedido.PedidoBebida;

public class DlgAdicionarBebida extends JDialog {
	private PedidoBebida pedidoBebida;

	private final JButton botaoOk = new JButton("OK");
	private final JButton botaoCancelar = new JButton("Cancelar");

	private final JComboBox<Bebida> comboCardapio = new JComboBox<>();
	private final JTextField textQuantidade;

	private boolean pressionouOk = false;

	public DlgAdicionarBebida() {
		setSize(500, 300);
		setLayout(null);
		setModal(true);
		
		setTitle("Adicionar bebida");

		botaoOk.addActionListener(new ActionOk());
		botaoCancelar.addActionListener(new ActionCancelar());

		botaoOk.setSize(100, 30);
		botaoOk.setLocation(15, 210);

		botaoCancelar.setSize(100, 30);
		botaoCancelar.setLocation(150, 210);

		comboCardapio.setLocation(100, 10);
		comboCardapio.setSize(250, 25);
		add(criaLabel("Bebida", 10));

		textQuantidade = criaTextField("Quantidade", 40, 5);

		add(comboCardapio);
		add(textQuantidade);

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
		label.setSize(100, 25);

		return label;
	}

	public PedidoBebida getPedidoBebida() {

		return pedidoBebida;
	}
	
	
	private int VerificaSaldoBebidas(PedidoBebida pedido){
		
		
					
					
		
					// buscando a bebida e adicionando o saldo atual na variavel
					int saldo = pedido.getBebida().getSaldo();
					
					// calculando o saldo atual
					saldo = saldo - pedido.getQuantidade();
					
					// se o saldo  estiver  0 ou negativo no ato de adicionar o pedido executará o escopo
					if(saldo < 0)
					{
						return saldo;
					}
					else
					{
						// se o saldo continuar positivo
						// atualiza o valor do saldo
						JOptionPane.showMessageDialog( botaoOk, "O saldo atual da bebida "+ pedido.getBebida().getDescricao().toString() + " é de : " + saldo);
						return saldo;
					}
					
				
		
	}

	public boolean pressionouOk() {

		return pressionouOk;
	}

	private class ActionOk implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
		  try {
				
				
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

				PedidoBebida pedidoBebida = new PedidoBebida ((Bebida) comboCardapio.getSelectedItem(), Integer.parseInt(textQuantidade.getText().trim()));

				// chamar a função verifica saldo de pedidoBebida;
				int saldo = VerificaSaldoBebidas(pedidoBebida);
				if(saldo < 0)
				{
					// Mensagem informando que o pedido não será adicionado
					JOptionPane.showMessageDialog( botaoOk, "Naõ é possível adicionar o pedido, pois a quantidade pedida, de"+ pedidoBebida.getQuantidade() +" , supera o saldo atual da bebida que é de: "+ pedidoBebida.getBebida().getSaldo());
					return;
				}
				
				
				
				
				DlgAdicionarBebida.this.pedidoBebida = pedidoBebida;

				DlgAdicionarBebida.this.setVisible(false);
				
				
				
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

			DlgAdicionarBebida.this.setVisible(false);
		}

	}
}
