package ezpos.gui.control;

import ezpos.Main;
import ezpos.repositories.interfaces.*;
import javafx.fxml.FXML;

public class JanelaPrincipal extends JanelaBase {
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;
    private final VendaRepository vendaRepository;

    public JanelaPrincipal(ClienteRepository clienteRepository, FornecedorRepository fornecedorRepository, ProdutoRepository produtoRepository, CompraRepository compraRepository, VendaRepository vendaRepository) {
        this.clienteRepository = clienteRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
        this.vendaRepository = vendaRepository;
    }

    @FXML
    private void abrirJanelaAdicionarCliente() {
        Main.alterarJanela(Main.JANELA_CLIENTES, aClass -> new JanelaClientes(clienteRepository));
    }

    @FXML
    private void abrirJanelaAdicionarFornecedor() {
        Main.alterarJanela(Main.JANELA_FORNECEDORES, aClass -> new JanelaFornecedores(fornecedorRepository));
    }

    @FXML
    private void abrirJanelaAdicionarProduto() {
        Main.alterarJanela(Main.JANELA_PRODUTOS, aClass -> new JanelaProdutos(produtoRepository));
    }

    @FXML
    private void abrirJanelaAdicionarCompra() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_COMPRA, aClass -> new JanelaAdicionarCompra(compraRepository, fornecedorRepository, produtoRepository));
    }

    @FXML
    private void abrirJanelaAdicionarVenda() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_VENDA, aClass -> new JanelaAdicionarVenda(vendaRepository, clienteRepository, produtoRepository));
    }

    @FXML
    private void abrirJanelaRelatorioEstoque() {
        Main.alterarJanela(Main.JANELA_RELATORIO_ESTOQUE, aClass -> new JanelaRelatorioEstoque(produtoRepository));
    }
}
