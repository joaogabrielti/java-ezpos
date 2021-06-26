package ezpos.repositories;

import ezpos.Main;
import ezpos.model.Fornecedor;
import ezpos.repositories.interfaces.FornecedorRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class FornecedorRepositoryImpl implements FornecedorRepository {
    private final ObservableList<Fornecedor> fornecedores;

    public FornecedorRepositoryImpl() {
        this.fornecedores = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Fornecedor> listar() throws SQLException {
        this.fornecedores.clear();
        this.fornecedores.addAll(Main.getFornecedorDAO().listar());
        return FXCollections.unmodifiableObservableList(this.fornecedores);
    }

    @Override
    public Fornecedor buscar(int id) throws SQLException {
        return Main.getFornecedorDAO().buscar(id);
    }

    @Override
    public boolean inserir(Fornecedor fornecedor) throws SQLException {
        return Main.getFornecedorDAO().inserir(fornecedor);
    }

    @Override
    public boolean editar(Fornecedor fornecedor) throws SQLException {
        return Main.getFornecedorDAO().editar(fornecedor);
    }

    @Override
    public boolean excluir(Fornecedor fornecedor) throws SQLException {
        return Main.getFornecedorDAO().excluir(fornecedor);
    }
}
