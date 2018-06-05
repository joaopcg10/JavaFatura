public class Saude extends AtividadeEconomica
{
    /** Fator de dedução da atividade económica */
    private static final double fator = 0.5;
    
    /** Devolve o fator de dedução desta atividade económica */
    public double getFator (){
        return this.fator;
    }
    
    /** Método que devolve uma string que contém a informação da atividade */
    public String toString (){
        return "Saude";
    }
}
