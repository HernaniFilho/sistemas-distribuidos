
import java.rmi.Naming;

public class Cliente {
    
    public static void main(String[] args) throws Exception {
        try { 
            ICalculadora calc = (ICalculadora) Naming.lookup("rmi://localhost/CalculadoraService");
            
            double a = 5.0, b = 3.0;
            System.out.println("Soma: " + calc.somar(a, b));
            System.out.println("Subtracao: " + calc.subtrair(a, b));
            System.out.println("Multiplicacao: " + calc.multiplicar(a, b));
            System.out.println("Divisao: " + calc.dividir(a, b));
        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
}
