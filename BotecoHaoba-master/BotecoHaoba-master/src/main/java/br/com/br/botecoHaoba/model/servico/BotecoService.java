package br.com.br.botecoHaoba.model.servico;

import java.util.ArrayList;
import java.util.List;

import br.com.br.botecoHaoba.model.entidades.Comanda;

public class BotecoService {


   private List<Comanda> comandas = new ArrayList<Comanda>();


   public void addComanda( Comanda comanda ) {

      calculaValorTotal( comanda );
      calculaValorComissao( comanda );
      this.comandas.add( comanda );
   }

   public List<Comanda> getComandas() {

      return new ArrayList<>( comandas );
   }

   public void remove( Comanda comanda ) {

      comandas.remove( comanda );
   }


   public void calculaValorTotal( Comanda comanda ) {


      comanda.setValorTotal( 0 );
   }

   public void calculaValorComissao( Comanda comanda ) {


      comanda.setValorTotal( 0 );
   }

   public int getQuantidadeItensConsumo() {

      return 0;
   }

   public int getQuantidadeClientes() {

      return 0;
   }

   public int getQuantidadeMesas() {

      return 0;
   }


}
