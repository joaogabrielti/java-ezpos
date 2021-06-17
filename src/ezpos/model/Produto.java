package ezpos.model;

import java.util.Locale;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double quantidade;
    private double valor;

    public Produto(int id, String nome, String descricao, double quantidade, double valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Produto(String nome, String descricao, double quantidade, double valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getEstoqueString() {
        return this.getId() +
                "\t" + this.getNome() +
                "\t" + String.format(Locale.getDefault(), "%.2f", this.getQuantidade()) +
                "\n";
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
