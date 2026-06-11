/**
 * Representa uma prova escolar.
 */
public class Prova extends Tarefa {

    public Prova(String titulo, String data, String materia) {
        super(titulo, data, materia);
    }

    @Override
    public String exibirDetalhes() {
        return "📚 Prova - " + getTitulo() + " | Matéria: " + getMateria() + " | Data: " + getData();
    }
}
