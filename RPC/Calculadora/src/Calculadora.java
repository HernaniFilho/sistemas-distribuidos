
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora extends UnicastRemoteObject implements ICalculadora{
    
    protected Calculadora() throws RemoteException{
        super();
    }

    @Override
    public double somar(double a, double b) throws RemoteException {
        double result = a + b;
        System.out.println("[Servidor] Somando " + a + " + " + b + " = " + result);
        return result;
    }

    @Override
    public double subtrair(double a, double b) throws RemoteException {
        double result = a - b;
        System.out.println("[Servidor] Subtraindo " + a + " - " + b + " = " + result);
        return result;
    }

    @Override
    public double multiplicar(double a, double b) throws RemoteException {
        double result = a * b;
        System.out.println("[Servidor] Multiplicando " + a + " * " + b + " = " + result);
        return result;
    }

    @Override
    public double dividir(double a, double b) throws RemoteException {
        if(b == 0) throw new ArithmeticException("Divisao por zero nao eh permitida");
        double result = a / b;
        System.out.println("[Servidor] Dividindo " + a + " / " + b + " = " + result);
        return result;
    }
}
