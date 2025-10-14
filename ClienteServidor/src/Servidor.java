import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
    private static int contadorThreads = 0;

    public static void main(String[] args) {
        int porta = 12345;

        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor iniciado na porta " + porta);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                contadorThreads++;

                Thread t = new Thread(new AtendeCliente(clienteSocket, contadorThreads));
                t.start();

                System.out.println("Nova conexão aceita. Thread_" + contadorThreads + " iniciada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class AtendeCliente implements Runnable {
        private Socket clienteSocket;
        private int idThread;

        public AtendeCliente(Socket clienteSocket, int idThread) {
            this.clienteSocket = clienteSocket;
            this.idThread = idThread;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
            ) {
                String mensagemCliente = in.readLine();
                System.out.println("Thread_" + idThread + " recebeu: " + mensagemCliente);

                String resposta = "Serviço prestado pela thread_" + idThread;
                out.println(resposta);

                System.out.println("Thread_" + idThread + " enviou resposta.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clienteSocket.close();
                    System.out.println("Thread_" + idThread + " finalizada.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
