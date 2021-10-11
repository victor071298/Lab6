//Classe das threads
class  thread extends Thread{
    //Identificador das threads 
    private int id;
    //Define que fragmento do programa cada thread executará
    private int bloco = lab6.tamvet/lab6.Nthreads; 

    //Construtor da thread
    public thread(int tid){
        this.id = tid;
    }

    //Código executado pelas threads
    public void run() {
        if(this.id==lab6.Nthreads-1){
            for(int i=id*bloco;i < lab6.tamvet;i++){
                if(lab6.vetor[i]%2==0){
                    mutex.inc();
                }
            }
        }
        else{
            for(int i=id*bloco ; i<bloco*(this.id+1) ; i++){
                if(lab6.vetor[i]%2==0){
                    mutex.inc();
                }
            }
        }
    }
}

class mutex {
    //Contador
    public static synchronized void inc(){
        lab6.paresthreads++;
    }
}

class lab6 {
    static final int Nthreads = 8;  //Número de threads
    static final int tamvet = 200;  //Tamanho máximo de elementos do vetor
    static public int paresthreads = 0;  //Quantidade de pares encontrador pelas threads
    static public int[] vetor;
    static private int paresseq=0;  //Quantidade de pares encontrados sequencialmente

    public static void main (String[] args){
        //Reservando espaço para o vetor de N threads
        Thread[] threads = new Thread[Nthreads];
        vetor = new int[tamvet];

        //Preenchendo o vetor
        for (int i=0;i<tamvet;i++){
            vetor[i]= i+1;
        }
        
        //Criando as threads
        for (int i=0; i<threads.length; i++) {
            threads[i] = new thread(i);
        }

        //Inicializando as threads
        for (int i=0; i<threads.length; i++) {
            threads[i].start();
        }
        
        //Aguardando o término das threads
        for (int i=0; i<threads.length; i++) {
            try { threads[i].join(); } catch (InterruptedException e) { return; }
        }

        //Calculando de maneira sequencial
        for (int i=0;i<tamvet;i++){
            if(vetor[i]%2==0){
                paresseq++;
            }
        }

        //Checando a corretude 
        if(paresseq==paresthreads){
            System.out.println("Valores sequenciais e concorrentes equivalentes");
        }
    
        //Resultado final obtido
        System.out.println("Total de numeros pares no vetor =  " + paresthreads); 
    }
}
