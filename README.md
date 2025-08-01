# Banco Digital

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Status](https://img.shields.io/badge/status-concluído-green?style=for-the-badge)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)


## 📖 Sobre o Projeto

O **Banco Digital** foi criado como um desafio para aplicar de forma prática os quatro pilares da Programação Orientada a Objetos (POO): **Abstração, Encapsulamento, Herança e Polimorfismo**. O sistema permite a criação de contas bancárias (corrente e poupança) e a realização de operações financeiras básicas através de uma interface de linha de comando interativa.

O projeto se destaca pela sua estrutura organizada, separação de responsabilidades e um robusto sistema de tratamento de erros utilizando exceções customizadas.

---

## ✨ Funcionalidades

* ✅ **Criação de Contas**: Permite ao usuário criar contas do tipo Corrente ou Poupança.
* ✅ **Operações Bancárias**: Suporte para Depósito, Saque e Transferência entre contas.
* ✅ **Impressão de Extrato**: Exibe um extrato detalhado com as informações do titular e o saldo atual.
* ✅ **Tratamento de Erros**: Validações para impedir operações com valores inválidos ou saldo insuficiente.
* ✅ **Interface Interativa**: Navegação por menus no console para uma fácil utilização.

---

## 🏛️ Diagrama UML do Sistema

O diagrama abaixo ilustra a arquitetura do projeto, mostrando as relações de herança, implementação e associação entre as classes e interfaces.

```mermaid
classDiagram
    direction TD

    class App {
        <<Application>>
        -sc: Scanner
        -banco: Banco
        +main(String[] args)
        -operarConta(Conta)
        -criarNovaConta()
    }

    class Banco {
        -name: String
        -contas: List~Conta~
    }

    class Cliente {
        -name: String
    }

    class IConta {
        <<Interface>>
        +depositar(double)
        +sacar(double)
        +transferencia(double, Conta)
        +imprimirExtratoDaConta()
    }

    class Conta {
        <<Abstract>>
        #agencia: Integer
        #numeroConta: Integer
        #saldo: Double
        #cliente: Cliente
        +sacar(double)
        +depositar(double)
    }

    class ContaCorrente {
        +imprimirExtratoDaConta()
    }

    class ContaPoupanca {
        +imprimirExtratoDaConta()
    }
    
    class SaldoInsuficienteException { <<Exception>> }
    class ValorInvalidoException { <<Exception>> }

    App ..> Banco : uses
    App ..> Conta : uses
    App ..> Cliente : uses
    App ..> SaldoInsuficienteException : handles
    
    Banco "1" o-- "*" Conta : has
    Conta "1" *-- "1" Cliente : owned by

    Conta ..|> IConta : implements
    ContaCorrente --|> Conta : extends
    ContaPoupanca --|> Conta : extends

    Conta ..> SaldoInsuficienteException : throws
    Conta ..> ValorInvalidoException : throws
   
   ---

   ## 🛠️ Tecnologias Utilizadas

   * **[Java](https://www.java.com/)**: Linguagem principal do projeto.
   * **[Mermaid](https://mermaid.js.org/)**: Ferramenta para a criação do diagrama UML.

   ---

Com certeza. Seguindo o mesmo padrão, aqui estão os códigos Markdown para as seções "Como Executar o Projeto", "Como Usar" e "Licença", com os ícones.

````markdown
---

## 🚀 Como Executar o Projeto

Para executar este projeto localmente, siga os passos abaixo.

### Pré-requisitos

* É necessário ter o **JDK (Java Development Kit)**, versão 17 ou superior, instalado em sua máquina.
* Um editor de código ou IDE de sua preferência (Ex: VS Code, IntelliJ IDEA, Eclipse).

### Instalação e Execução

1.  Clone o repositório (substitua `seu-usuario` pelo seu nome de usuário do GitHub):
    ```sh
    git clone [https://github.com/seu-usuario/banco-digital-java.git](https://github.com/seu-usuario/banco-digital-java.git)
    ```
2.  Navegue até o diretório do projeto:
    ```sh
    cd banco-digital-java
    ```
3.  Compile os arquivos `.java`. Supondo que seus arquivos estejam na pasta `src`, o seguinte comando irá compilar e colocar os `.class` na pasta `bin`:
    ```sh
    javac -d bin -sourcepath src src/application/App.java
    ```
4.  Execute a aplicação:
    ```sh
    java -cp bin application.App
    ```

---

## 🕹️ Como Usar

Ao iniciar o programa, um menu principal será exibido:

````

\+===================================+
|           BANCO DIGITAL           |
\+===================================+
| 1. Criar Nova Conta               |
| 2. Acessar Conta Existente        |
|                                   |
| 0. Sair do Sistema                |
\+===================================+

```

1.  **Para criar uma conta**, escolha a opção `1`, informe o nome do titular e selecione o tipo de conta (Corrente ou Poupança).
2.  **Para operar uma conta**, escolha a opção `2`, selecione a conta desejada na lista e utilize o menu de operações para depositar, sacar, transferir ou ver o extrato.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---
```