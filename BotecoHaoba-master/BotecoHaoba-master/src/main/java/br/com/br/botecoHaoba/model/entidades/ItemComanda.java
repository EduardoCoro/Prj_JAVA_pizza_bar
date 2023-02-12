package br.com.br.botecoHaoba.model.entidades;

public class ItemComanda {

   private final Integer  quantidade;
   private final Cardapio item;

   public ItemComanda( Integer quantidade, Cardapio item ) {

      this.quantidade = quantidade;
      this.item       = item;
   }


   public Integer getQuantidade() {

      return quantidade;
   }


   public Cardapio getItem() {

      return item;
   }


   @Override
   public String toString() {

      return "ItemComanda [quantidade=" + quantidade + ", item=" + item + "]";
   }


}
