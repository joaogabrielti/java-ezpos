package ezpos.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Compra {
    private int id;
    private Fornecedor fornecedor;
    private Usuario usuario;
    private double valor;
    private LocalDate data;
    private List<CompraItem> items;

    public Compra(int id, Fornecedor fornecedor, Usuario usuario, double valor, LocalDate data, List<CompraItem> items) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.usuario = usuario;
        this.valor = valor;
        this.data = data;
        this.items = items;
    }

    public Compra(Fornecedor fornecedor, Usuario usuario, double valor, LocalDate data, List<CompraItem> items) {
        this.fornecedor = fornecedor;
        this.usuario = usuario;
        this.valor = valor;
        this.data = data;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<CompraItem> getItems() {
        return items;
    }

    public void setItems(List<CompraItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Compra #" + this.getId() + " - "
                + this.getData().format(DateTimeFormatter.ofPattern("dd/MM/yy"))
                + " - R$ " + this.getValor();
    }
}
