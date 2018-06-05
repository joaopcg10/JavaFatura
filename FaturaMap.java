import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class FaturaMap
{
    /** Mapa que contém como chave o NIF da empresa e como valor uma lista ligada que contém as faturas por ela emitidas */
    private Map <String,LinkedList<Fatura>> faturas;
    
    /** Construtor por omissão */
    public FaturaMap (){
        this.faturas = new HashMap <String,LinkedList<Fatura>>();
    }
    
    /** Método que adiciona uma fatura emitida por uma empresa ao mapa por ordem decrescente do seu valor */
    public void addFatura (Fatura f){
        if (this.faturas.get(f.getNIF_emitente()) != null){
            addToList(this.faturas.get(f.getNIF_emitente()), f);
        } else {
            LinkedList <Fatura> flist = new LinkedList <Fatura> ();
            flist.add(f);
            this.faturas.put(f.getNIF_emitente(),flist);
        }
    }
    
    /** Método que adiciona uma fatura a uma lista ligada de faturas por ordem cronológica (mais recente para mais antigo) */
    public void addToListDate (LinkedList<Fatura> faturas, Fatura f){
        int i = 0;
        
        if (faturas.size() == 0){
            faturas.add(f);
            return;
        }
        
        for (Fatura fi : faturas){
            if (f.getData().isAfter(fi.getData())){
                faturas.add(i,f);
                return;
            }
            i++;
        }
        
        faturas.add(i,f);
    }
    
    /** Método que adiciona uma fatura a uma lista de faturas por ordem decrescente de valor */
    public void addToList (LinkedList<Fatura> faturas, Fatura f){
        int i = 0;
        
        if (faturas.size() == 0){
            faturas.add(f);
            return;
        }
        
        for (Fatura fi : faturas){
            if (f.getValor() >= fi.getValor()) {
                faturas.add(i,f);
                return;
            }
            i++;
        }
        
        faturas.add(i,f);
    }
        
    /** Método que guarda todas as faturas no ficheiro "registo_faturas.txt" */
    public void guardarFaturas () throws IOException {
        FileWriter fw = new FileWriter("registo_faturas.txt");
        
        for(String NIF : this.faturas.keySet()){
            for(Fatura f : this.faturas.get(NIF)){
                fw.write(f.toString());
            }
        }
        
        fw.close();
    }
    
    /** Método que carrega todas as faturas guardadas no ficheiro "registo_faturas.txt" */
    public void loadFaturas () throws IOException {
        Scanner sc = new Scanner(new File("registo_faturas.txt"));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        AtividadeEconomica eco = new AtividadeEconomica ();
        
        while(sc.hasNextInt()){
            Fatura f = new Fatura();
            f.setNIF_emitente(sc.next());
            f.setNIF_cliente(sc.next());
            f.setNatureza_despesa(eco.getAtividade(sc.next()));
            f.setValor(sc.nextInt());
            f.setDescricao_emitente(sc.next().replace("-"," "));
            f.setDescricao_despesa(sc.next().replace("-"," "));
            f.setData(LocalDateTime.parse(sc.next(),format));
            f.setValidada(Boolean.parseBoolean(sc.next()));
            f.setAlterada(Boolean.parseBoolean(sc.next()));
            
            addFatura(f);
        }
        
    }
    
    /** Método que troca uma fatura por outra */
    public void swapFatura (Fatura oldF, Fatura newF){
        this.faturas.get(oldF.getNIF_emitente()).remove(oldF);
        addToList(this.faturas.get(oldF.getNIF_emitente()),newF);
    }
    
    /** Método que devolve a lista que contém as faturas de um contribuinte empresarial por ordem de data ou valor (true = data, false = valor)*/
    public LinkedList<Fatura> getFaturasEmp (String NIF, boolean opt){
        if (!opt) return this.faturas.get(NIF);
        
        LinkedList <Fatura> fs = new LinkedList <Fatura> ();
        for (Fatura f : this.faturas.get(NIF))
            addToListDate(fs,f);
            
        return fs;
    }
    
    /** Método que devolve uma lista ligada contendo todas as faturas de um contribuinte individual por ordem de data ou valor (true = data, false = valor) */
    public LinkedList<Fatura> getFaturasInd (String NIF, boolean opt){
        LinkedList <Fatura> fs = new LinkedList <Fatura> ();
        
        for(String key : this.faturas.keySet()){
            for(Fatura f : this.faturas.get(key)){
                if (f.getNIF_cliente().equals(NIF)) {
                    if (opt) addToListDate(fs,f);
                    else addToList(fs,f);
                }
            }
        }
        
        return fs;
    }
    
    /** Método que troca a lista de faturas associadas a um NIF pela passada como argumento */
    public void mudarFaturas (String NIF, LinkedList<Fatura> fs){
        this.faturas.replace(NIF,fs);
    }
    
    /** Método que devolve o total gasto de um contribuinte ou o faturado de um empresarial (true = gasto, false = faturado) */
    public double faturadoOuGasto (String NIF, boolean opt){
        double res = 0;
        
        for (String key : this.faturas.keySet()){
            for (Fatura f : this.faturas.get(key)){
                if (opt) if (f.getNIF_cliente().equals(NIF)) res += f.getValor();
                else if (f.getNIF_emitente().equals(NIF)) res += f.getValor();
            }
        }
        
        return res;
    }
        
    /** Método que devolve o montante de dedução de um só NIF */
    public double deduzido (String NIF, ArrayList <AtividadeEconomica> codigos){
        double res = 0;
        
        for(String key : this.faturas.keySet()){
            for(Fatura f : this.faturas.get(key)){
                if(f.getNIF_cliente().equals(NIF) && f.getValidada() == true && f.getNatureza_despesa().isContained(codigos))
                    res += f.getValor() * f.getNatureza_despesa().getFator();
            }
        }
        
        
        
        return res;
    }
    
    /** Método que imprime todas as faturas */
    public void printFaturas (){
        int i = 1;
        for(String key : this.faturas.keySet()){
            System.out.println("Entrada " + Integer.toString(i)+":");
            i++;
            for(Fatura f : this.faturas.get(key)){
                System.out.printf("%s",f.toString());
            }
        }
        
    }

}
