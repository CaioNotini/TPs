package aed3;

import java.util.ArrayList;

public class ArquivoCategorias extends Arquivos<Categoria> {
    private ArvoreBMais<ParNomeId> arvore;  

    public ArquivoCategorias(String nomeArquivo, String nomeArvore) throws Exception {
        super(nomeArquivo, Categoria.class.getConstructor());
        arvore = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5, nomeArvore);
    }



    public int create(Categoria categoria) throws Exception {
        int id = super.create(categoria); 
        categoria.setId(id);
        arvore.create(new ParNomeId(categoria.getNome(), id));  
        return id;
    }


    
    public boolean delete(int id) throws Exception {
        Categoria categoria = super.read(id);
        if (categoria != null) {
            arvore.delete(new ParNomeId(categoria.getNome(), id)); 
            return super.delete(id);
        }
        return false;
    }

    public boolean update(Categoria categoria) throws Exception {
        Categoria categoriaAntiga = super.read(categoria.getId());
        if (categoriaAntiga != null && !categoriaAntiga.getNome().equals(categoria.getNome())) {
            arvore.delete(new ParNomeId(categoriaAntiga.getNome(), categoriaAntiga.getId())); 
            arvore.create(new ParNomeId(categoria.getNome(), categoria.getId())); 
        }
        return super.update(categoria);
    }

    
    public ArrayList<Categoria> listarCategorias() throws Exception {
        ArrayList<Categoria> listaCategorias = new ArrayList<>();
        ArrayList<ParNomeId> listaNomes = arvore.read(null); 

        for (ParNomeId parNomeId : listaNomes) {
            Categoria categoria = super.read(parNomeId.getId());  
            if (categoria != null) {
                listaCategorias.add(categoria);  
            }
        }
        return listaCategorias;
    }

    public Categoria buscarPorNome(String nome) throws Exception {
        ArrayList<ParNomeId> categorias = arvore.read(new ParNomeId(nome, -1));
        if (categorias.isEmpty()) {
            return null;
        }
        return super.read(categorias.get(0).getId());
    }
}
