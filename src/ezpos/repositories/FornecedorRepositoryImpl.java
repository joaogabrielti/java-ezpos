package ezpos.repositories;

import ezpos.daos.interfaces.FornecedorDAO;
import ezpos.model.Fornecedor;
import ezpos.repositories.interfaces.FornecedorRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class FornecedorRepositoryImpl implements FornecedorRepository {
    private final ObservableList<Fornecedor> fornecedores;
    private final FornecedorDAO fornecedorDAO;

    public FornecedorRepositoryImpl(FornecedorDAO fornecedorDAO) {
        this.fornecedores = FXCollections.observableArrayList();
        this.fornecedorDAO = fornecedorDAO;
    }

    @Override
    public ObservableList<Fornecedor> listar() throws SQLException {
        this.fornecedores.clear();
        this.fornecedores.addAll(this.fornecedorDAO.listar());
        return FXCollections.unmodifiableObservableList(this.fornecedores);
    }

    @Override
    public Fornecedor buscar(int id) throws SQLException {
        return this.fornecedorDAO.buscar(id);
    }

    @Override
    public boolean inserir(Fornecedor fornecedor) throws SQLException {
        return this.fornecedorDAO.inserir(fornecedor);
    }

    @Override
    public boolean editar(Fornecedor fornecedor) throws SQLException {
        return this.fornecedorDAO.editar(fornecedor);
    }

    @Override
    public boolean excluir(Fornecedor fornecedor) throws SQLException {
        return this.fornecedorDAO.excluir(fornecedor);
    }
}
