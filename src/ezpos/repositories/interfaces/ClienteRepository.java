package ezpos.repositories.interfaces;

import ezpos.model.Cliente;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface ClienteRepository {
    ObservableList<Cliente> listar() throws SQLException;
    Cliente buscar(int id) throws SQLException;
    boolean inserir(Cliente cliente) throws SQLException;
    boolean editar(Cliente cliente) throws SQLException;
    boolean excluir(Cliente cliente) throws SQLException;
}
