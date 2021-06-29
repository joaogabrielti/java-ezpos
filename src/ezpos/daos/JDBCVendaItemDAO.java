package ezpos.daos;

import ezpos.Main;
import ezpos.daos.interfaces.VendaItemDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Produto;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCVendaItemDAO implements VendaItemDAO {
    private static final String INSERIR = "INSERT INTO venda_itens(venda_id,produto_id,quantidade,valor) VALUES (?,?,?,?);";
    private static final String EDITAR = "UPDATE venda_itens SET quantidade=?, valor=? WHERE venda_id=? AND produto_id=?;";
    private static final String EXCLUIR = "DELETE FROM venda_itens WHERE venda_id=? AND produto_id=?;";
    private static final String LISTAR = "SELECT * FROM venda_itens WHERE venda_id=?;";

    @Override
    public List<VendaItem> listar(Venda venda) throws SQLException {
        ArrayList<VendaItem> lista = new ArrayList<>();

        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        stmt.setInt(1, venda.getId());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Produto p = Main.getProdutoDAO().buscar(rs.getInt("produto_id"));
            double quantidade = rs.getDouble("quantidade");
            double valor = rs.getDouble("valor");

            lista.add(new VendaItem(venda, p, quantidade, valor));
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public boolean inserir(VendaItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR);
        stmt.setInt(1, item.getVenda().getId());
        stmt.setInt(2, item.getProduto().getId());
        stmt.setDouble(3, item.getQuantidade());
        stmt.setDouble(4, item.getValor());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean editar(VendaItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EDITAR);
        stmt.setDouble(1, item.getQuantidade());
        stmt.setDouble(2, item.getValor());
        stmt.setInt(3, item.getVenda().getId());
        stmt.setInt(4, item.getProduto().getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean excluir(VendaItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EXCLUIR);
        stmt.setInt(1, item.getVenda().getId());
        stmt.setInt(2, item.getProduto().getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }
}
