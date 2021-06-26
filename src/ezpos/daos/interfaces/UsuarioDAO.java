package ezpos.daos.interfaces;

import ezpos.model.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    List<Usuario> listar() throws SQLException;
    Usuario buscar(int id) throws SQLException;
    Usuario autenticar(String usuario, String senha) throws SQLException;
    boolean inserir(Usuario usuario) throws SQLException;
    boolean editar(Usuario usuario) throws SQLException;
    boolean excluir(Usuario usuario) throws SQLException;
}
