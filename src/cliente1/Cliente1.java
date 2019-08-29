package cliente1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import util.Mensagem;
import util.enumConfi.Impressoras;
import util.enumConfi.QualidadeImpressao;

public class Cliente1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        Socket soquete = new Socket("localhost",5555);
        
        Mensagem mensagem = new Mensagem();
        mensagem.conteudo = "TEXTO";
        mensagem.frenteVerso = false;
        mensagem.modeloImpressora = Impressoras.HP8600;
        mensagem.numeroCopias = 1;
        mensagem.qualidade = QualidadeImpressao.NORMAL;
   
        
        //criando os streams
        ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(soquete.getInputStream());
        
        /*
        Scanner teclado = new Scanner(System.in);
        
        String palavra;
        
        System.out.println("Digite uma palavra: ");
        palavra = teclado.next();        
        */
        //enviando a mensagem
        saida.writeObject(mensagem);
        saida.flush();
                
        //receber a resposta
        String resposta = (String) entrada.readObject();
        
        System.out.println("CLIENTE::RESPOSTA DO SERVIDOR: "+resposta);
        
        
    }
    
}
