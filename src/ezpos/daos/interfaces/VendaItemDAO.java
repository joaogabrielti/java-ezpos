package ezpos.daos.interfaces;

import ezpos.model.VendaItem;
import java.sql.SQLException;
import java.util.List;

public interface VendaItemDAO {
    List<VendaItem> listar(int venda) throws SQLException;
    boolean inserir(VendaItem item) throws SQLException;
    boolean editar(VendaItem item) throws SQLException;
    boolean excluir(VendaItem item) throws SQLException;
}
