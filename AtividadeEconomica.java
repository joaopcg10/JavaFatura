import java.util.ArrayList;

public class AtividadeEconomica
{    
    /** Método que transforma uma string numa atividade económica */
    public AtividadeEconomica getAtividade (String atividade){
        switch (atividade){
            case "Saude":
                Saude saude = new Saude ();
                return saude;            
            case "Educacao":
                Educacao educacao = new Educacao ();
                return educacao;
            case "Restauracao":
                Restauracao restauracao = new Restauracao ();
                return restauracao;
            case "AguaLuz":
                AguaLuz agualuz = new AguaLuz ();
                return agualuz;
            default:
                AtividadeEconomica x = new AtividadeEconomica ();
                return x;
     }
    }
    
    /** Método que transforma uma string num ArrayList de atividades económicas */
    public ArrayList <AtividadeEconomica> fromString (String atividade){
        ArrayList <AtividadeEconomica> atividades = new ArrayList <AtividadeEconomica> ();
        String[] strings = atividade.replace("[","").replace("]","").split(",");
        
        for(String aux : strings){
            atividades.add(getAtividade(aux));
        }
        
        return atividades;
    }
    
    /** Método que devolve uma string que contém a informação da atividade */
    public String toString (){
        return "X";
    }
    
    /** Método que devolve o fator de dedução da atividade económica */
    public double getFator (){
        return 0;
    }
    
    /** Método que iguala duas atividades económicas */
    public boolean equals (AtividadeEconomica e){
        if (e.toString().equals(this.toString())) return true;
        else return false;
    }
    
    /** Método que diz se uma atividade económica está contida numa lista de atividades */ 
    public boolean isContained (ArrayList<AtividadeEconomica> atividades){
        for (AtividadeEconomica temp : atividades){
            if (this.equals(temp)) return true; 
        }
        
        return false;
    }

}
