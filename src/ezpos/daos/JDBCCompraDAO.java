package ezpos.daos;

import ezpos.daos.interfaces.CompraDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import java.sql.*;
import java.util.List;

public class JDBCCompraDAO implements CompraDAO {
    private static final String INSERIR = "INSERT INTO compras(fornecedor_id,usuario_id,valor,data) VALUES (?,1,?,?);";
    private static final String INSERIR_ITEM = "INSERT INTO compra_itens(compra_id,produto_id,quantidade,valor) VALUES (?,?,?,?);";

    @Override
    public List<Compra> listar() throws SQLException {
        return null;
    }

    @Override
    public Compra buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public int inserir(Compra compra) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, compra.getFornecedor().getId());
        stmt.setDouble(2, compra.getValor());
        stmt.setDate(3, Date.valueOf(compra.getData()));

        int result = stmt.executeUpdate();

        int id = -1;
        if (result == 1) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
        }

        stmt.close();
        conn.close();

        return id;
    }

    @Override
    public boolean inserirItem(CompraItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR_ITEM);
        stmt.setInt(1, item.getCompra().getId());
        stmt.setInt(2, item.getProduto().getId());
        stmt.setDouble(3, item.getQuantidade());
        stmt.setDouble(4, item.getValor());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean editar(Compra compra) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Compra compra) throws SQLException {
        return false;
    }
}
