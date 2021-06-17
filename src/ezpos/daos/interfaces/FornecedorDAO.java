package ezpos.daos.interfaces;

import ezpos.model.Fornecedor;
import java.sql.SQLException;
import java.util.List;

public interface FornecedorDAO {
    List<Fornecedor> listar() throws SQLException;
    Fornecedor buscar(int id) throws SQLException;
    boolean inserir(Fornecedor fornecedor) throws SQLException;
    boolean editar(Fornecedor fornecedor) throws SQLException;
    boolean excluir(Fornecedor fornecedor) throws SQLException;
}
