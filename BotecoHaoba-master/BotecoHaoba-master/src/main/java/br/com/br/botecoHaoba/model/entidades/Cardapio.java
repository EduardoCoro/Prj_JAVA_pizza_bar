package br.com.br.botecoHaoba.model.entidades;


public enum Cardapio {

   CERVEJA_PURO_MALTE( "Cerveja puro malte",     12.50 ),
   CERVEJA_PURO_MILHO( "Cerveja puro milho",      9.15 ),
   TACA_VINHO(         "Taça de vinho",          13.00 ),
   REFRIGERANTE_LATA(  "Refrigerante cola lata",  6.00 ),
   AGUA(               "Água",                    4.00 ),
   BOLINHO_BACALHAU(   "Bolinho de bacalhau",    43.00 ),
   BATATA_FRITA(       "Porção de batata frita", 17.00 ),
   SALADA(             "Salada verde",           22.49 ),
   PORCAO_FRANGO(      "Frango a passarinho",    33.00 ),
   SORVETE(            "Sorvete palito",          9.00 );


   private String descricao;
   private Double preco;

   private Cardapio( String descricao, Double preco ) {

      this.descricao = descricao;
      this.preco     = preco;
   }


   public String getDescricao() {

      return descricao;
   }


   public Double getPreco() {

      return preco;
   }


   @Override
   public String toString() {

      return descricao;
   }

}
