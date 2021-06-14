package ezpos.daos.interfaces;

import ezpos.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> listar() throws SQLException;
    Cliente buscar(int id) throws SQLException;
    boolean inserir(Cliente cliente) throws SQLException;
    boolean editar(Cliente cliente) throws SQLException;
    boolean excluir(Cliente cliente) throws SQLException;
}
