package ezpos.repositories;

import ezpos.Main;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import ezpos.repositories.interfaces.CompraRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class CompraRepositoryImpl implements CompraRepository {
    private final ObservableList<Compra> compras;
    private final ObservableList<CompraItem> items;

    public CompraRepositoryImpl() {
        this.compras = FXCollections.observableArrayList();
        this.items = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Compra> listar() throws SQLException {
        this.compras.clear();
        this.compras.addAll(Main.getCompraDAO().listar());
        return FXCollections.unmodifiableObservableList(this.compras);
    }

    @Override
    public Compra buscar(int id) throws SQLException {
        return Main.getCompraDAO().buscar(id);
    }

    @Override
    public int inserir(Compra compra) throws SQLException {
        return Main.getCompraDAO().inserir(compra);
    }

    @Override
    public ObservableList<CompraItem> listarItems(Compra compra) throws SQLException {
        this.items.clear();
        this.items.addAll(Main.getCompraItemDAO().listar(compra));
        return FXCollections.unmodifiableObservableList(this.items);
    }

    @Override
    public boolean inserirItem(CompraItem item) throws SQLException {
        return Main.getCompraItemDAO().inserir(item);
    }

    @Override
    public boolean excluirItem(CompraItem item) throws SQLException {
        return Main.getCompraItemDAO().excluir(item);
    }

    @Override
    public boolean editar(Compra compra) throws SQLException {
        return Main.getCompraDAO().editar(compra);
    }

    @Override
    public boolean excluir(Compra compra) throws SQLException {
        return Main.getCompraDAO().excluir(compra);
    }
}
