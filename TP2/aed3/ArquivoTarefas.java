package aed3;

import java.util.*;

public class ArquivoTarefas extends Arquivos<Tarefa> {
    private ArvoreBMais<ParIdId> arvore;  

    public ArquivoTarefas(String nomeArquivo, String nomeArvore) throws Exception {
        super(nomeArquivo, Tarefa.class.getConstructor());  
        arvore = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, nomeArvore); 
    }

    @Override
    public int create(Tarefa tarefa) throws Exception {
        int id = super.create(tarefa);  
        tarefa.setId(id);  

        arvore.create(new ParIdId(tarefa.getIdCategoria(), id));
        
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Tarefa tarefa = super.read(id);  
        if (tarefa != null) {
            arvore.delete(new ParIdId(tarefa.getIdCategoria(), id));
            return super.delete(id);  
        }
        return false;
    }

    @Override
    public boolean update(Tarefa tarefa) throws Exception {
        Tarefa tarefaAntiga = super.read(tarefa.getId());
        if (tarefaAntiga != null && tarefaAntiga.getIdCategoria() != tarefa.getIdCategoria()) {

            arvore.delete(new ParIdId(tarefaAntiga.getIdCategoria(), tarefaAntiga.getId()));

            arvore.create(new ParIdId(tarefa.getIdCategoria(), tarefa.getId()));
        }
        return super.update(tarefa); 
    }

    public ArrayList<Tarefa> buscarPorCategoria(int idCategoria) throws Exception {
        ArrayList<Tarefa> listaTarefas = new ArrayList<>();

        ArrayList<ParIdId> listaIds = arvore.read(new ParIdId(idCategoria, -1));
        for (ParIdId parIdId : listaIds) {
            Tarefa tarefa = super.read(parIdId.getIdTarefa());
            if (tarefa != null) {
                listaTarefas.add(tarefa);
            }
        }

        return listaTarefas;
    }
}
