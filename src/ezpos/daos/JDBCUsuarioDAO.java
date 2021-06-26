package ezpos.daos;

import ezpos.daos.interfaces.UsuarioDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Cliente;
import ezpos.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCUsuarioDAO implements UsuarioDAO {
    private static final String AUTENTICAR = "SELECT * FROM usuarios WHERE usuario LIKE ? AND senha LIKE ?;";

    @Override
    public List<Usuario> listar() throws SQLException {
        return null;
    }

    @Override
    public Usuario buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public Usuario autenticar(String usuario, String senha) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(AUTENTICAR);
        stmt.setString(1, usuario);
        stmt.setString(2, senha);

        ResultSet rs = stmt.executeQuery();

        Usuario u = null;
        if (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            senha = rs.getString("senha");
            int admin = rs.getInt("admin");

            u = new Usuario(id, usuario, nome, email, senha, admin);
        }

        rs.close();
        stmt.close();
        conn.close();

        return u;
    }

    @Override
    public boolean inserir(Usuario usuario) throws SQLException {
        return false;
    }

    @Override
    public boolean editar(Usuario usuario) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Usuario usuario) throws SQLException {
        return false;
    }
}
