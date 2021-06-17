package ezpos.gui.control;

import ezpos.Main;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.CompraRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
import ezpos.repositories.interfaces.ProdutoRepository;
import javafx.fxml.FXML;

public class JanelaPrincipal extends JanelaBase {
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;

    public JanelaPrincipal(ClienteRepository clienteRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository, CompraRepository compraRepository) {
        this.clienteRepository = clienteRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
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

    @FXML
    private void abrirJanelaAdicionarCompra() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_COMPRA, aClass -> new JanelaAdicionarCompra(compraRepository, fornecedorRepository, produtoRepository));
    }
}
