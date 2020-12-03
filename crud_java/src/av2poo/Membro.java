package av2poo;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Membro {
    String nomeCompleto, cpf, numCelular, email, dob;
    Scanner info = new Scanner(System.in);
    
    boolean go = true; //go sinaliza se o arquivo deve ser escrito/modificado.

    File cadastro = null; //cadastro permite a criação e pesquisa de um arquivo.
    FileWriter escrever = null; //escreve dentro do arquivo gerado.
    Scanner ler = null; //ler adquire as informações relevantes ao cadastro.

    String dir = null; //receberá um caminho relativo para salvar o arquivo na pasta apropriada.
    //caminhos disponíveis
    String pathAluno = "..\\data\\Alunos\\";
    String pathProf = "..\\data\\Professores\\";
    String pathTurma = "..\\data\\Unidades\\";
    String pathFuncionario = "..\\data\\Funcionários\\";

    //padrões de verificação dos dados.
    Pattern rxCpf = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$"); 
    //esse padrão tem uma precisão extremamente alta.
    //esse padrão foi adquirido da fonte:
    //https://gist.github.com/igorcosta/3a4caa954a99035903ab
    Pattern rxNome = Pattern.compile("[a-zA-Z]");
    //esse padrão tem uma precisão boa, mas não é perfeito.
    //esse padrão é de autoria de Lucas Guerra Cardoso
    Pattern rxCelular = Pattern.compile("^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$");
    //esse padrão funciona bem.
    //esse padrão foi adquirido da fonte:
    //https://pt.stackoverflow.com/questions/46672/como-fazer-uma-express%C3%A3o-regular-para-telefone-celular
    Pattern rxEmail = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    //esse padrão tem uma precisão extremamente alta.
    //esse padrão foi adquirido da fonte:
    //https://emailregex.com/
    Pattern rxDob = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}");
    //esse padrão tem uma precisão extremamente alta.
    //esse padrão é de autoria de Lucas Guerra Cardoso

    //comparam dados entrados com os padrões.
    Matcher mtNome = null;
    Matcher mtCpf = null;
    Matcher mtCelular = null;
    Matcher mtEmail = null;
    Matcher mtDob = null;

    public void SetInfoPessoal(){
        do {
            System.out.println("\nNome completo (Letras somente): ");
            this.nomeCompleto = this.info.nextLine();
            mtNome = this.rxNome.matcher(this.nomeCompleto);
        } while(!this.mtNome.find());

        do {
            System.out.println("Número de celular (formato (XX) 9XXXX-XXXX): ");
            this.numCelular = this.info.nextLine();
            this.mtCelular = this.rxCelular.matcher(this.numCelular);
        } while(!this.mtCelular.find());
        
        do {
            System.out.println("Cpf (formato XXX.XXX.XXX-XX): ");
            this.cpf = this.info.nextLine();
            this.mtCpf = this.rxCpf.matcher(this.cpf);
        } while(!this.mtCpf.find());
           
        do {
            System.out.println("Data de nascimento ");
            System.out.println("Dia (XX ou X): ");
            this.dob = this.info.nextLine() + "/";
            System.out.println("Mês (XX ou X): ");
            this.dob += this.info.nextLine() + "/";
            System.out.println("Ano (XXXX): ");
            this.dob += this.info.nextLine();
            this.mtDob = this.rxDob.matcher(this.dob);
        } while(!this.mtDob.find());

        do {
            System.out.println("Email (formato nomedoemail@provedor.com(.br opcional)): ");
            this.email = this.info.nextLine();
            this.mtEmail = rxEmail.matcher(this.email);
        } while (!this.mtEmail.find());
    }
    
    public void GetInfoPessoal(){
        //escolhi usar uma função ao invés dos vários gets pré-gerados do Java para melhorar a leitura.
        System.out.println("\nNome completo: " + this.nomeCompleto);
        System.out.println("Data de nascimento: " + this.dob);
        System.out.println("Número de celular: " + this.numCelular);
        System.out.println("Cpf: " + this.cpf);
        System.out.println("Email: " + this.email);
    }
   
    public void CarregarCadastro(char path){
        //permite a continuidade da função no caso da entrada de turma.
        boolean pass = false;
        
        switch (path){
            case '1':
                this.dir = this.pathAluno;
                break;
            case '2':
                this.dir = this.pathProf;
                break;
            case '3':
                this.dir = this.pathTurma;
                break;
            case '4':
                this.dir = this.pathFuncionario;
                break;
        }
        
        //caso o arquivo procurado seja turma...
        if(this.dir == this.pathTurma){
            do{
                System.out.println("\nQual unidade?\n"
                        + "1 - Méier I\n"
                        + "2 - Méier II\n"
                        + "3 - Méier III\n"
                        + "4 - Rio Comprido\n"
                        + "5 - Méier V\n");
                
                switch(this.info.next().charAt(0)){
                    case '1':
                        this.dir += "\\Méier I\\";
                        pass = true;
                        break;
                    case '2':
                        this.dir += "\\Méier II\\";
                        pass = true;
                        break;
                    case '3':
                        this.dir += "\\Méier III\\";
                        pass = true;
                        break;
                    case '4':
                        this.dir += "\\Rio Comprido\\";
                        pass = true;
                        break;
                    case '5':
                        this.dir += "\\Méier V\\";
                        pass = true;
                        break;
                    default: 
                        System.out.println("Inválido"); 
                        break;
                }
            } while(pass == false);
            this.info.nextLine();
            
        }
        
        try {
            System.out.println("Nome?");
            this.nomeCompleto = this.info.nextLine();
            //concatena diretório + nome inserido + tipo de arquivo (texto).
            
            System.out.println(this.dir + this.nomeCompleto + ".txt");
            
            this.cadastro = new File(this.dir + this.nomeCompleto + ".txt");
            
            this.ler = new Scanner(this.cadastro);

            System.out.println("\n");

            //lê o arquivo até não ter mais linhas não-vazias.
            while(this.ler.hasNextLine()){
                String texto = this.ler.nextLine();
                System.out.println(texto);
            }
            //esse snippet foi levemente modificado da fonte:
            //https://www.w3schools.com/java/java_files_read.asp

            this.ler.close();
            
        } catch (FileNotFoundException e){
            System.out.println("Não existe!");
        }
        
    }
    
    public void GerarCadastro(char path){
        //permite a continuidade da função no caso da entrada de turma.
        boolean pass = false;
        
        switch (path){
            case '1':
                this.dir = this.pathAluno;
                break;
            case '2':
                this.dir = this.pathProf;
                break;
            case '3':
                this.dir = this.pathTurma;
                break;
            case '4':
                this.dir = this.pathFuncionario;
                break;
        }
        
        //caso o arquivo gerado seja turma...
        if(this.dir == this.pathTurma){
            do{
                
                System.out.println("Qual unidade?\n"
                        + "1 - Méier I\n"
                        + "2 - Méier II\n"
                        + "3 - Méier III\n"
                        + "4 - Rio Comprido\n"
                        + "5 - Méier V\n");
                
                switch(this.info.next().charAt(0)){
                    case '1':
                        this.dir += "\\Méier I\\";
                        pass = true;
                        break;
                    case '2':
                        this.dir += "\\Méier II\\";
                        pass = true;
                        break;
                    case '3':
                        this.dir += "\\Méier III\\";
                        pass = true;
                        break;
                    case '4':
                        this.dir += "\\Rio Comprido\\";
                        pass = true;
                        break;
                    case '5':
                        this.dir += "\\Méier V\\";
                        pass = true;
                        break;
                    default: 
                        System.out.println("Inválido"); 
                        break;
                }
            } while(pass == false);
                
        }
        
        try{
            //concatena diretório + nome inserido + tipo de arquivo (texto).
            this.cadastro = new File(this.dir + this.nomeCompleto + ".txt");
            //se o arquivo gerado tem nome de um arquivo já existente no mesmo diretório...
            if(this.cadastro.exists()){
                System.out.println("\nArquivo já existe! Deseja substituir-lo (1 Sim, qualquer outro não)?\n");
                if(this.info.nextInt() != 1){
                    this.go = false;
                }
            }

            if(this.go == true){
                //um tanto impossível que a geração de arquivo irá dar errado.
                if(this.cadastro.createNewFile()){
                    System.out.println("\nArquivo Criado!");
                } 
            }

        } catch(IOException e) {
            System.out.println("\nOcorreu um erro!\n");
        }
    }
    
}
