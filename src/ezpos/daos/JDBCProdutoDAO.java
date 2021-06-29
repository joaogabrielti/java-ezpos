package ezpos.daos;

import ezpos.daos.interfaces.ProdutoDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCProdutoDAO implements ProdutoDAO {
    private static final String INSERIR = "INSERT INTO produtos(nome,descricao,valor_compra,valor_venda) VALUES (?,?,?,?);";
    private static final String EDITAR = "UPDATE produtos SET nome=?, descricao=?, valor_compra=?, valor_venda=? WHERE id=?;";
    private static final String EXCLUIR = "DELETE FROM produtos WHERE id=?;";
    private static final String LISTAR = "SELECT * FROM produtos;";
    private static final String BUSCAR = "SELECT * FROM produtos WHERE id=?;";
    private static final String FN_ATUALIZA_ESTOQUE = "SELECT fn_atualiza_estoque(?);";

    @Override
    public List<Produto> listar() throws SQLException {
        ArrayList<Produto> lista = new ArrayList<>();

        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            double quantidade = rs.getDouble("quantidade");
            double valor_compra = rs.getDouble("valor_compra");
            double valor_venda = rs.getDouble("valor_venda");

            lista.add(new Produto(id, nome, descricao, quantidade, valor_compra, valor_venda));
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public Produto buscar(int id) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(BUSCAR);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        Produto produto = null;
        if (rs.next()) {
            String nome = rs.getString("nome");
            String descricao = rs.getString("descricao");
            double quantidade = rs.getDouble("quantidade");
            double valor_compra = rs.getDouble("valor_compra");
            double valor_venda = rs.getDouble("valor_venda");

            produto = new Produto(id, nome, descricao, quantidade, valor_compra, valor_venda);
        }

        rs.close();
        stmt.close();
        conn.close();

        return produto;
    }

    @Override
    public boolean inserir(Produto produto) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getValorCompra());
        stmt.setDouble(4, produto.getValorVenda());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean editar(Produto produto) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EDITAR);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getValorCompra());
        stmt.setDouble(4, produto.getValorVenda());
        stmt.setInt(5, produto.getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean excluir(Produto produto) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(EXCLUIR);
        stmt.setInt(1, produto.getId());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean atualizarEstoque(Produto produto) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(FN_ATUALIZA_ESTOQUE);
        stmt.setInt(1, produto.getId());

        boolean result = stmt.execute();

        stmt.close();
        conn.close();

        return result;
    }
}
