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

    public CompraRepositoryImpl() {
        this.compras = FXCollections.observableArrayList();
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
    public boolean inserirItem(CompraItem item) throws SQLException {
        return Main.getCompraItemDAO().inserir(item);
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
