package ezpos.daos;

import ezpos.daos.interfaces.FornecedorDAO;
import ezpos.db.ConnectionManager;
import ezpos.model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCFornecedorDAO implements FornecedorDAO {
    private static final String INSERIR = "INSERT INTO fornecedores(cpf_cnpj,nome,endereco,telefone,email) VALUES (?,?,?,?,?);";
    private static final String EDITAR = "UPDATE fornecedores SET cpf_cnpj=?, nome=?, endereco=?, telefone=?, email=? WHERE id=?;";
    private static final String EXCLUIR = "DELETE FROM fornecedores WHERE id=?;";
    private static final String LISTAR = "SELECT * FROM fornecedores;";
    private static final String BUSCAR = "SELECT * FROM fornecedores WHERE id=?;";

    @Override
    public List<Fornecedor> listar() throws SQLException {
        ArrayList<Fornecedor> lista = new ArrayList<>();

        Connection conn = ConnectionManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(LISTAR);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String cpfCnpj = rs.getString("cpf_cnpj");
            String nome = rs.getString("nome");
            String endereco = rs.getString("endereco");
            String telefone = rs.getString("telefone");
            String email = rs.getString("email");

            lista.add(new Fornecedor(id, cpfCnpj, nome, endereco, telefone, email));
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    @Override
    public Fornecedor buscar(int id) throws SQLException {
        return null;
    }

    @Override
    public boolean inserir(Fornecedor fornecedor) throws SQLException {
        Connection conn = ConnectionManager.getConnection();

        PreparedStatement stmt = conn.prepareStatement(INSERIR);
        stmt.setString(1, fornecedor.getCpfCnpj());
        stmt.setString(2, fornecedor.getNome());
        stmt.setString(3, fornecedor.getEndereco());
        stmt.setString(4, fornecedor.getTelefone());
        stmt.setString(5, fornecedor.getEmail());

        int result = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return result == 1;
    }

    @Override
    public boolean editar(Fornecedor fornecedor) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Fornecedor fornecedor) throws SQLException {
        return false;
    }
}
