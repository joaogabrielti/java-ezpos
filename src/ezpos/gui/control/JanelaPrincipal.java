package ezpos.gui.control;

import ezpos.Main;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;

public class JanelaPrincipal extends JanelaBase {
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    public JanelaPrincipal(ClienteRepository clienteRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
    }

    @FXML
    private void abrirJanelaAdicionarCliente() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_CLIENTE, aClass -> new JanelaAdicionarCliente(clienteRepository));
    }

    @FXML
    private void abrirJanelaAdicionarFornecedor() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_FORNECEDOR, aClass -> new JanelaAdicionarFornecedor(fornecedorRepository));
    }

    @FXML
    private void abrirJanelaAdicionarProduto() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_PRODUTO, aClass -> new JanelaAdicionarProduto(produtoRepository));
    }
}
