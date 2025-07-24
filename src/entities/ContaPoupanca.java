package entities;

public class ContaPoupanca extends Conta {

    @Override
    public void imprimirExtratoDaConta() {
        System.out.println("=== Extrato Conta Poupança ===");
        super.imprimirInfoComum();
    }

   
 
}
