package ezpos.daos.interfaces;

import ezpos.model.Venda;
import java.sql.SQLException;
import java.util.List;

public interface VendaDAO {
    List<Venda> listar() throws SQLException;
    Venda buscar(int id) throws SQLException;
    int inserir(Venda venda) throws SQLException;
    boolean editar(Venda venda) throws SQLException;
    boolean excluir(Venda venda) throws SQLException;
}
