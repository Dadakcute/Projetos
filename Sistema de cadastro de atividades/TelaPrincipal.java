import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Interface gráfica principal do sistema StudyFlow.
 */
public class TelaPrincipal {
    private JFrame frame;
    private JTextField txtTitulo;
    private JTextField txtMateria;
    private JTextField txtData;
    private JRadioButton rdbProva;
    private JRadioButton rdbTrabalho;
    private JButton btnAdicionar;
    private JButton btnRemover;
    private JButton btnLimpar;
    private DefaultListModel<Tarefa> listModel;
    private JList<Tarefa> listaTarefas;
    private JLabel lblContador;

    private final ArrayList<Tarefa> tarefas = new ArrayList<>();

    public TelaPrincipal() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        frame = new JFrame("StudyFlow - Organizador de Estudos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 520);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel painelFormulario = criarPainelFormulario();
        JPanel painelLista = criarPainelLista();

        frame.add(painelFormulario, BorderLayout.WEST);
        frame.add(painelLista, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createTitledBorder("Nova Tarefa"));
        painel.setLayout(new GridBagLayout());
        painel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Título:");
        txtTitulo = new JTextField();

        JLabel lblMateria = new JLabel("Matéria:");
        txtMateria = new JTextField();

        JLabel lblData = new JLabel("Data (dd/MM/yyyy):");
        txtData = new JTextField();

        JLabel lblTipo = new JLabel("Tipo de tarefa:");
        rdbProva = new JRadioButton("Prova 📚");
        rdbTrabalho = new JRadioButton("Trabalho 📝");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rdbProva);
        grupoTipo.add(rdbTrabalho);

        btnAdicionar = new JButton("Adicionar");
        btnRemover = new JButton("Remover Selecionada");
        btnLimpar = new JButton("Limpar Campos");
        lblContador = new JLabel("Tarefas cadastradas: 0");

        btnAdicionar.addActionListener(this::adicionarTarefa);
        btnRemover.addActionListener(this::removerTarefa);
        btnLimpar.addActionListener(e -> limparCampos());

        int linha = 0;
        gbc.gridx = 0;
        gbc.gridy = linha;
        painel.add(lblTitulo, gbc);
        gbc.gridy = ++linha;
        painel.add(txtTitulo, gbc);
        gbc.gridy = ++linha;
        painel.add(lblMateria, gbc);
        gbc.gridy = ++linha;
        painel.add(txtMateria, gbc);
        gbc.gridy = ++linha;
        painel.add(lblData, gbc);
        gbc.gridy = ++linha;
        painel.add(txtData, gbc);
        gbc.gridy = ++linha;
        painel.add(lblTipo, gbc);
        gbc.gridy = ++linha;
        painel.add(rdbProva, gbc);
        gbc.gridy = ++linha;
        painel.add(rdbTrabalho, gbc);
        gbc.gridy = ++linha;
        painel.add(btnAdicionar, gbc);
        gbc.gridy = ++linha;
        painel.add(btnRemover, gbc);
        gbc.gridy = ++linha;
        painel.add(btnLimpar, gbc);
        gbc.gridy = ++linha;
        painel.add(lblContador, gbc);

        return painel;
    }

    private JPanel criarPainelLista() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Tarefas Registradas"));

        listModel = new DefaultListModel<>();
        listaTarefas = new JList<>(listModel);
        listaTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTarefas.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(listaTarefas);
        painel.add(scrollPane, BorderLayout.CENTER);

        JLabel info = new JLabel("Selecione uma tarefa para removê-la.");
        info.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        painel.add(info, BorderLayout.SOUTH);

        return painel;
    }

    private void adicionarTarefa(ActionEvent event) {
        try {
            String titulo = txtTitulo.getText().trim();
            String materia = txtMateria.getText().trim();
            String data = txtData.getText().trim();

            validarCampos(titulo, materia, data);

            Tarefa novaTarefa;
            if (rdbProva.isSelected()) {
                novaTarefa = new Prova(titulo, data, materia);
            } else if (rdbTrabalho.isSelected()) {
                novaTarefa = new Trabalho(titulo, data, materia);
            } else {
                throw new IllegalArgumentException("Selecione o tipo de tarefa: Prova ou Trabalho.");
            }

            tarefas.add(novaTarefa);
            atualizarLista();
            limparCamposComSucesso();
            JOptionPane.showMessageDialog(frame, "Tarefa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Ocorreu um erro ao cadastrar a tarefa. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerTarefa(ActionEvent event) {
        int indice = listaTarefas.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(frame, "Selecione uma tarefa para remover.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Tarefa tarefaRemovida = listModel.getElementAt(indice);
        tarefas.remove(tarefaRemovida);
        atualizarLista();
        JOptionPane.showMessageDialog(frame, "Tarefa removida com sucesso!", "Removido", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtMateria.setText("");
        txtData.setText("");
        rdbProva.setSelected(false);
        rdbTrabalho.setSelected(false);
    }

    private void limparCamposComSucesso() {
        limparCampos();
        txtTitulo.requestFocusInWindow();
    }

    private void validarCampos(String titulo, String materia, String data) {
        if (titulo.isEmpty()) {
            throw new IllegalArgumentException("O campo título não pode ficar vazio.");
        }
        if (materia.isEmpty()) {
            throw new IllegalArgumentException("O campo matéria não pode ficar vazio.");
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("O campo data não pode ficar vazio.");
        }
        if (!validarData(data)) {
            throw new IllegalArgumentException("Data inválida. Use o formato dd/MM/yyyy.");
        }
    }

    private boolean validarData(String data) {
        Pattern padrao = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$");
        return padrao.matcher(data).matches();
    }

    private void atualizarLista() {
        listModel.clear();
        for (Tarefa tarefa : tarefas) {
            listModel.addElement(tarefa);
        }
        lblContador.setText("Tarefas cadastradas: " + tarefas.size());
    }
}
