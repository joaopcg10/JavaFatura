import java.util.*;
import java.io.*;

public class ContribuinteMap
{
    /** Mapa que contém como chave o NIF do contribuinte e o valor é o contribuinte */
    private Map <String,Contribuinte> contribuintes;

    /** Construtor nulo */
    public ContribuinteMap (){
        this.contribuintes = new HashMap <String,Contribuinte>();
    }
    
    /** Adiciona um contribuinte ao mapa */
    public void addContribuinte (Contribuinte contribuinte){
        this.contribuintes.put(contribuinte.getNIF(),contribuinte);
    }
    
    /** Retorna um contribuinte baseado no seu NIF */
    public Contribuinte getContribuinte (String NIF){
       return this.contribuintes.get(NIF);
    }
    
    /** Método que devolve uma lista ligada com os contribuintes do mapa */
    public LinkedList<Contribuinte> getContribuintes (){
        LinkedList <Contribuinte> lc = new LinkedList <Contribuinte> ();
        
        for (String NIF : this.contribuintes.keySet())
            lc.push(this.contribuintes.get(NIF));
        
        return lc;
    }
    
    /** Valida os dados do utilizador ao entrar na aplicação */
    public int validarDados (String NIF, String pw){
        if (NIF.equals("admin") && pw.equals("admin")) return 3;
        
        if (getContribuinte(NIF) != null){
            if (getContribuinte(NIF).getPassword().equals(pw)) return 1;
        } else return 0;
        
        return 2;
    }
    
    /** Guarda os contribuintes no ficheiro "registo_contribuintes.txt" */
    public void guardarContribuintes () throws IOException {
        FileWriter f = new FileWriter("registo_contribuintes.txt");
        
        for(String NIF : contribuintes.keySet())
            f.write(getContribuinte(NIF).toString());
            
        f.close();
    }
    
    /** Método auxiliar que transforma uma string num array de inteiros */
    public int[] fromString(String string) {
        if (string.equals("[]")) return new int[0];
        
        String[] strings = string.replace("[", "").replace("]", "").split(",");
        int result[] = new int[strings.length];
        
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        
        return result;
    }
      
    /** Lê os contribuintes guardados no ficheiro "registo_contribuintes.txt" */
    public void loadContribuintes () throws IOException {
        Scanner sc = new Scanner (new File ("registo_contribuintes.txt"));

        AtividadeEconomica eco = new AtividadeEconomica ();
        
        while(sc.hasNextInt()){
            Contribuinte c = new Contribuinte ();
            c.setNIF(sc.next());
            c.setNome(sc.next().replace("-"," "));
            c.setEmail(sc.next());
            c.setMorada(sc.next().replace("-"," "));
            c.setPassword(sc.next());
            
            if(sc.hasNextInt()){
                Individual i = new Individual (c,sc.nextInt(),sc.nextInt(),fromString(sc.next()),eco.fromString(sc.next()));
                addContribuinte(i);
            } else {
                Empresarial e = new Empresarial (c,eco.fromString(sc.next()),sc.nextInt(),Boolean.parseBoolean(sc.next()));
                addContribuinte(e);
            }
        }
    }
    
    /** Imprime todos os contribuintes */
    public void printContribuintes (){
        for(String s : contribuintes.keySet()){
            System.out.printf("%s",getContribuinte(s).toString());
        }
    }
}
