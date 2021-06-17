package ezpos.daos.interfaces;

import ezpos.model.Compra;
import ezpos.model.CompraItem;
import java.sql.SQLException;
import java.util.List;

public interface CompraDAO {
    List<Compra> listar() throws SQLException;
    Compra buscar(int id) throws SQLException;
    int inserir(Compra compra) throws SQLException;
    boolean inserirItem(CompraItem item) throws SQLException;
    boolean editar(Compra compra) throws SQLException;
    boolean excluir(Compra compra) throws SQLException;
}
