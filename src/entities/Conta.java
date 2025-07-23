package entities;

public abstract class Conta implements IConta {
   
    private static int SEQUENCIAL = 1;
    private static int AGENCIA_PADRAO = 0001;

    protected Integer agencia;
    protected Integer numeroConta;
    protected Double saldo;
    
    public Conta(){
        this.agencia = AGENCIA_PADRAO;
        this.numeroConta = SEQUENCIAL++;
    }

     @Override
    public void sacar(double valor) {
        saldo -= valor;
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }
  
    @Override
    public void transferencia(double valor, Conta contaDestino) {
        this.sacar(valor);
        contaDestino.depositar(valor);
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
    
}
