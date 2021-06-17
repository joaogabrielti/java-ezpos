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
    private static final String INSERIR = "INSERT INTO produtos(grupo_id,subgrupo_id,nome,descricao,quantidade,valor) VALUES (1,1,?,?,0,?);";
    private static final String EDITAR = "UPDATE produtos SET nome=?, descricao=?, valor=? WHERE id=?;";
    private static final String EXCLUIR = "DELETE FROM produtos WHERE id=?;";
    private static final String LISTAR = "SELECT * FROM produtos;";
    private static final String BUSCAR = "SELECT * FROM produtos WHERE id=?;";

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
            double valor = rs.getDouble("valor");

            lista.add(new Produto(id, nome, descricao, quantidade, valor));
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public Produto buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public boolean inserir(Produto produto) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR);
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setDouble(3, produto.getValor());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean editar(Produto produto) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Produto produto) throws SQLException {
        return false;
    }
}
