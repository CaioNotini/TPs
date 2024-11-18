package aed3;

import java.util.ArrayList;

public class ArquivoRotulos extends Arquivos<Rotulo> {
    private ArvoreBMais<ParNomeId> arvore;

    public ArquivoRotulos(String nomeArquivo, String nomeArvore) throws Exception {
        super(nomeArquivo, Rotulo.class.getConstructor());
        arvore = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5, nomeArvore);
    }

    public int create(Rotulo rotulo) throws Exception {
        int id = super.create(rotulo); 
        rotulo.setId(id);  
        arvore.create(new ParNomeId(rotulo.getNome(), id));  
        return id;
    }

    public boolean delete(int id) throws Exception {
        Rotulo rotulo = super.read(id);
        if (rotulo != null) {
            arvore.delete(new ParNomeId(rotulo.getNome(), id)); 
            return super.delete(id);
        }
        return false;
    }

    public boolean update(Rotulo rotulo) throws Exception {
        Rotulo rotuloAntigo = super.read(rotulo.getId());
        if (rotuloAntigo != null && !rotuloAntigo.getNome().equals(rotulo.getNome())) {
            arvore.delete(new ParNomeId(rotuloAntigo.getNome(), rotuloAntigo.getId())); 
            arvore.create(new ParNomeId(rotulo.getNome(), rotulo.getId())); 
        }
        return super.update(rotulo);
    }

    public ArrayList<Rotulo> listarRotulos() throws Exception {
        ArrayList<Rotulo> listaRotulos = new ArrayList<>();
        ArrayList<ParNomeId> listaNomes = arvore.read(null);

        for (ParNomeId parNomeId : listaNomes) {
            Rotulo rotulo = super.read(parNomeId.getId());  
            if (rotulo != null) {
                listaRotulos.add(rotulo);  
            }
        }
        return listaRotulos;
    }

    public Rotulo buscarPorNome(String nome) throws Exception {
        ArrayList<ParNomeId> rotulos = arvore.read(new ParNomeId(nome, -1));
        if (rotulos.isEmpty()) {
            return null;
        }
        return super.read(rotulos.get(0).getId());
    }
}
