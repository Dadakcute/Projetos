/**
 * Classe base para tarefas de estudo.
 * Contém atributos comuns a provas e trabalhos.
 */
public abstract class Tarefa {
    private String titulo;
    private String data;
    private String materia;

    public Tarefa(String titulo, String data, String materia) {
        this.titulo = titulo;
        this.data = data;
        this.materia = materia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    /**
     * Exibe detalhes da tarefa de forma personalizada.
     * Classes filhas devem sobrescrever este método.
     */
    public abstract String exibirDetalhes();

    @Override
    public String toString() {
        return exibirDetalhes();
    }
}
