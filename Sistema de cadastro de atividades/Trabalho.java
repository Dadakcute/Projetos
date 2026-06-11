/**
 * Representa um trabalho escolar.
 */
public class Trabalho extends Tarefa {

    public Trabalho(String titulo, String data, String materia) {
        super(titulo, data, materia);
    }

    @Override
    public String exibirDetalhes() {
        return "📝 Trabalho - " + getTitulo() + " | Matéria: " + getMateria() + " | Data: " + getData();
    }
}
