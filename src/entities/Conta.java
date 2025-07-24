package entities;

import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

public abstract class Conta implements IConta {
    
    private static int SEQUENCIAL = 1;
    private static int AGENCIA_PADRAO = 1; 

    protected Integer agencia;
    protected Integer numeroConta;
    protected Double saldo; 
    protected Cliente cliente;
    
    public Conta(Cliente cliente){
        this.agencia = AGENCIA_PADRAO;
        this.numeroConta = SEQUENCIAL++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    @Override
    public void sacar(double valor) throws SaldoInsuficienteException, ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser positivo e maior que zero.");
        }
        
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque. Saldo atual: R$" + String.format("%.2f", this.saldo));
        }
        
        this.saldo -= valor;
        System.out.println(String.format("Saque de R$%.2f realizado com sucesso. Novo saldo: R$%.2f", valor, this.saldo));
    }

    @Override
    public void depositar(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser positivo e maior que zero.");
        }
        this.saldo += valor;
        System.out.println(String.format("Depósito de R$%.2f realizado com sucesso. Novo saldo: R$%.2f", valor, this.saldo));
    }
    
    @Override
    public void transferencia(double valor, Conta contaDestino) throws SaldoInsuficienteException, ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor da transferência deve ser positivo e maior que zero.");
        }
        
        if (contaDestino == null) {
            throw new ValorInvalidoException("Conta de destino não pode ser nula para a transferência.");
        }

        try {
            this.sacar(valor);
        } catch (SaldoInsuficienteException e) {
            throw new SaldoInsuficienteException("Falha na transferência (conta de origem): " + e.getMessage());
        } catch (ValorInvalidoException e) {
            throw new ValorInvalidoException("Falha na transferência (valor inválido): " + e.getMessage());
        }

        try {
            contaDestino.depositar(valor);
        } catch (ValorInvalidoException e) {
            System.err.println("AVISO: O saque foi realizado, mas o depósito na conta de destino falhou. Considere implementar uma reversão de transação aqui.");
            throw new ValorInvalidoException("Falha na transferência ao depositar na conta destino: " + e.getMessage());
        }
        System.out.println(String.format("Transferência de R$%.2f realizada com sucesso de conta %d para conta %d.",
                                         valor, this.numeroConta, contaDestino.getNumeroConta()));
    }

    public Integer getAgencia() {
        return agencia;
    }
    public Integer getNumeroConta() {
        return numeroConta;
    }
    public Double getSaldo() {
        return saldo;
    }

    protected void imprimirInfoComum(){
        System.out.println(String.format("Titular: %s", this.cliente.getName()));
        System.out.println(String.format("Agencia: %d", this.agencia));
        System.out.println(String.format("Numero: %d", this.numeroConta));
        System.out.println(String.format("Saldo: R$%.2f", this.saldo));
    }
}