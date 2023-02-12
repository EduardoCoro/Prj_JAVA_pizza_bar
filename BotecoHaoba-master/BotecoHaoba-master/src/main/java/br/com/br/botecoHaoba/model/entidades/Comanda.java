package br.com.br.botecoHaoba.model.entidades;

import java.util.ArrayList;
import java.util.List;


public class Comanda {


   private Integer                 mesa;

   private String                  clienteReferencia;

   private Integer                 quantidadePessoasMesa;

   private final List<ItemComanda> itensComanda  = new ArrayList<>();

   private double                  valorTotal    = 0;

   private double                  valorComissao = 0;


   public Comanda( Integer mesa, Integer quantidadePessoasMesa, String clienteReferencia ) {

      super();
      this.mesa                  = mesa;
      this.clienteReferencia     = clienteReferencia;
      this.quantidadePessoasMesa = quantidadePessoasMesa;
   }


   public int getMesa() {

      return mesa;
   }

   public int getQuantidadePessoasMesa() {

      return quantidadePessoasMesa;
   }


   public String getClienteReferencia() {

      return clienteReferencia;
   }


   public void addItem( ItemComanda item ) {

      itensComanda.add( item );
   }

   public int getQuantidadeItensConsumidos() {

      return itensComanda.size();
   }

   public List<ItemComanda> getItensComanda() {

      return new ArrayList<ItemComanda>( itensComanda );
   }

   public void removeItem( ItemComanda item ) {

      itensComanda.remove( item );
   }


   public double getValorComissao() {

      return valorComissao;
   }


   public double getValorTotal() {

      return valorTotal;
   }

   public void setValorComissao( double valorComissao ) {

      this.valorComissao = valorComissao;
   }

   public void setValorTotal( double valorTotal ) {

      this.valorTotal = valorTotal;
   }


   @Override
   public int hashCode() {

      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( mesa == null ) ? 0 : mesa.hashCode() );
      return result;
   }


   @Override
   public boolean equals( Object obj ) {

      if ( this == obj ) return true;
      if ( obj == null ) return false;
      if ( getClass() != obj.getClass() ) return false;
      Comanda other = (Comanda) obj;
      if ( mesa == null ) {
         if ( other.mesa != null ) return false;
      }
      else if ( !mesa.equals( other.mesa ) ) return false;
      return true;
   }


   @Override
   public String toString() {

      return "Comanda [mesa=" + mesa + ", clienteReferencia=" + clienteReferencia + ", quantidadePessoasMesa=" + quantidadePessoasMesa + ", itensComanda=" + itensComanda + ", valorTotal=" + valorTotal + ", valorComissao=" + valorComissao + "]";
   }

}
