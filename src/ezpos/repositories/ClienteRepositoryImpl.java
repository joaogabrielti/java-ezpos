package ezpos.repositories;

import ezpos.Main;
import ezpos.model.Cliente;
import ezpos.repositories.interfaces.ClienteRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class ClienteRepositoryImpl implements ClienteRepository {
    private final ObservableList<Cliente> clientes;

    public ClienteRepositoryImpl() {
        this.clientes = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Cliente> listar() throws SQLException {
        this.clientes.clear();
        this.clientes.addAll(Main.getClienteDAO().listar());
        return FXCollections.unmodifiableObservableList(this.clientes);
    }

    @Override
    public Cliente buscar(int id) throws SQLException {
        return Main.getClienteDAO().buscar(id);
    }

    @Override
    public boolean inserir(Cliente cliente) throws SQLException {
        return Main.getClienteDAO().inserir(cliente);
    }

    @Override
    public boolean editar(Cliente cliente) throws SQLException {
        return Main.getClienteDAO().editar(cliente);
    }

    @Override
    public boolean excluir(Cliente cliente) throws SQLException {
        return Main.getClienteDAO().excluir(cliente);
    }
}
