import java.util.Arrays;
import java.lang.String;
import java.util.ArrayList;

public class Empresarial extends Contribuinte
{
    private ArrayList <AtividadeEconomica> codigos;
    private int fator_calculo;
    private boolean interior;
    
    /** Construtor nulo */
    public Empresarial (){
        super();
        this.codigos = new ArrayList <AtividadeEconomica> ();
        this.fator_calculo = 0;
        this.interior = false;
    }
    
    /** Construtor parametrizado */
    public Empresarial (Contribuinte c,ArrayList codigos, int fator_calculo, boolean interior){
        super(c);
        this.codigos = codigos;
        this.fator_calculo = fator_calculo;
        this.interior = interior;
    }
    
    /** Construtor de cópia */
    public Empresarial (Empresarial e){
        super(e);
        this.codigos = e.getCodigos();
        this.fator_calculo = e.getFator_calculo();
        this.interior = e.getInterior();
    }
    
    /** Retorna os codigos das atividades economicas da empresa */
    public ArrayList<AtividadeEconomica> getCodigos (){
        return this.codigos;
    }
    
    /** Retorna o fator de cálculo de dedução fiscal da empresa */
    public int getFator_calculo (){
        return this.fator_calculo;
    }
    
    public boolean getInterior (){
        return this.interior;
    }
    
    /** Define os codigos das atividades economicas da empresa */
    public void setCodigos (ArrayList codigos){
        this.codigos = codigos;
    }
    
    /** Define o fator de cálculo de dedução fiscal da empresa */
    public void setFator_calculo (int fator_calculo){
        this.fator_calculo = fator_calculo;
    }
    
    public void setInterior (boolean interior){
        this.interior = interior;
    }
    
    /** Método que devolve um boolean caso as empresas sejam iguais */
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;      
        
        Empresarial e = (Empresarial) o;
        return super.equals(this) &&
               this.codigos == e.getCodigos () &&
               this.fator_calculo == e.getFator_calculo() &&
               this.interior == e.getInterior();
    }
    
    /** Método que clona uma empresa */
    public Empresarial clone (){
        return new Empresarial(this);
    }
    
    /** Método que devolve uma string com informação da empresa */
    public String toString (){
        return super.toString()+" "
              +this.codigos.toString().replace(" ","")+" "
              +this.fator_calculo+" "
              +Boolean.toString(this.interior)+"\r\n";
    }
    
    /** Método que apresenta a informação da empresa no ecrã */
    public void displayContribuinte (){
        super.displayContribuinte();
        System.out.println("Codigos das atividades económicas: "+this.codigos.toString());
        System.out.println("Fator de dedução fiscal: "+Integer.toString(this.fator_calculo));
        System.out.println("Empresa do interior: "+Boolean.toString(this.interior));
        System.out.println("##########################################################");        
    }
}
