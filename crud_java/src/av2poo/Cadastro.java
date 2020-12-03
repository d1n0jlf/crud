package av2poo;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Cadastro {
    
    //Class reponsavel por apagar entidades
    public void deletarPessoa(){
        Scanner scOption = new Scanner(System.in);
        Scanner scName   = new Scanner(System.in);

        
        Map<Integer, String> pathList = new HashMap<>();
        
        pathList.put(1, "..\\data\\Alunos\\");
        pathList.put(2, "..\\data\\Professores\\");
        pathList.put(3, "..\\data\\Unidades\\");
        pathList.put(4, "..\\data\\Funcionários\\");
        pathList.put(11, pathList.get(3)+"Méier I\\");
        pathList.put(12, pathList.get(3)+"Méier II\\");
        pathList.put(13, pathList.get(3)+"Méier III\\");
        pathList.put(14, pathList.get(3)+"Rio Comprido\\");
        pathList.put(15, pathList.get(3)+"Méier V\\");
        
        System.out.println("\nQual tipo de usuario deseja deletar?"
                + "(use números)\n"
                + "1 - Aluno\n"
                + "2 - Professor\n"
                + "3 - Turma\n"
                + "4 - Funcionário\n"
        );
            
        int option = scOption.nextInt();
        
        if(pathList.containsKey(option)){
            String currentPath;
            
            if(option != 3){
                currentPath = pathList.get(option);
            }else{
                System.out.println("\nQual a unidade desta tuma?"
                        +"\n1- Méier I"
                        +"\n2- Méier II"
                        +"\n3- Méier III"
                        +"\n4- Rio Comprido"
                        +"\n5- Méier V");
                
                int newoption = scOption.nextInt();
                currentPath = pathList.get(newoption + 10);
            }
            System.out.println("\nQuem você deseja deletar?");
            String name = scName.nextLine();
            
            File entidade = new File(currentPath + name + ".txt");
                if(entidade.exists()){
                    entidade.delete();

                    System.out.println("\nArquivo deletado com sucesso!");
                }else{
                    System.out.println("\nOpção inválida!");
                }
        }else{
            System.out.println("\nOpção inválida!");
        }
    }
    
    //essa é a classe principal.
    public static void main(String[] args){
        List<String> paths = new ArrayList<String>(){{
            add("..\\data\\");
            add(get(0) + "Alunos\\");
            add(get(0) + "Professores\\");
            add(get(0) + "Funcionários\\");
            add(get(0) + "Unidades\\");
            add(get(4) + "Méier I\\");
            add(get(4) + "Méier II\\");
            add(get(4) + "Méier III\\");
            add(get(4) + "Méier V\\");
            add(get(4) + "Rio Comprido\\");
        }};
        Path dados = null;
        File a = null;

        for (String s : paths) {
            dados = Paths.get(s);
            a = new File(dados.toString());
            if (Files.notExists(dados)) {
                System.out.println("Caminho criado: "+dados);
                a.mkdir();
            }
        }
        
        //Aluno, Professor, Turma são subclasses, Membro é a superclasse.
        Professor prof = new Professor();
        Aluno aluno = new Aluno();
        Turma turma = new Turma();
        Funcionario funcionario = new Funcionario();
        Cadastro cadastro = new Cadastro();
        //objetos usados para acessar os métodos relevantes ao contexto pedido.
        try (Scanner select = new Scanner(System.in)) {
            char opcao;
            opcao = ' ';
            
            do {
                System.out.println("\nCadastro Unicarioca"
                        + " (use números)\n"
                        + "1 - Novo Aluno\n"
                        + "2 - Novo Professor\n"
                        + "3 - Nova Turma\n"
                        + "4 - Novo Funcionário\n"
                        + "5 - Carregar Aluno\n"
                        + "6 - Carregar Professor\n"
                        + "7 - Carregar Turma\n"
                        + "8 - Carregar Funcionário\n"
                        + "9 - Deletar Entidade\n"
                        + "0 - Sair\n");
                
                opcao = select.next().charAt(0);
                //usado para selecionar a opção.
                
                switch (opcao){
                    case '1':
                        aluno.NovoCadastro();
                        break;
                    case '2':
                        prof.NovoCadastro();
                        break;
                    case '3':
                        turma.NovoCadastro();
                        break;
                    case '4':
                        funcionario.NovoCadastro();
                        break;    
                    case '5':
                        aluno.CarregarCadastro('1');
                        break;
                    case '6':
                        prof.CarregarCadastro('2');
                        break;
                    case '7':
                        turma.CarregarCadastro('3');
                        break;
                    case '8':
                        funcionario.CarregarCadastro('4');
                        break;
                    case '9':
                        cadastro.deletarPessoa(); 
                    case '0':
                        System.out.println("\nPrograma finalizado!");
                        break;
                    default:
                        System.out.println("\nOpção inválida!");
                        break;
                }
            } while(opcao != '0');
        }
    }
}
