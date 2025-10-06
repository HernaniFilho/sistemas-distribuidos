import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {
    public static void main(String[] args) throws Exception {
        try {
            LocateRegistry.createRegistry(1099);
            Calculadora calc = new Calculadora();
            Naming.rebind("rmi://localhost/CalculadoraService", calc);

            System.out.println("Servidor pronto. Calculadora RMI no ar");
        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}
