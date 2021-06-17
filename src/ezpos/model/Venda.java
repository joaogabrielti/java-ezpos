package ezpos.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venda {
    private int id;
    private Cliente cliente;
    private double valor;
    private String formaPagamento;
    private LocalDate data;

    public Venda(int id, Cliente cliente, double valor, String formaPagamento, LocalDate data) {
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.data = data;
    }

    public Venda(Cliente cliente, double valor, String formaPagamento, LocalDate data) {
        this.cliente = cliente;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Venda #" + this.getId() + " - "
                + this.getData().format(DateTimeFormatter.ofPattern("dd/MM/yy"))
                + " - R$ " + this.getValor();
    }
}
