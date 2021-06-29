package ezpos.repositories.interfaces;

import ezpos.model.Venda;
import ezpos.model.VendaItem;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface VendaRepository {
    ObservableList<Venda> listar() throws SQLException;
    Venda buscar(int id) throws SQLException;
    int inserir(Venda venda) throws SQLException;
    ObservableList<VendaItem> listarItems(Venda venda) throws SQLException;
    boolean inserirItem(VendaItem item) throws SQLException;
    boolean excluirItem(VendaItem item) throws SQLException;
    boolean editar(Venda venda) throws SQLException;
    boolean excluir(Venda venda) throws SQLException;
}
