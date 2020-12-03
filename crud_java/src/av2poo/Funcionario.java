package av2poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funcionario extends Membro {
    Pattern rxSalario = Pattern.compile("\\d{1,5}(?:,?\\d{2})?");
    //esse padrão funciona relativamente bem.
    //esse padrão foi modificado, o original se encontra em:
    //https://stackoverflow.com/questions/7716728/regular-expression-or-complete-javascript-to-validate-salary-a-double-number
    Matcher mtSalario = null;
    Matcher mtCargo = null;

    String salario;
    String cargo;
    
    public void SetInfoEspecifico(){
        do {
            System.out.println("\nSalário (formato XXXXX,XX): ");
            this.salario = this.info.nextLine();
            this.mtSalario = this.rxSalario.matcher(this.salario);
        } while(!mtSalario.find());
        
        
        do {
            System.out.println("\nCargo: ");
            this.cargo = this.info.nextLine();
            this.mtCargo = this.rxNome.matcher(this.cargo);
        } while(!mtCargo.find());
        
    }
    
    public void GetInfoEspecifico(){
        System.out.println("Salario: " + this.salario);
        System.out.println("Cargo: " + this.cargo);
    }
    
    public void EscreverCadastro(){
        try {
            this.cadastro = new File(this.dir + this.nomeCompleto + ".txt");
            if (this.cadastro.exists()){
                this.escrever = new FileWriter(this.cadastro);
                this.escrever.write("Nome completo: " + this.nomeCompleto);
                this.escrever.write("\nData de nascimento: " + this.dob);
                this.escrever.write("\nNúmero de celular: " + this.numCelular);
                this.escrever.write("\nCpf: " + this.cpf);
                this.escrever.write("\nEmail: " + this.email);
                this.escrever.write("\nSalário: R$" + this.salario);
                this.escrever.write("\nCargo: " + this.cargo);
                System.out.println("Arquivo Editado!");
                this.escrever.close();
            } 
        } catch(IOException e) {
            System.out.println("\nOcorreu um erro!\n");
        }
    }
    
    public void NovoCadastro(){
        this.SetInfoPessoal();
        this.SetInfoEspecifico();
        this.GetInfoPessoal();
        this.GetInfoEspecifico();
        this.GerarCadastro('4');
        if (this.go == true){
            this.EscreverCadastro();
        }    
        this.info.nextLine();
    }
    
}
