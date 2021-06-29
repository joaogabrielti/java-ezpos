package ezpos.daos.interfaces;

import ezpos.model.Compra;
import ezpos.model.CompraItem;
import java.sql.SQLException;
import java.util.List;

public interface CompraItemDAO {
    List<CompraItem> listar(Compra compra) throws SQLException;
    boolean inserir(CompraItem item) throws SQLException;
    boolean editar(CompraItem item) throws SQLException;
    boolean excluir(CompraItem item) throws SQLException;
}
