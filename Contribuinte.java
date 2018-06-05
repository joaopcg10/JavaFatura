public class Contribuinte
{
    /** NIF do contribuinte */
    private String NIF;
    /** Email do contribuinte */
    private String email;
    /** Nome do contribuinte */
    private String nome;
    /** Morada do contribuinte */
    private String morada;
    /** Password do contribuinte */
    private String password;

    /** Construtor nulo */
    public Contribuinte (){
        this.NIF = "";
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.password = "";
    }
    
    /** Construtor parametrizado para a classe Contribuinte */
    public Contribuinte (String NIF, String email, String nome, String morada, String password){
        this.NIF = NIF;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.password = password;
    }
    
    /** Construtor de cópia */    
    public Contribuinte (Contribuinte c){
        this(c.getNIF(),c.getEmail(),c.getNome(),c.getMorada(),c.getPassword());
    }
    
    /** Retorna o NIF do contribuinte */
    public String getNIF (){
        return this.NIF;
    }
    
    /** Retorna o email do contribuinte */
    public String getEmail (){
        return this.email;
    }
    
    /** Retorna o nome do contribuinte */
    public String getNome (){
        return this.nome;
    }
    
    /** Retorna a morada do contribuinte */
    public String getMorada (){
        return this.morada;
    }
    
    /** Retorna a password do contribuinte */
    public String getPassword (){
        return this.password;
    }
    
    /** Define o NIF do contribuinte */
    public void setNIF (String NIF){
        this.NIF = NIF;
    }
    
    /** Define o email do contribuinte */
    public void setEmail (String email){
        this.email = email;
    }
    
    /** Define o nome do contribuinte */
    public void setNome (String nome){
        this.nome = nome;
    }
    
    /** Define a morada do contribuinte */
    public void setMorada (String morada){
        this.morada = morada;
    }
    
    /** Define a password do contribuinte */
    public void setPassword (String password){
        this.password = password;
    }
    
    /** Método que devolve um boolean caso os contribuintes sejam iguais */
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        
        Contribuinte c = (Contribuinte) o;
        return this.NIF == c.getNIF() &&
               this.nome == c.getNome() &&
               this.email == c.getEmail() &&
               this.morada == c.getMorada() &&
               this.password == c.getPassword();
    }
    
    /** Método que clona um contribuinte */
    public Contribuinte clone (){
        return new Contribuinte(this);
    }
    
    /** Método que cria uma string com a informação do contribuinte */
    public String toString (){
        return this.NIF+" "
               +this.nome.replace(" ","-")+" "
               +this.email+" "
               +this.morada.replace(" ","-")+" "
               +this.password;
    }
    
    /** Método que apresenta no ecrã a informação do contribuinte */
    public void displayContribuinte (){
        System.out.println("##########################################################");
        System.out.println("NIF: "+this.NIF);
        System.out.println("Email: "+this.email);
        System.out.println("Nome: "+this.nome);
        System.out.println("Morada: "+this.morada);
        System.out.println("Password: "+this.password);
    }    
}
