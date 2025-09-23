import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class Buffer {
    private final Queue<Integer> fila = new LinkedList<>();

    private final Semaphore mutex = new Semaphore(1);       // exclusão mútua
    private final Semaphore espacos;                        // espaços livres do buffer (empty)
    private final Semaphore itens = new Semaphore(0);       // itens disponíveis (full)

    public Buffer(int capacidade) {
        this.espacos = new Semaphore(capacidade);
    }

    public void produzir(int valor) throws InterruptedException {
        espacos.acquire(); // espera espaço livre
        mutex.acquire();   

        fila.add(valor);
        System.out.println("Produziu: " + valor);

        mutex.release();   
        itens.release();   // sinaliza que há novo item
    }

    public int consumir() throws InterruptedException {
        itens.acquire();   // espera item disponível
        mutex.acquire();   

        int valor = fila.poll();
        System.out.println("Consumiu: " + valor);

        mutex.release();   
        espacos.release(); // libera espaço no buffer
        return valor;
    }
}

class Produtor implements Runnable {
    private final Buffer buffer;

    public Produtor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            int i = 1;
            while (!Thread.currentThread().isInterrupted()) {
                buffer.produzir(i++);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Produtor interrompido.");
            Thread.currentThread().interrupt();
        }
    }
}

class Consumidor implements Runnable {
    private final Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                buffer.consumir();
                Thread.sleep(800);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor interrompido.");
            Thread.currentThread().interrupt();
        }
    }
}

public class ProdutorConsumidorSemaphore {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(5);

        Thread produtor = new Thread(new Produtor(buffer));
        Thread produtor2 = new Thread(new Produtor(buffer));
        Thread produtor3 = new Thread(new Produtor(buffer));
        Thread consumidor = new Thread(new Consumidor(buffer));

        produtor.start();
        produtor2.start();
        produtor3.start();
        consumidor.start();

        Thread.sleep(5000);
        produtor.interrupt();
        produtor2.interrupt();
        produtor3.interrupt();

        Thread.sleep(3000);
        consumidor.interrupt();

        System.out.println("Programa finalizado.");
    }
}
