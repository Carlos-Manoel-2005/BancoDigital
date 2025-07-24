package entities;

import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public interface IConta {
    void depositar(double valor);
    void sacar (double valor) throws SaldoInsuficienteException, ValorInvalidoException;
    void transferencia(double valor, Conta contaDestino) throws SaldoInsuficienteException, ValorInvalidoException;
    void imprimirExtratoDaConta();
}
