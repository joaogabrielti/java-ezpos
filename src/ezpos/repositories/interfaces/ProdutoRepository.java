package ezpos.repositories.interfaces;

import ezpos.model.Produto;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface ProdutoRepository {
    ObservableList<Produto> listar() throws SQLException;
    Produto buscar(int id) throws SQLException;
    boolean inserir(Produto produto) throws SQLException;
    boolean editar(Produto produto) throws SQLException;
    boolean excluir(Produto produto) throws SQLException;
}
