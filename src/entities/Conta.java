package entities;

public abstract class Conta implements IConta {
   
    private static int SEQUENCIAL = 1;
    private static int AGENCIA_PADRAO = 0001;

    protected Integer agencia;
    protected Integer numeroConta;
    protected Double saldo;
    protected Cliente cliente;
    
    public Conta(Cliente cliente){
        this.agencia = AGENCIA_PADRAO;
        this.numeroConta = SEQUENCIAL++;
        this.cliente = cliente;
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

    protected void imprimirInfoComum(){
        System.out.println(String.format("Titular: %s", this.cliente.getName()));
        System.out.println(String.format("Agencia: %d", this.agencia));
        System.out.println(String.format("Numero: %d", this.numeroConta));
        System.out.println(String.format("Saldo: %.2f", this.saldo));
    }
    
}
