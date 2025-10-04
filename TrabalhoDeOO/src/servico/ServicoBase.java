package servico;

import java.util.List;

public abstract class ServicoBase<T> {

    public abstract void criar(T obj);
    public abstract List<T> listar();
    public abstract T buscar(T obj);
    public abstract void remover(T obj);

}
