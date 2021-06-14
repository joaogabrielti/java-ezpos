package ezpos.repositories;

import ezpos.daos.interfaces.ClienteDAO;
import ezpos.model.Cliente;
import ezpos.repositories.interfaces.ClienteRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class ClienteRepositoryImpl implements ClienteRepository {
    private final ObservableList<Cliente> clientes;
    private final ClienteDAO clienteDAO;

    public ClienteRepositoryImpl(ClienteDAO clienteDAO) {
        this.clientes = FXCollections.observableArrayList();
        this.clienteDAO = clienteDAO;
    }

    @Override
    public ObservableList<Cliente> listar() throws SQLException {
        this.clientes.clear();
        this.clientes.addAll(this.clienteDAO.listar());
        return FXCollections.unmodifiableObservableList(this.clientes);
    }

    @Override
    public Cliente buscar(int id) throws SQLException {
        return this.clienteDAO.buscar(id);
    }

    @Override
    public boolean inserir(Cliente cliente) throws SQLException {
        return this.clienteDAO.inserir(cliente);
    }

    @Override
    public boolean editar(Cliente cliente) throws SQLException {
        return this.clienteDAO.editar(cliente);
    }

    @Override
    public boolean excluir(Cliente cliente) throws SQLException {
        return this.clienteDAO.excluir(cliente);
    }
}
