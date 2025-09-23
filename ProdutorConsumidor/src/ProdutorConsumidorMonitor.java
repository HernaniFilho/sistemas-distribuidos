import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final Queue<Integer> fila = new LinkedList<>();
    private final int capacidade;

    public Buffer(int capacidade) {
        this.capacidade = capacidade;
    }

    public synchronized void produzir(int valor) throws InterruptedException {
        while (fila.size() == capacidade) {
            wait(); 
        }
        fila.add(valor);
        System.out.println("Produziu: " + valor);
        notifyAll(); 
    }

    public synchronized int consumir() throws InterruptedException {
        while (fila.isEmpty()) {
            wait(); 
        }
        int valor = fila.poll();
        System.out.println("Consumiu: " + valor);
        notifyAll(); 
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

public class ProdutorConsumidorMonitor {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(5);

        Thread produtor = new Thread(new Produtor(buffer));
        Thread consumidor = new Thread(new Consumidor(buffer));

        produtor.start();
        consumidor.start();

        Thread.sleep(5000);
        produtor.interrupt();

        Thread.sleep(3000);
        consumidor.interrupt();

        System.out.println("Programa finalizado.");
    }
}
