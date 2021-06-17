package ezpos.repositories;

import ezpos.daos.interfaces.CompraDAO;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import ezpos.repositories.interfaces.CompraRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class CompraRepositoryImpl implements CompraRepository {
    private final ObservableList<Compra> compras;
    private final CompraDAO compraDAO;

    public CompraRepositoryImpl(CompraDAO compraDAO) {
        this.compras = FXCollections.observableArrayList();
        this.compraDAO = compraDAO;
    }

    @Override
    public ObservableList<Compra> listar() throws SQLException {
        this.compras.clear();
        this.compras.addAll(this.compraDAO.listar());
        return FXCollections.unmodifiableObservableList(this.compras);
    }

    @Override
    public Compra buscar(int id) throws SQLException {
        return this.compraDAO.buscar(id);
    }

    @Override
    public int inserir(Compra compra) throws SQLException {
        return this.compraDAO.inserir(compra);
    }

    @Override
    public boolean inserirItem(CompraItem item) throws SQLException {
        return this.compraDAO.inserirItem(item);
    }

    @Override
    public boolean editar(Compra compra) throws SQLException {
        return this.compraDAO.editar(compra);
    }

    @Override
    public boolean excluir(Compra compra) throws SQLException {
        return this.compraDAO.excluir(compra);
    }
}
