package ezpos.daos;

import ezpos.daos.interfaces.ClienteDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCClienteDAO implements ClienteDAO {
    private static final String INSERIR = "INSERT INTO clientes(cpf_cnpj,nome,endereco,telefone,email) VALUES (?,?,?,?,?);";
    private static final String EDITAR = "UPDATE clientes SET cpf_cnpj=?, nome=?, endereco=?, telefone=?, email=? WHERE id=?;";
    private static final String EXCLUIR = "DELETE FROM clientes WHERE id=?;";
    private static final String LISTAR = "SELECT * FROM clientes;";
    private static final String BUSCAR = "SELECT * FROM clientes WHERE id=?;";

    @Override
    public List<Cliente> listar() throws SQLException {
        return null;
    }

    @Override
    public Cliente buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public boolean inserir(Cliente cliente) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR);
        stmt.setString(1, cliente.getCpfCnpj());
        stmt.setString(2, cliente.getNome());
        stmt.setString(3, cliente.getEndereco());
        stmt.setString(4, cliente.getTelefone());
        stmt.setString(5, cliente.getEmail());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean editar(Cliente cliente) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Cliente cliente) throws SQLException {
        return false;
    }
}
