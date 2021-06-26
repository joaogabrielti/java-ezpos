package ezpos.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private Usuario usuario;
    private double valor;
    private String formaPagamento;
    private LocalDate data;
    private List<VendaItem> items;

    public Venda(int id, Cliente cliente, Usuario usuario, double valor, String formaPagamento, LocalDate data, List<VendaItem> items) {
        this.id = id;
        this.cliente = cliente;
        this.usuario = usuario;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.data = data;
        this.items = items;
    }

    public Venda(Cliente cliente, Usuario usuario, double valor, String formaPagamento, LocalDate data, List<VendaItem> items) {
        this.cliente = cliente;
        this.usuario = usuario;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.data = data;
        this.items = items;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<VendaItem> getItems() {
        return items;
    }

    public void setItems(List<VendaItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Venda #" + this.getId() + " - "
                + this.getData().format(DateTimeFormatter.ofPattern("dd/MM/yy"))
                + " - R$ " + this.getValor();
    }
}
