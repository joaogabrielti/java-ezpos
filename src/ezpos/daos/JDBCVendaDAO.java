package ezpos.daos;

import ezpos.Main;
import ezpos.daos.interfaces.VendaDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Cliente;
import ezpos.model.Usuario;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCVendaDAO implements VendaDAO {
    private static final String INSERIR = "INSERT INTO vendas(cliente_id,usuario_id,valor,forma_pagamento,data) VALUES (?,?,?,?,?);";
    private static final String EDITAR = "UPDATE vendas SET cliente_id=?, usuario_id=?, valor=?, forma_pagamento=?, data=? WHERE id=?;";
    private static final String EXCLUIR = "DELETE FROM vendas WHERE id=?;";
    private static final String LISTAR = "SELECT * FROM vendas;";

    @Override
    public List<Venda> listar() throws SQLException {
        ArrayList<Venda> lista = new ArrayList<>();

        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Cliente cliente = Main.getClienteDAO().buscar(rs.getInt("cliente_id"));
            Usuario usuario = Main.getUsuarioDAO().buscar(rs.getInt("usuario_id"));
            double valor = rs.getDouble("valor");
            String formaPagamento = rs.getString("forma_pagamento");
            LocalDate data = LocalDate.parse(rs.getString("data").substring(0, 10));

            Venda v = new Venda(id, cliente, usuario, valor, formaPagamento, data, null);
            List<VendaItem> items = Main.getVendaItemDAO().listar(v);

            v.setItems(items);

            lista.add(v);
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public Venda buscar(int id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        ResultSet rs = stmt.executeQuery();

        Venda venda = null;
        if (rs.next()) {
            Cliente cliente = Main.getClienteDAO().buscar(rs.getInt("cliente_id"));
            Usuario usuario = Main.getUsuarioDAO().buscar(rs.getInt("usuario_id"));
            double valor = rs.getDouble("valor");
            String formaPagamento = rs.getString("forma_pagamento");
            LocalDate data = LocalDate.parse(rs.getString("data").substring(0, 10));

            Venda v = new Venda(id, cliente, usuario, valor, formaPagamento, data, null);
            List<VendaItem> items = Main.getVendaItemDAO().listar(v);

            v.setItems(items);

            venda = new Venda(id, cliente, usuario, valor, formaPagamento, data, items);
        }

        rs.close();
        stmt.close();
        conn.close();

        return venda;
    }

    @Override
    public int inserir(Venda venda) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, venda.getCliente().getId());
        stmt.setInt(2, venda.getUsuario().getId());
        stmt.setDouble(3, venda.getValor());
        stmt.setString(4, venda.getFormaPagamento());
        stmt.setDate(5, Date.valueOf(venda.getData()));

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
    public boolean editar(Venda venda) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EDITAR);
        stmt.setInt(1, venda.getCliente().getId());
        stmt.setInt(2, venda.getUsuario().getId());
        stmt.setDouble(3, venda.getValor());
        stmt.setString(4, venda.getFormaPagamento());
        stmt.setDate(5, Date.valueOf(venda.getData()));
        stmt.setInt(6, venda.getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean excluir(Venda venda) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EXCLUIR);
        stmt.setInt(1, venda.getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }
}
