package ezpos.daos;

import ezpos.Main;
import ezpos.daos.interfaces.CompraItemDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import ezpos.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCCompraItemDAO implements CompraItemDAO {
    private static final String INSERIR = "INSERT INTO compra_items(compra_id,produto_id,quantidade,valor) VALUES (?,?,?,?);";
    private static final String EDITAR = "UPDATE compra_items SET quantidade=?, valor=? WHERE compra_id=? AND produto_id=?;";
    private static final String EXCLUIR = "DELETE FROM compra_items WHERE compra_id=? AND produto_id=?;";
    private static final String LISTAR = "SELECT * FROM compra_items WHERE compra_id=?;";

    @Override
    public List<CompraItem> listar(int compra) throws SQLException {
        ArrayList<CompraItem> lista = new ArrayList<>();

        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        stmt.setInt(1, compra);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Compra c = Main.getCompraDAO().buscar(rs.getInt("compra_id"));
            Produto p = Main.getProdutoDAO().buscar(rs.getInt("produto_id"));
            double quantidade = rs.getDouble("quantidade");
            double valor = rs.getDouble("valor");

            lista.add(new CompraItem(c, p, quantidade, valor));
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public boolean inserir(CompraItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR);
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
    public boolean editar(CompraItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EDITAR);
        stmt.setDouble(1, item.getQuantidade());
        stmt.setDouble(2, item.getValor());
        stmt.setInt(3, item.getCompra().getId());
        stmt.setInt(4, item.getProduto().getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean excluir(CompraItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EXCLUIR);
        stmt.setInt(1, item.getCompra().getId());
        stmt.setInt(2, item.getProduto().getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }
}
