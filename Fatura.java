import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fatura
{
    /** NIF da empresa que emitiu a fatura */
    private String NIF_emitente;
    /** Nome da empresa que emitiu a fatura */
    private String descricao_emitente;
    /** NIF do cliente */
    private String NIF_cliente;
    /** Descrição da despesa */
    private String descricao_despesa;
    /** Data da fatura */
    private LocalDateTime data;
    /** Atividade económica da fatura */
    private AtividadeEconomica natureza_despesa;
    /** Valor da fatura */
    private int valor;
    /** Boolean que diz se a fatura foi validada ou não */
    private boolean validada;
    /** Boolean que diz se a fatura foi alterada ou não */
    private boolean alterada;
    
    /** Construtor por omissão */
    public Fatura (){
        this.NIF_emitente = "";
        this.descricao_emitente = "";
        this.NIF_cliente = "";
        this.descricao_despesa = "";
        this.natureza_despesa = null;
        this.valor = 0;
        this.validada = false;
        this.alterada = false;
    }
    
    /** Construtor parametrizado */
    public Fatura (String NIF_emitente, String descricao_emitente, String NIF_cliente, String descricao_despesa, AtividadeEconomica natureza_despesa, int valor, LocalDateTime data, boolean validada, boolean alterada){
        this.NIF_emitente = NIF_emitente;
        this.descricao_emitente = descricao_emitente;
        this.NIF_cliente = NIF_cliente;
        this.descricao_despesa = descricao_despesa;
        this.natureza_despesa = natureza_despesa;
        this.valor = valor;
        this.data = data;
        this.validada = validada;
        this.alterada = alterada;
    }
    
    /** Construtor de cópia */
    public Fatura (Fatura f){
        this(f.getNIF_emitente(), f.getDescricao_emitente(),f.getNIF_cliente(),f.getDescricao_despesa(),f.getNatureza_despesa(),f.getValor(),f.getData(),f.getValidada(),f.getAlterada());
    }
    
    /** Método que devolve o NIF do emitente */
    public String getNIF_emitente(){
        return this.NIF_emitente;
    }
    
    /** Método que devolve o NIF do cliente */
    public String getNIF_cliente(){
        return this.NIF_cliente;
    }

    /** Método que devolve a atividade económica da despesa */
    public AtividadeEconomica getNatureza_despesa(){
        return this.natureza_despesa;
    }
    
    /** Método que devolve o valor da fatura */
    public int getValor(){
        return this.valor;
    }
    
    /** Método que devolve a descrição do emitente */
    public String getDescricao_emitente(){
        return this.descricao_emitente;
    }
    
    /** Método que devolve a descrição da despesa */
    public String getDescricao_despesa (){
        return this.descricao_despesa;
    }
    
    /** Método que devolve a data da fatura */
    public LocalDateTime getData (){
        return this.data;
    }
    
    /** Método que devolve se a fatura é validada */
    public boolean getValidada (){
        return this.validada;
    }
    
    /** Método que devolve se a fatura é alterada */
    public boolean getAlterada (){
        return this.alterada;
    }
    
    /** Método que define o NIF do emitente */
    public void setNIF_emitente (String NIF_emitente){
        this.NIF_emitente = NIF_emitente;
    }
    
    /** Método que define o NIF do cliente */
    public void setNIF_cliente (String NIF_cliente){
        this.NIF_cliente = NIF_cliente;
    }
    
    /** Método que define a natureza da despesa */
    public void setNatureza_despesa (AtividadeEconomica natureza_despesa){
        this.natureza_despesa = natureza_despesa;
    }
    
    /** Método que define o valor da despesa */
    public void setValor (int valor){
        this.valor = valor;
    }
    
    /** Método que define a descrição do emitente */
    public void setDescricao_emitente (String descricao_emitente){
        this.descricao_emitente = descricao_emitente;
    }
    
    /** Método que define a descrição da despesa */
    public void setDescricao_despesa (String descricao_despesa){
        this.descricao_despesa = descricao_despesa;
    }
    
    /** Método que define a data da fatura */
    public void setData (LocalDateTime data){
        this.data = data;
    }
    
    /** Método que define se a fatura é validada */
    public void setValidada (boolean validada){
        this.validada = validada;
    }
    
    /** Método que define se a fatura é alterada */
    public void setAlterada (boolean alterada){
        this.alterada = alterada;
    }
    
    /** Método que apresenta no ecrã a informação da fatura */
    public void displayFatura (){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        
        System.out.println("NIF (emitente): "+this.NIF_emitente);
        System.out.println("NIF (cliente): "+this.NIF_cliente);
        System.out.println("Natureza da despesa: "+this.natureza_despesa.toString());
        System.out.println("Valor: "+Integer.toString(this.valor));
        System.out.println("Designação do emitente: "+this.descricao_emitente);
        System.out.println("Descrição da despesa: "+this.descricao_despesa);
        System.out.println("Data: "+this.data.format(f));
        System.out.println("Validada: "+Boolean.toString(this.validada));
        System.out.println("Alterada: "+Boolean.toString(this.alterada));
        System.out.println("##########################################################");
        
    }
    
    /** Método que passa para uma string a informação da fatura */
    public String toString(){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        
        return this.NIF_emitente+" "
              +this.NIF_cliente+" "
              +this.natureza_despesa.toString()+" "
              +Integer.toString(this.valor)+" "
              +this.descricao_emitente.replace(" ","-")+" "
              +this.descricao_despesa.replace(" ","-")+" "
              +this.data.format(f)+" "
              +Boolean.toString(this.validada)+" "
              +Boolean.toString(this.alterada)+"\r\n";
    }
    
    /** Método que devolve um clone da fatura */
    public Fatura clone(){
        return new Fatura(this);
    }
    
    /** Método que iguala duas faturas */
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        
        Fatura f = (Fatura) o;
        return this.NIF_emitente == f.getNIF_emitente() &&
               this.natureza_despesa == f.getNatureza_despesa() &&
               this.valor == f.getValor() &&
               this.descricao_emitente == f.getDescricao_emitente() &&
               this.descricao_despesa == f.getDescricao_despesa() &&
               this.NIF_cliente == f.getNIF_cliente() &&
               this.validada == f.getValidada() &&
               this.alterada == f.getAlterada();
    }
    
}
