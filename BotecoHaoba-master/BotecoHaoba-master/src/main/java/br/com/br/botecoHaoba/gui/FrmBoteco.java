package br.com.br.botecoHaoba.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import br.com.br.botecoHaoba.gui.grid.MesasColumnModel;
import br.com.br.botecoHaoba.gui.grid.MesasTableModel;
import br.com.br.botecoHaoba.model.entidades.Comanda;
import br.com.br.botecoHaoba.model.entidades.ItemComanda;
import br.com.br.botecoHaoba.model.servico.BotecoService;

public class FrmBoteco extends JFrame {


   private final BotecoService    service               = new BotecoService();


   private final MesasColumnModel columnModel           = new MesasColumnModel();
   private final MesasTableModel  tableModel            = new MesasTableModel( service, columnModel );
   private final JTable           gridComanda           = new JTable( tableModel, columnModel, null );

   private final JScrollPane      pane                  = new JScrollPane( gridComanda );


   private final JButton          botaoAdicionarComanda = new JButton( "Adicionar comanda" );
   private final JButton          botaoRemoverComanda   = new JButton( "Remover comanda" );
   private final JButton          botaoAdicionarItem    = new JButton( "Adicionar item" );

   public FrmBoteco() {

      setTitle( "Boteco Haoba!" );
      setLayout( null );
      setSize( 800, 600 );
      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      setResizable( false );

      pane.setSize( 770, 500 );
      pane.setLocation( 0, 0 );

      adicionaColunasGrid();

      gridComanda.getTableHeader().setReorderingAllowed( false );


      add( pane );


      configuraBotoes();

      add( botaoAdicionarComanda );
      add( botaoRemoverComanda );
      add( botaoAdicionarItem );

      atualizaTitulo();
   }


   private void atualizaTitulo() {

      setTitle( "Boteco Haoba! ## Mesas: " + service.getQuantidadeMesas() + " ## Clientes: " + service.getQuantidadeClientes() + " ## Itens sendo consumidos " + service.getQuantidadeItensConsumo() );
   }

   private void configuraBotoes() {

      botaoAdicionarComanda.setSize( 140, 30 );
      botaoAdicionarComanda.setLocation( 1, 520 );
      botaoAdicionarComanda.addActionListener( new ActionAdicionarComanda() );

      botaoRemoverComanda.setSize( 140, 30 );
      botaoRemoverComanda.setLocation( 140, 520 );
      botaoRemoverComanda.addActionListener( new ActionRemoverComanda() );

      botaoAdicionarItem.setSize( 140, 30 );
      botaoAdicionarItem.setLocation( 630, 520 );
      botaoAdicionarItem.addActionListener( new ActionAdicionarItem() );
   }


   private void adicionaColunasGrid() {

      columnModel.addColumn( criaColuna( "Mesa", 40 ) );
      columnModel.addColumn( criaColuna( "Cliente", 260 ) );
      columnModel.addColumn( criaColuna( "Pessoas na mesa", 120 ) );
      columnModel.addColumn( criaColuna( "Itens consumidos", 110 ) );
      columnModel.addColumn( criaColuna( "Valor total", 110 ) );
      columnModel.addColumn( criaColuna( "Comissão garçom", 120 ) );
   }


   private TableColumn criaColuna( String titulo, int tamanho ) {

      TableColumn column = new TableColumn();
      column.setHeaderValue( titulo );
      column.setResizable( false );
      column.setWidth( tamanho );
      column.setMaxWidth( tamanho );
      column.setMinWidth( tamanho );
      column.setModelIndex( columnModel.getColumnCount() );
      return column;
   }


   private class ActionAdicionarComanda implements ActionListener {

      @Override
      public void actionPerformed( ActionEvent e ) {

         try {
            DlgAdicionarComanda dialog = new DlgAdicionarComanda( service );
            dialog.setVisible( true );

            if ( !dialog.pressionouOk() ) {
               return;
            }

            Comanda comanda = dialog.getComanda();

            service.addComanda( comanda );

            service.calculaValorTotal( comanda );
            service.calculaValorComissao( comanda );

            tableModel.fireTableDataChanged();

            atualizaTitulo();
         }
         catch ( Throwable ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog( botaoRemoverComanda, ex.getLocalizedMessage() );
         }
      }

   }

   private class ActionRemoverComanda implements ActionListener {

      @Override
      public void actionPerformed( ActionEvent e ) {

         try {
            int linhaSelecionada = gridComanda.getSelectedRow();

            Comanda comanda = service.getComandas().get( linhaSelecionada );


            service.remove( comanda );
            tableModel.fireTableDataChanged();

            atualizaTitulo();
         }
         catch ( Throwable ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog( botaoRemoverComanda, ex.getLocalizedMessage() );
         }
      }

   }

   private class ActionAdicionarItem implements ActionListener {

      @Override
      public void actionPerformed( ActionEvent e ) {

         try {
            int linhaSelecionada = gridComanda.getSelectedRow();

            Comanda comanda = service.getComandas().get( linhaSelecionada );


            DlgAdicionarItem dialog = new DlgAdicionarItem();
            dialog.setVisible( true );

            if ( !dialog.pressionouOk() ) {
               return;
            }

            ItemComanda itemComanda = dialog.itemComanda;

            comanda.addItem( itemComanda );

            service.calculaValorTotal( comanda );
            service.calculaValorComissao( comanda );

            tableModel.fireTableDataChanged();

            atualizaTitulo();
         }
         catch ( Throwable ex ) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog( botaoRemoverComanda, ex.getLocalizedMessage() );
         }

      }

   }

}
