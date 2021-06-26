package ezpos.daos;

import ezpos.Main;
import ezpos.daos.interfaces.CompraDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Compra;
import ezpos.model.CompraItem;
import ezpos.model.Fornecedor;
import ezpos.model.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCCompraDAO implements CompraDAO {
    private static final String INSERIR = "INSERT INTO compras(fornecedor_id,usuario_id,valor,data) VALUES (?,?,?,?);";
    private static final String EDITAR = "UPDATE compras SET fornecedor_id=?, usuario_id=?, valor=?, data=? WHERE id=?;";
    private static final String EXCLUIR = "DELETE FROM compras WHERE id=?;";
    private static final String LISTAR = "SELECT * FROM compras;";
    private static final String BUSCAR = "SELECT * FROM compras WHERE id=?;";

    @Override
    public List<Compra> listar() throws SQLException {
        ArrayList<Compra> lista = new ArrayList<>();

        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Fornecedor fornecedor = Main.getFornecedorDAO().buscar(rs.getInt("fornecedor_id"));
            Usuario usuario = Main.getUsuarioDAO().buscar(rs.getInt("usuario_id"));
            double valor = rs.getDouble("valor");
            LocalDate data = LocalDate.parse(rs.getString("data"));
            List<CompraItem> items = Main.getCompraItemDAO().listar(id);

            lista.add(new Compra(id, fornecedor, usuario, valor, data, items));
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public Compra buscar(int id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(BUSCAR);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        Compra compra = null;
        if (rs.next()) {
            Fornecedor fornecedor = Main.getFornecedorDAO().buscar(rs.getInt("fornecedor_id"));
            Usuario usuario = Main.getUsuarioDAO().buscar(rs.getInt("usuario_id"));
            double valor = rs.getDouble("valor");
            LocalDate data = LocalDate.parse(rs.getString("data"));
            List<CompraItem> items = Main.getCompraItemDAO().listar(id);

            compra = new Compra(id, fornecedor, usuario, valor, data, items);
        }

        rs.close();
        stmt.close();
        conn.close();

        return compra;
    }

    @Override
    public int inserir(Compra compra) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, compra.getFornecedor().getId());
        stmt.setInt(2, compra.getUsuario().getId());
        stmt.setDouble(3, compra.getValor());
        stmt.setDate(4, Date.valueOf(compra.getData()));

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
    public boolean editar(Compra compra) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EDITAR);
        stmt.setInt(1, compra.getFornecedor().getId());
        stmt.setInt(2, compra.getUsuario().getId());
        stmt.setDouble(3, compra.getValor());
        stmt.setDate(4, Date.valueOf(compra.getData()));
        stmt.setInt(5, compra.getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean excluir(Compra compra) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EXCLUIR);
        stmt.setInt(1, compra.getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }
}
