package ezpos.model;

import java.util.Locale;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double quantidade;
    private double valor_compra;
    private double valor_venda;

    public Produto(int id, String nome, String descricao, double quantidade, double valor_compra, double valor_venda) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor_compra = valor_compra;
        this.valor_venda = valor_venda;
    }

    public Produto(String nome, String descricao, double quantidade, double valor_compra, double valor_venda) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor_compra = valor_compra;
        this.valor_venda = valor_venda;
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

    public double getValorCompra() {
        return valor_compra;
    }

    public void setValorCompra(double valor_compra) {
        this.valor_compra = valor_compra;
    }

    public double getValorVenda() {
        return valor_venda;
    }

    public void setValorVenda(double valor_venda) {
        this.valor_venda = valor_venda;
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
