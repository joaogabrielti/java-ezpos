package ezpos.repositories;

import ezpos.daos.interfaces.VendaDAO;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import ezpos.repositories.interfaces.VendaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class VendaRepositoryImpl implements VendaRepository {
    private final ObservableList<Venda> vendas;
    private final VendaDAO vendaDAO;

    public VendaRepositoryImpl(VendaDAO vendaDAO) {
        this.vendas = FXCollections.observableArrayList();
        this.vendaDAO = vendaDAO;
    }

    @Override
    public ObservableList<Venda> listar() throws SQLException {
        this.vendas.clear();
        this.vendas.addAll(this.vendaDAO.listar());
        return FXCollections.unmodifiableObservableList(this.vendas);
    }

    @Override
    public Venda buscar(int id) throws SQLException {
        return this.vendaDAO.buscar(id);
    }

    @Override
    public int inserir(Venda venda) throws SQLException {
        return this.vendaDAO.inserir(venda);
    }

    @Override
    public boolean inserirItem(VendaItem item) throws SQLException {
        return this.vendaDAO.inserirItem(item);
    }

    @Override
    public boolean editar(Venda venda) throws SQLException {
        return this.vendaDAO.editar(venda);
    }

    @Override
    public boolean excluir(Venda venda) throws SQLException {
        return this.vendaDAO.excluir(venda);
    }
}
