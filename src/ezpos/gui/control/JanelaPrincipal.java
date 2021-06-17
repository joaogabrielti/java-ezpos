package ezpos.gui.control;

import ezpos.Main;
import ezpos.repositories.interfaces.ClienteRepository;
import ezpos.repositories.interfaces.FornecedorRepository;
import javafx.fxml.FXML;

public class JanelaPrincipal extends JanelaBase {
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;

    public JanelaPrincipal(ClienteRepository clienteRepository, FornecedorRepository fornecedorRepository) {
        this.clienteRepository = clienteRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @FXML
    private void abrirJanelaAdicionarCliente() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_CLIENTE, aClass -> new JanelaAdicionarCliente(clienteRepository));
    }

    @FXML
    private void abrirJanelaAdicionarFornecedor() {
        Main.alterarJanela(Main.JANELA_ADICIONAR_FORNECEDOR, aClass -> new JanelaAdicionarFornecedor(fornecedorRepository));
    }
}
