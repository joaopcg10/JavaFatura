import java.lang.String;
import java.util.Arrays;
import java.util.ArrayList;

public class Individual extends Contribuinte
{
    /** Número de dependentes do agregado familiar */
    private int num_dependentes;
    /** Array que contém os NIF's do agregado familiar */
    private int[] NIF_agregado;
    /** Lista que contém as atividades económicas para qual um individuo pode deduzir */
    private ArrayList <AtividadeEconomica> codigos;
    /** Coeficiente fiscal do individuo */
    private int coeficiente_fiscal;
    
    /** Construtor nulo */
    public Individual (){
        super();
        this.num_dependentes = 0;
        this.NIF_agregado = new int[0];
        this.codigos = new ArrayList <AtividadeEconomica> ();
        this.coeficiente_fiscal = 0;
    }
    
    /** Construtor parametrizado */
    public Individual (Contribuinte c, int num_dependentes, int coeficiente_fiscal, int[] NIF_agregado, ArrayList codigos){
        super(c);
        this.num_dependentes = num_dependentes;
        this.NIF_agregado = NIF_agregado;
        this.codigos = codigos;
        this.coeficiente_fiscal = coeficiente_fiscal;
    }
    
    /** Construtor de cópia */
    public Individual (Individual i){
        super(i);
        this.num_dependentes = i.getNum_dependentes();
        this.NIF_agregado = i.getNIF_agregado();
        this.codigos = i.getCodigos();
        this.coeficiente_fiscal = i.getCoeficiente_fiscal();
    }
    
    /** Retorna o número de dependentes do agregado familiar do individuo */
    public int getNum_dependentes (){
        return this.num_dependentes;
    }
    
    /** Retorna o array que contém o NIF do agregado familiar do individuo */
    public int[] getNIF_agregado (){
        return this.NIF_agregado;
    }
    
    /** Retorna os códigos das atividades económicas do individuo */
    public ArrayList getCodigos (){
        return this.codigos;
    }
    
    /** Retorna o coeficiente fiscal do invididuo */
    public int getCoeficiente_fiscal (){
        return this.coeficiente_fiscal;
    }
    
    /** Define o número de dependentes do agregado familiar do indiviiduo */
    public void setNum_dependentes (int num_dependentes){
        this.num_dependentes = num_dependentes;
    }
    
    /** Define o array que contém o NIF do agregado familiar do individuo */
    public void setNIF_agregado (int[] NIF_agregado){
        this.NIF_agregado = NIF_agregado;
    }
    
    /** Define os códigos das atividades económicas do individuo */
    public void setCodigos (ArrayList codigos){
        this.codigos = codigos;
    }
    
    /** Define o coeficiente fiscal do individuo */
    public void setCoeficiente_fiscal (int coeficiente_fiscal){
        this.coeficiente_fiscal = coeficiente_fiscal;
    }
    
    /** Método que iguala dois individuos */
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        
        Individual i = (Individual) o;
        return this.num_dependentes == i.getNum_dependentes() &&
               this.NIF_agregado == i.getNIF_agregado() &&
               this.codigos == i.getCodigos() &&
               this.coeficiente_fiscal == i.getCoeficiente_fiscal() &&
               super.equals(this);
    }
    
    /** Método que clona um individuo */
    public Individual clone (){
        return new Individual(this);
    }
    
    /** Método que devolve uma string com a informação do individuo */
    public String toString (){
        return super.toString()+" "
              +Integer.toString(this.num_dependentes)+" "
              +Integer.toString(this.coeficiente_fiscal)+" "
              +Arrays.toString(this.NIF_agregado).replace(" ","")+" "
              +this.codigos.toString().replace(" ","")+"\r\n";
    }
    
    /** Método que apresenta a informação do individuo no ecrã */
    public void displayIndividuo (){
        System.out.println("Número de dependentes do agregado familiar: "+Integer.toString(this.num_dependentes));
        System.out.println("Coeficiente fiscal: "+Integer.toString(this.coeficiente_fiscal));
        System.out.println("NIF's do agregado familiar: "+Arrays.toString(this.NIF_agregado));
        System.out.println("Códigos das atividades económicas: "+this.codigos.toString());
        System.out.println("##########################################################");        
    }
}
