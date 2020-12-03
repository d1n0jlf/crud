package av2poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Turma extends Membro {
    String nomeTurma, profTurma, disciplina;
    ArrayList<String> alunos = new ArrayList<>(); //guarda alunos adicionados na atual sessão
    
    public void SetInfoTurma(){
        System.out.println("\nNome da turma: ");
        this.nomeTurma = this.info.nextLine();
        System.out.println("Disciplina: ");
        this.disciplina = this.info.nextLine();

        boolean pass = false;
        
        while(pass==false) {
            
            do {
                System.out.println("\nNome do aluno (entradas únicas, 0 para parar): " 
                                   + "\nQuantidade atual: " + this.alunos.size());
                this.nomeCompleto = info.nextLine();
            } while (this.alunos.contains(this.nomeCompleto + ".txt"));

            //caso 0 seja lido, quebra o loop.
            if (this.nomeCompleto.indexOf('0')!=-1){
                pass = true;
            }
            
            this.cadastro = new File(this.pathAluno + this.nomeCompleto + ".txt");
            
            //os nomes de alunos são adicionados baseado no nome de arquivos existentes.
            if (this.cadastro.exists()){
                alunos.add(this.cadastro.getName());
            } else {
                if (pass==false){
                    System.out.println("Não existe!");
                } 
            }

            
        } 
        //o nome do professor adicionado é baseado no nome de arquivo existente.
        do {
            this.cadastro = null;
            System.out.println("\nNome do professor: ");
            this.profTurma = this.info.nextLine();
            this.cadastro = new File(this.pathProf + this.profTurma + ".txt");
            this.profTurma = this.cadastro.getName();
        } while (!this.cadastro.exists());
        
        //nome de turma vira nome do arquivo.
        this.nomeCompleto = this.nomeTurma;
        
    }
    
    public void GetInfoTurma(){
        System.out.println("Nome da turma: " + this.nomeTurma);
        System.out.println("Disciplina: " + this.disciplina);
        System.out.println("Professor: " + this.profTurma.substring(0, this.profTurma.lastIndexOf(".")));
        System.out.println("Quantidade de alunos: " + this.alunos.size());
    }       
    
    public void EscreverCadastro(){
        try {
            this.cadastro = new File(this.dir + this.nomeTurma + ".txt");
            if (this.cadastro.exists()){
                this.escrever = new FileWriter(this.cadastro);
                this.escrever.write("Nome da turma: " + this.nomeTurma);
                this.escrever.write("\nDisciplina: " + this.disciplina);
                this.escrever.write("\nProfessor: " + this.profTurma.substring(0, this.profTurma.lastIndexOf(".")));
                this.escrever.write("\nQuantidade de alunos: " + this.alunos.size());
                this.escrever.write("\nAlunos da turma:\n"); 
                //esse snippet foi modificado, fonte:
                //https://stackoverflow.com/questions/10168066/how-to-print-out-all-the-elements-of-a-list-in-java/26901895
                for (String i : this.alunos){
                    this.escrever.write("[" + i.substring(0, i.lastIndexOf(".")) + "]");
                }

                System.out.println("Arquivo Editado!");
                this.escrever.close();
            } 
        } catch(IOException e) {
            System.out.println("\nOcorreu um erro!\n");
        }
        
        
    }
    
    public void NovoCadastro(){
        this.SetInfoTurma();
        this.GetInfoTurma();
        this.GerarCadastro('3');
        if (this.go == true){
            this.EscreverCadastro();
        }
        this.info.nextLine();
    }
    
}
