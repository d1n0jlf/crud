package av2poo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Aluno extends Membro {
    float mensalidade;
    String qteDisciplinas, desconto;

    Pattern rxInt = Pattern.compile("[0-9]");

    Matcher mtQte = null;
    Matcher mtDesc = null;

    public void SetInfoEspecifico(){
        do {
            System.out.println("\nQuantidade de disciplinas: ");
            this.qteDisciplinas = this.info.nextLine();
            this.mtQte = this.rxInt.matcher(this.qteDisciplinas);
        } while(!this.mtQte.find());
        do{
            System.out.println("Porcentagem desconto (somente valor): ");
            this.desconto = this.info.nextLine();
            this.mtDesc = this.rxInt.matcher(this.desconto);
        } while(!this.mtDesc.find());
        int q = Integer.parseInt(this.qteDisciplinas);
        int d = Integer.parseInt(this.desconto);
        //esse 189.99 foi o valor dado por um membro do atendimento unicarioca para mim quando perguntado sobre valor das disciplinas do curso
        this.mensalidade = ((float)189.99 * q * d)/100;
    }
    
    public void GetInfoEspecifico(){
        System.out.println("Mensalidade: " + this.mensalidade);
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
                this.escrever.write("\nQuantidade de disciplinas: " + this.qteDisciplinas);
                this.escrever.write("\nDesconto: " + this.desconto + "%");
                this.escrever.write("\nMensalidade: " + this.mensalidade);
                System.out.println("Arquivo Editado!");
                this.escrever.close();
            } 
        } catch (IOException e) {
            System.out.println("\nOcorreu um erro!\n");
        }
    }
    
    public void NovoCadastro(){
        this.SetInfoPessoal();
        this.SetInfoEspecifico();
        this.GetInfoPessoal();
        this.GetInfoEspecifico();
        this.GerarCadastro('1');
        if (this.go == true){
            this.EscreverCadastro();
        }
        this.info.nextLine();
    }
}
