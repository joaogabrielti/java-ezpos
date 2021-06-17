package ezpos.repositories.interfaces;

import ezpos.model.Fornecedor;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface FornecedorRepository {
    ObservableList<Fornecedor> listar() throws SQLException;
    Fornecedor buscar(int id) throws SQLException;
    boolean inserir(Fornecedor fornecedor) throws SQLException;
    boolean editar(Fornecedor fornecedor) throws SQLException;
    boolean excluir(Fornecedor fornecedor) throws SQLException;
}
