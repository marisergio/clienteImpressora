package cliente1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import util.Mensagem;
import util.MensagemImprimir;
import util.enumConfi.Impressoras;
import util.enumConfi.Operacao;
import util.enumConfi.QualidadeImpressao;
import util.enumConfi.StatusMensagem;

public class Cliente1 implements Serializable {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket soquete = new Socket("localhost", 5555);

        MensagemImprimir impressao = new MensagemImprimir();
        impressao.conteudo = "TEXTO";
        impressao.frenteVerso = false;
        impressao.modeloImpressora = Impressoras.HP8600;
        impressao.numeroCopias = 1;
        impressao.qualidade = QualidadeImpressao.NORMAL;
        impressao.operacao = Operacao.IMPRIMIR;
        
        Mensagem mensagem;
        
        //criando os streams
        ObjectOutputStream saida = new ObjectOutputStream(soquete.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(soquete.getInputStream());

        Operacao op;
        Scanner teclado = new Scanner(System.in);
        do {
            mensagem = new Mensagem();
            System.out.println("Escolha a opção da operação:\n"
                    + "0 - IMPRIMIR\n1 - SAIR\n");
            int posicao = teclado.nextInt();
            for (Operacao o : Operacao.values()) {
                if (o.ordinal() == posicao) {
                    mensagem.operacao = o;
                }
            }
            
            //enviando a mensagem
            saida.writeObject(mensagem);
            saida.flush();

            //receber a resposta
            Mensagem resposta = (Mensagem) entrada.readObject();
            System.out.println("CLIENTE::RESPOSTA DO SERVIDOR: " + resposta.statusMensagem);
            
            if(resposta.statusMensagem.equals(StatusMensagem.OK) && mensagem.operacao.equals(Operacao.IMPRIMIR)){
                saida.writeObject(impressao);
                saida.flush();
                resposta = (Mensagem) entrada.readObject();
                System.out.println("CLIENTE::RESPOSTA DO SERVIDOR: " + resposta.statusMensagem);                
            }

        } while (mensagem.operacao != Operacao.SAIR);
    }
}
