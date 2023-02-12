package br.com.br.botecoHaoba.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.br.botecoHaoba.model.entidades.Cardapio;
import br.com.br.botecoHaoba.model.entidades.ItemComanda;

public class DlgAdicionarItem extends JDialog {

   private final JButton             botaoOk       = new JButton( "OK" );
   private final JButton             botaoCancelar = new JButton( "Cancelar" );


   private final JComboBox<Cardapio> comboCardapio = new JComboBox<Cardapio>();
   private final JTextField          textQuantidade;

   private boolean             pressionouOk  = false;

   public ItemComanda          itemComanda;


   public DlgAdicionarItem() {

      setSize( 500, 300 );
      setLayout( null );
      setModal( true );

      botaoOk.addActionListener( new ActionOk() );
      botaoCancelar.addActionListener( new ActionCancelar() );

      botaoOk.setSize( 100, 30 );
      botaoOk.setLocation( 15, 210 );

      botaoCancelar.setSize( 100, 30 );
      botaoCancelar.setLocation( 150, 210 );

      comboCardapio.setLocation( 100, 10 );
      comboCardapio.setSize( 250, 25 );
      add( criaLabel( "Item", 10 ) );


      textQuantidade = criaTextField( "Quantidade", 40, 5 );


      add( comboCardapio );
      add( textQuantidade );

      add( botaoOk );
      add( botaoCancelar );


      for ( Cardapio cardapio : Cardapio.values() ) {
         comboCardapio.addItem( cardapio );
      }

   }


   private JTextField criaTextField( String string, int linha, int tamanho ) {

      JTextField txt = new JTextField( "" );
      txt.setLocation( 100, linha );
      txt.setSize( tamanho * 10, 25 );

      add( criaLabel( string, linha ) );

      return txt;
   }


   private JLabel criaLabel( String string, int linha ) {

      JLabel label = new JLabel( string );
      label.setLocation( 10, linha );
      label.setSize( 100, 25 );

      return label;
   }


   public ItemComanda getItemComanda() {

      return itemComanda;
   }

   public boolean pressionouOk() {

      return pressionouOk;
   }

   private class ActionOk implements ActionListener {

      @Override
      public void actionPerformed( ActionEvent e ) {


         if ( testaVazio( textQuantidade, "quantidade" ) ) {
            return;
         }
         if ( !testaNaoInteiro( textQuantidade, "quantidade" ) ) {
            return;
         }

         pressionouOk = true;

         ItemComanda comanda = new ItemComanda( Integer.parseInt( textQuantidade.getText().trim() ),
                                                (Cardapio) comboCardapio.getSelectedItem() );

         DlgAdicionarItem.this.itemComanda = comanda;

         DlgAdicionarItem.this.setVisible( false );
      }

   }

   private boolean testaVazio( JTextField field, String label ) {

      if ( field.getText().trim().length() == 0 ) {
         JOptionPane.showMessageDialog( botaoOk, "Necessário preencher " + label );
         return true;
      }
      return false;
   }


   private boolean testaNaoInteiro( JTextField field, String label ) {

      if ( isInteger( field.getText() ) ) {
         return true;
      }
      JOptionPane.showMessageDialog( botaoOk, label + " precisa ser um numero inteiro" );
      return false;

   }

   private boolean isInteger( String text ) {

      text = text.trim();
      try {
         Integer.parseInt( text );
         return true;
      }
      catch ( Throwable ex ) {
         return false;
      }

   }

   private class ActionCancelar implements ActionListener {

      @Override
      public void actionPerformed( ActionEvent e ) {

         DlgAdicionarItem.this.setVisible( false );

      }

   }


}
