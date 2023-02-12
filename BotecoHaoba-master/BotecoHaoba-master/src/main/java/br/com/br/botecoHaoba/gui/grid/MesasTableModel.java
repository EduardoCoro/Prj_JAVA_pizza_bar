package br.com.br.botecoHaoba.gui.grid;

import java.text.NumberFormat;

import javax.swing.table.AbstractTableModel;

import br.com.br.botecoHaoba.model.entidades.Comanda;
import br.com.br.botecoHaoba.model.servico.BotecoService;

public class MesasTableModel extends AbstractTableModel {


   private BotecoService     facade;

   private MesasColumnModel columnModel;

   public MesasTableModel( BotecoService facade, MesasColumnModel columnModel ) {

      this.facade      = facade;
      this.columnModel = columnModel;

   }

   @Override
   public int getRowCount() {

      return facade.getComandas().size();
   }

   @Override
   public int getColumnCount() {

      return columnModel.getColumnCount();
   }


   @Override
   public Object getValueAt( int rowIndex, int columnIndex ) {

      Comanda comanda = facade.getComandas().get( rowIndex );

      if ( columnIndex == 0 ) {
         return comanda.getMesa();

      }

      if ( columnIndex == 1 ) {
         return comanda.getClienteReferencia();
      }

      if ( columnIndex == 2 ) {
         return comanda.getQuantidadePessoasMesa();
      }

      if ( columnIndex == 3 ) {
         return comanda.getQuantidadeItensConsumidos();
      }

      if ( columnIndex == 4 ) {
         return NumberFormat.getCurrencyInstance().format( comanda.getValorTotal() );
      }

      if ( columnIndex == 5 ) {
         return NumberFormat.getCurrencyInstance().format( comanda.getValorComissao() );
      }

      return null;
   }
}
