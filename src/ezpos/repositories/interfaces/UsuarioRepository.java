package ezpos.repositories.interfaces;

import ezpos.model.Usuario;
import java.sql.SQLException;

public interface UsuarioRepository {
    Usuario autenticar(String usuario, String senha) throws SQLException;
}
