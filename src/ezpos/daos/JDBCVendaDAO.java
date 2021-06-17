package ezpos.daos;

import ezpos.daos.interfaces.VendaDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Venda;
import ezpos.model.VendaItem;
import java.sql.*;
import java.util.List;

public class JDBCVendaDAO implements VendaDAO {
    private static final String INSERIR = "INSERT INTO vendas(cliente_id,usuario_id,valor,forma_pagamento,data) VALUES (?,1,?,?,?);";
    private static final String INSERIR_ITEM = "INSERT INTO venda_itens(venda_id,produto_id,quantidade,valor) VALUES (?,?,?,?);";

    @Override
    public List<Venda> listar() throws SQLException {
        return null;
    }

    @Override
    public Venda buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public int inserir(Venda venda) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, venda.getCliente().getId());
        stmt.setDouble(2, venda.getValor());
        stmt.setString(3, venda.getFormaPagamento());
        stmt.setDate(4, Date.valueOf(venda.getData()));

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
    public boolean inserirItem(VendaItem item) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR_ITEM);
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
    public boolean editar(Venda venda) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Venda venda) throws SQLException {
        return false;
    }
}
