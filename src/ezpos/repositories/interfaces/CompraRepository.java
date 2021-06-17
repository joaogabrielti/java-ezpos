package ezpos.repositories.interfaces;

import ezpos.model.Compra;
import ezpos.model.CompraItem;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface CompraRepository {
    ObservableList<Compra> listar() throws SQLException;
    Compra buscar(int id) throws SQLException;
    int inserir(Compra compra) throws SQLException;
    boolean inserirItem(CompraItem item) throws SQLException;
    boolean editar(Compra compra) throws SQLException;
    boolean excluir(Compra compra) throws SQLException;
}
