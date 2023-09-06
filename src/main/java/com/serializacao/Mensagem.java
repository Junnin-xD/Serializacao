package com.serializacao;

import java.io.Serializable;

public class Mensagem implements Serializable {

	private String nome;
    private String idade;
    private String cpf;
    private String msg;

    public Mensagem() {
        super();
    }

    public Mensagem(String nome, String idade, String cpf, String msg) {
        super();
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.msg = msg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Mensagem [nome=" + nome + ", idade=" + idade + ", cpf=" + cpf + ", msg=" + msg + "]";
    }
	
}