import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        int numeroClientes = 4;

        for (int i = 1; i <= numeroClientes; i++) {
            int id = i;
            Thread clienteThread = new Thread(() -> executarCliente(id));
            clienteThread.start();
        }
    }

    public static void executarCliente(int id) {
        String servidor = "localhost";
        int porta = 12345;

        try {
            // Tempo aleatório para simular clientes chegando em momentos diferentes
            Thread.sleep((long) (Math.random() * 2000));

            Socket socket = new Socket(servidor, porta);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensagem = "Cliente_" + id + ": Pode me prestar um serviço?";
            System.out.println("Cliente_" + id + " enviando: " + mensagem);
            out.println(mensagem);

            String resposta = in.readLine();
            System.out.println("Cliente_" + id + " recebeu: " + resposta);

            socket.close();

        } catch (IOException e) {
            System.err.println("Erro no Cliente_" + id + ": " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Cliente_" + id + " interrompido: " + e.getMessage());
        }
    }
}
