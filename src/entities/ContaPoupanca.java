package entities;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void imprimirExtratoDaConta() {
        System.out.println("=== Extrato Conta Poupança ===");
        super.imprimirInfoComum();
    }

   
 
}
