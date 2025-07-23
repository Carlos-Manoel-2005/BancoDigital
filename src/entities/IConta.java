package entities;

public interface IConta {
    void depositar(double valor);
    void sacar (double valor);
    void transferencia(double valor, Conta contaDestino);
    void imprimirExtratoDaConta();
}
