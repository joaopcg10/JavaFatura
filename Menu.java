import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

//fazer validações
public class Menu
{
    private static ContribuinteMap contribuintes;
    private static FaturaMap faturas;
    private static Contribuinte user;
    
    /** Construtor por omissão */
    public Menu (){
        this.contribuintes = new ContribuinteMap ();
        this.faturas = new FaturaMap ();
        this.user = null;        
    }
    
    /** Método que devolve uma linha escrita no terminal */
    public static String getLine (){
        Scanner sc = new Scanner (System.in);
        String temp = sc.nextLine();
        sc.close();
        return temp;
    }
    
    /** Método que devolve uma string escrita no terminal */
    public static String getString (){
        Scanner sc = new Scanner (System.in);
        String temp = sc.next();
        sc.nextLine();
        sc.close();
        return temp;
    }
    
    /** Método que devolve um inteiro escrito no terminal */
    public static int getInt (){
        Scanner sc = new Scanner (System.in);
        int temp = sc.nextInt();
        sc.nextLine();
        sc.close();
        return temp;
    }
    
    /** Método que guarda o estado da aplicação */
    public static void guardarEstado (){
        try {
            contribuintes.guardarContribuintes();
            faturas.guardarFaturas();
            System.out.println("Estado guardado com sucesso.");
        } catch (IOException ex) {
            System.out.println("Erro! Não foi possível guardar o estado da aplicação.");
        }
    }
    
    /** Método que carrega o estado da aplicação */
    public static void carregarEstado (){
        try {
            contribuintes.loadContribuintes();
            faturas.loadFaturas();
            System.out.println("Estado carregado com sucesso.");
        } catch (IOException ex) {
            System.out.println("Erro! Não foi possível carregar o estado da aplicação.");
        }
        
        init();
    }
    
    /** Método que inicia o menu */
    public static void init (){
        Scanner sc = new Scanner (System.in);
        System.out.println("Escolha uma das seguintes opções registar um contribuinte (R), fazer login (L), carregar estado da aplicação (C) ou sair (S).");
        String temp = getString();
        
        switch (temp){
            case "R":
                registarContribuinte();
                break;
            case "L":
                login();
                break;
            case "C":
                carregarEstado();
                break;
            case "P":
                contribuintes.printContribuintes();
                faturas.printFaturas();
                init();
                break;
            case "S":
                return;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                init();
                break;
        }
    }
    
    /** Método que faz logOut ao utilizador */
    public static void logOut (){
        user = null;
        init();
    }
    
    /** Método que faz logIn ao utilizador */
    public static void login (){
        String NIF, pw;
        System.out.println("Introduza o seu NIF:");
        NIF = getString();
        System.out.println("Introduza a sua palavra passe:");
        pw = getString();
        
        switch (contribuintes.validarDados(NIF,pw)){
            case 0:
                System.out.println("Erro! Palavra-passe errada.");
                login();
                break;
            case 1:
                System.out.println("Login efetuado com sucesso como: "+contribuintes.getContribuinte(NIF).getNome());
                user = contribuintes.getContribuinte(NIF);                
                
                if (user instanceof Individual) optIndividual();
                else optEmpresa();
                break;
            case 2:
                System.out.println("Erro! Este NIF não está registado na aplicação");
                login();
                break;
            case 3:
                System.out.println("Login efetuado com sucesso como: administrador");
                optAdmin();
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                login();
                break;
        }               
    }
    
    /** Método que regista um contribuinte no mapa */
    public static void registarContribuinte (){
        Scanner sc = new Scanner(System.in);
        System.out.println("Individual ou Empresarial? (I/E)");
        String temp = getString();
        AtividadeEconomica eco = new AtividadeEconomica ();
        boolean val;
        
        switch (temp){
            case "E":
                Empresarial e = new Empresarial ();

                System.out.println("Introduza o NIF:");
                e.setNIF(getString());
                
                System.out.println("Introduza o email:");
                e.setEmail(getString());
                
                System.out.println("Introduza o nome:");
                e.setNome(getLine());
                
                System.out.println("Introduza a morada:");
                e.setMorada(getLine());
                
                System.out.println("Introduza a palavra passe:");
                e.setPassword(getString());

                System.out.println("Introduza o fator de dedução fiscal:");
                e.setFator_calculo(getInt());

                System.out.println("Introduza as atividades económicas em que participa no formato [Atividade1,Atividade2,Atividade3], alguma atividades disponiveis são Saude, Educacao, Restauracao, etc.");
                e.setCodigos(eco.fromString(getString()));
                
                System.out.println("É uma empresa do interior?");
                e.setInterior(Boolean.parseBoolean(getString()));

                System.out.println("Contribuinte adicionado com sucesso!");
                
                System.out.println(e.toString());
                contribuintes.addContribuinte(e);
                init();
                break;
            case "I":
                Individual i = new Individual ();
                
                System.out.println("Introduza o NIF:");
                i.setNIF(getString());
                
                System.out.println("Introduza o email:");
                i.setEmail(getString());
                
                System.out.println("Introduza o nome:");
                i.setNome(getLine());
                
                System.out.println("Introduza a morada:");
                i.setMorada(getLine());
                
                System.out.println("Introduza a palavra passe:");
                i.setPassword(getString());
                
                System.out.println("Introduza o número de dependentes do agregado familiar:");
                i.setNum_dependentes(getInt());
                
                System.out.println("Introduza os NIFS do agregado familiar no formato [NIF1,NIF2,NIF3]");
                i.setNIF_agregado(contribuintes.fromString(getString()));
                
                System.out.println("Introduza os códigos das atividades económicas para as quais tem possibilidade de deduzir despesas no formato [Atividade1,Atividade2,Atividade3]");
                i.setCodigos(eco.fromString(getString()));
                
                System.out.println(i.toString());
                contribuintes.addContribuinte(i);
                init();
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                registarContribuinte();
                break;
     }
    }
    
    /** Método que muda uma atividade económica associada a uma fatura */
    public static void mudarAtividade (){
        AtividadeEconomica eco = new AtividadeEconomica ();
        String temp;
        
        for(Fatura f : faturas.getFaturasInd(user.getNIF(),false)){
            System.out.println("Fatura: ");
            f.displayFatura();
            System.out.println("Deseja alterar a atividade económica desta fatura? (S/N)");
            
            temp = getString();
            switch (temp){
                case "S":
                    System.out.println("Introduza uma atividade económica (Saude, Educacao..):");
                    Fatura nf = new Fatura(f);
                    nf.setNatureza_despesa(eco.getAtividade(getString()));
                    nf.setValidada(true);
                    if (!f.getNatureza_despesa().toString().equals("X")) nf.setAlterada(true);
                    faturas.swapFatura(f,nf);
                    System.out.println(nf.toString());
                    break;
                case "N":
                    System.out.println("Break!");
                    break;
                default:
                    System.out.println("Erro! Introduza um comando válido.");
                    mudarAtividade();
                    break;
            }
            
        }
        
        System.out.println("Alterações efetuadas com sucesso!");       
    }
    
    /** Método que mostra as faturas de um contribuinte individual no ecrã */
    public static void displayFaturas (){
        System.out.println("##########################################################");
        for(Fatura f : faturas.getFaturasInd(user.getNIF(),true))
            f.displayFatura();
    }
    
    /** Método que devolve o total faturado por uma empresa no ecrã */
    public static void totalFaturado (){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        
        System.out.println("Insira a data mais recente no seguinte formato: yyyy-MM-dd_HH:mm");        
        LocalDateTime antiga = LocalDateTime.parse(getString(),format);
        
        System.out.println("Insira a data mais antiga no seguinte formato: yyyy-MM-dd_HH:mm");
        LocalDateTime recente = LocalDateTime.parse(getString(),format);
        
        double res = 0;        
        for(Fatura f : faturas.getFaturasEmp(user.getNIF(),false)){
            if (f.getData().isBefore(recente) && f.getData().isAfter(antiga)) res += f.getValor();
        }
        
        System.out.println("Total faturado: "+Double.toString(res)+" euros");
        optEmpresa();
    }
    
    /** Método que emite uma fatura para o mapa */
    public static void criarFatura (){
        Fatura f = new Fatura();
        Empresarial userE = (Empresarial) user;

        System.out.println("Insira o NIF do cliente:");
        f.setNIF_cliente(getString());

        System.out.println("Insira o valor da despesa:");
        f.setValor(getInt());
        
        System.out.println("Insira a descrição da despesa:");
        f.setDescricao_despesa(getLine());
        
        f.setNIF_emitente(user.getNIF());
        f.setDescricao_emitente(user.getNome());
        f.setData(LocalDateTime.now());
        f.setAlterada(false);
        if (userE.getCodigos().size() == 1) {
            f.setNatureza_despesa(userE.getCodigos().get(0));
            f.setValidada(true);
            
            
            Double val;
            Individual i = (Individual) contribuintes.getContribuinte(f.getNIF_cliente());
            if (f.getNatureza_despesa().isContained(i.getCodigos())){
                val = f.getValor() * f.getNatureza_despesa().getFator();
            } else {
                val = 0.0;
            }
            System.out.println("Esta fatura acrescenta um montante de dedução fiscal ao contribuinte "+contribuintes.getContribuinte(f.getNIF_cliente()).getNIF()+" de "+Double.toString(val)+" euros.");
        } else {
            f.setNatureza_despesa(new AtividadeEconomica ());
            f.setValidada(false);
        }
        
        faturas.addFatura(f);
        optEmpresa();
    }
    
    /** Método que lista as faturas de uma empresa no ecrã */
    public static void listarFaturasEmpresa (){
        System.out.println("Ordenadas por data (D) ou valor (V)?");
        String temp = getString();
        
        System.out.println("##########################################################");
        switch (temp){
            case "D":                
                for(Fatura f : faturas.getFaturasEmp(user.getNIF(),true))
                    f.displayFatura();

                break;
            case "V":
                for(Fatura f : faturas.getFaturasEmp(user.getNIF(),false))
                    f.displayFatura();
                
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                listarFaturasEmpresa();
                break;                
        }
        
        optEmpresa();
    }
    
    /** Método que lista as faturas de um contribuinte individual no ecrã */
    public static void listarFaturasContribuinte (){
        System.out.println("Introduza o NIF do contribuinte:");
        String NIF = getString();
        
        System.out.println("Ordenadas por data (D) ou valor (V)?");
        String temp = getString();
        
        System.out.println("##########################################################");
        switch (temp){
            case "D":
                for (Fatura f : faturas.getFaturasInd(NIF,true))
                    if (f.getNIF_emitente().equals(user.getNIF())) f.displayFatura();
                break;
            case "V":
                for (Fatura f : faturas.getFaturasInd(NIF,false))
                    if (f.getNIF_emitente().equals(user.getNIF())) f.displayFatura();
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                listarFaturasContribuinte();
                break;
        }
        
        optEmpresa();
    }    
    
    /** Método que apresenta as opções de um contribuinte empresarial */
    public static void optEmpresa (){
        System.out.println("Escolha uma das seguintes opções: Criar fatura (C), listar faturas da empresa (LFE), listar faturas de um contribuinte (LFC), total faturado (TF), gravar (G) e logout (L).");
        String temp = getString();
        
        switch (temp){
            case "C":
                criarFatura();
                break;
            case "TF":
                totalFaturado();
                break;
            case "LFE":
                listarFaturasEmpresa();
                break;
            case "LFC":
                listarFaturasContribuinte();
                break;
            case "G":
                guardarEstado();
                optEmpresa();
                break;
            case "L":
                logOut();
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                optEmpresa();
                break;
            
        }
    }
    
    /** Método que apresenta as opções de um contribuinte individual */
    public static void optIndividual (){
        System.out.println("Escolha uma das seguintes opções: ver faturas (VF), verificar montante de dedução fiscal acumulado (VD), mudar atividade económica de uma fatura (MF), gravar estado da aplicação (G) e logout (L).");
        String temp = getString();
        
        switch (temp){
            case "VF":
                displayFaturas();
                optIndividual();
                break;
            case "VD":
                System.out.println("O total deduzido por si e pelo seu agregado familiar é: "+Double.toString(totalDeduzidoInd()) + " euros.");
                optIndividual();
                break;
            case "MF":
                mudarAtividade();
                optIndividual();
                break;
            case "G":
                guardarEstado();
                optIndividual();
                break;
            case "L":
                logOut();
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");
                optIndividual();
                break;
        }
    }
    
    /** Método que adiciona um contribuinte a uma lista ligada por ordem decrescente de gasto */
    public static void addToListGasto (LinkedList<Contribuinte> list, Contribuinte cont){
        if (list.size() == 0){
            list.add(cont);
            return;
        }
            
        int i = 0;
        
        for (Contribuinte c : list){
            if (faturas.faturadoOuGasto(cont.getNIF(),true) >= faturas.faturadoOuGasto(c.getNIF(),true)){
                list.add(i,cont);
                return;
            }
            
            i++;
        }
        
        list.add(i,cont);
    }
    
    /** Método que devolve o top 10 de contribuintes que mais gastam */
    public static void topGasto (){
        LinkedList <Contribuinte> top = new LinkedList <Contribuinte> ();
        
        for (Contribuinte c : contribuintes.getContribuintes())
            addToListGasto(top,c);
        
        for (int x = 0; x<top.size(); x++){
            if (x == 10) break;
            top.get(x).displayContribuinte();
        }
        
        optAdmin();
    }
    
    /** Método que adiciona uma empresa a uma lista ligada por ordem crescente do faturado */
    public static void addToListFaturado (LinkedList<Empresarial> list, Empresarial emp){
        if (list.size() == 0){
            list.add(emp);
            return;
        }
            
        int i = 0;
        
        for (Empresarial e : list){
            if (faturas.faturadoOuGasto(emp.getNIF(),false) >= faturas.faturadoOuGasto(e.getNIF(),false)){
                list.add(i,emp);
                return;
            }
            
            i++;
        }
        
        list.add(i,emp);        
    }
    
    /** Método que devolve o top X de empresas que mais faturam e o seu montante de dedução fiscal */
    public static void topFaturado (){
        LinkedList <Empresarial> top = new LinkedList <Empresarial> ();
        
        for (Contribuinte c : contribuintes.getContribuintes()){
            if (c instanceof Empresarial){
                Empresarial e = (Empresarial) c;
                addToListFaturado(top,e);
            }
        }
        
        System.out.println("Introduza o número de empresas para ver:");
        int x = getInt();
        
        for (int i = 0; i<top.size(); i++){
            if (i == x) break;
            top.get(i).displayContribuinte();
            System.out.println("O total deduzido por esta empresa é: "+Double.toString(totalDeduzidoEmp())+" euros.");
        }        

        
        optAdmin();
    }    
    
    /** Método que apresenta as opções de administrador */
    public static void optAdmin (){
        System.out.println("Escolha uma das seguintes opções: ver top 10 contribuintes que mais gastam (C) e ver empresas que mais faturam, o seu montante deduzido (E) e logOut (L).");
        String temp = getString();
        
        switch (temp){
            case "C":
                topGasto();
                break;
            case "E":
                topFaturado();
                break;
            case "L":
                logOut();
                break;
            default:
                System.out.println("Erro! Introduza um comando válido.");  
                optAdmin();
                break;
        }
    }
    
    /** Método que devolve o total deduzido de uma empresa */
    public static double totalDeduzidoEmp (){
        Empresarial e = (Empresarial) user;
        double res = faturas.deduzido(e.getNIF(),e.getCodigos()) * reducaoImposto();
        
        switch (e.getFator_calculo()){
            case 1:
                if (res > 500) return 500;
                else return res;
            case 2:
                if (res > 1000) return 1000;
                else return res;
            default:
                return 0;
        }
    }    
    
    /** Método que devolve o total deduzido de um individuo */
    public static double totalDeduzidoInd (){
        Individual i = (Individual) user;
        double res = faturas.deduzido(i.getNIF(),i.getCodigos()) + reducaoImposto();

        if (i.getNum_dependentes() > 0){
            for (int NIF : i.getNIF_agregado()){
                res += faturas.deduzido(Integer.toString(NIF),i.getCodigos());
            }
        }
        
        switch (i.getCoeficiente_fiscal()){
            case 1:
                if (res > 200) return 200;
                else return res;
            case 2:
                if (res > 500) return 500;
                else return res;
            default:
                return 0;
        }
    }
    
    /** Método que faz a redução de imposto caso seja uma familia numerosa ou uma empresa do interior */
    public static double reducaoImposto (){
        if (user instanceof Individual){
            Individual i = (Individual) user;
            if (i.getNum_dependentes() > 4) return 5 * i.getNum_dependentes ();
            else return 0;
        } else {
            Empresarial e = (Empresarial) user;
            if (e.getInterior()) return 1.2;
            else return 1;
        }
    }
    
}
