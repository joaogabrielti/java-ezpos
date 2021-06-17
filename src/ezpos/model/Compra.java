package ezpos.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Compra {
    private int id;
    private Fornecedor fornecedor;
    private double valor;
    private LocalDate data;

    public Compra(int id, Fornecedor fornecedor, double valor, LocalDate data) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.valor = valor;
        this.data = data;
    }

    public Compra(Fornecedor fornecedor, double valor, LocalDate data) {
        this.fornecedor = fornecedor;
        this.valor = valor;
        this.data = data;
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

    @Override
    public String toString() {
        return "Compra #" + this.getId() + " - "
                + this.getData().format(DateTimeFormatter.ofPattern("dd/MM/yy"))
                + " - R$ " + this.getValor();
    }
}
