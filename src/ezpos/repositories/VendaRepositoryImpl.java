package ezpos.repositories;

import ezpos.Main;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import ezpos.repositories.interfaces.VendaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class VendaRepositoryImpl implements VendaRepository {
    private final ObservableList<Venda> vendas;

    public VendaRepositoryImpl() {
        this.vendas = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Venda> listar() throws SQLException {
        this.vendas.clear();
        this.vendas.addAll(Main.getVendaDAO().listar());
        return FXCollections.unmodifiableObservableList(this.vendas);
    }

    @Override
    public Venda buscar(int id) throws SQLException {
        return Main.getVendaDAO().buscar(id);
    }

    @Override
    public int inserir(Venda venda) throws SQLException {
        return Main.getVendaDAO().inserir(venda);
    }

    @Override
    public boolean inserirItem(VendaItem item) throws SQLException {
        return Main.getVendaItemDAO().inserir(item);
    }

    @Override
    public boolean editar(Venda venda) throws SQLException {
        return Main.getVendaDAO().editar(venda);
    }

    @Override
    public boolean excluir(Venda venda) throws SQLException {
        return Main.getVendaDAO().excluir(venda);
    }
}
