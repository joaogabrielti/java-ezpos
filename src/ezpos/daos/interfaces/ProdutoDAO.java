package ezpos.daos.interfaces;

import ezpos.model.Produto;
import java.sql.SQLException;
import java.util.List;

public interface ProdutoDAO {
    List<Produto> listar() throws SQLException;
    Produto buscar(int id) throws SQLException;
    boolean inserir(Produto produto) throws SQLException;
    boolean editar(Produto produto) throws SQLException;
    boolean excluir(Produto produto) throws SQLException;
    boolean atualizarEstoque(Produto produto) throws SQLException;
}
