package aed3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.text.Normalizer;

public class ArquivoTarefas extends Arquivos<Tarefa> {
    private ArvoreBMais<ParIdId> arvore; 
    private  ListaInvertida lista;

    public ArquivoTarefas(String nomeArquivo, String nomeArvore,String arquivoLista,String arquivoBloco) throws Exception {
        super(nomeArquivo, Tarefa.class.getConstructor());  
        arvore = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, nomeArvore);
        lista = new ListaInvertida(4, arquivoLista, arquivoBloco); 
    }

    @Override
    public int create(Tarefa tarefa) throws Exception {
        int id = super.create(tarefa);  
        tarefa.setId(id);  

        arvore.create(new ParIdId(tarefa.getIdCategoria(), id));
       List<String> listasemStop = carregarStopwords(tarefa.nome);
       float[] frequenciaPalavras = calcularFrequencia(listasemStop);
       LinkedHashSet<String> set = new LinkedHashSet<>();
        for (String palavra : listasemStop) {
            set.add(palavra);  
        }

        List<String> listaPalavras = new ArrayList<>(set);
        
         for(int i=0;i<frequenciaPalavras.length;i++){

             lista.create(listaPalavras.get(i), new ElementoLista(id, frequenciaPalavras[i]));
          //   System.out.println("elemento criado: "+listaPalavras.get(i)+" frequencia: "+frequenciaPalavras[i]);
            }
        lista.incrementaEntidades();
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Tarefa tarefa = super.read(id);  
        if (tarefa != null) {
            arvore.delete(new ParIdId(tarefa.getIdCategoria(), id));
            lista.decrementaEntidades();

        List<String> listasemStop = carregarStopwords(tarefa.nome);  
        LinkedHashSet<String> set = new LinkedHashSet<>(listasemStop);  
        for (String palavra : set) {
            lista.delete(palavra, id);  
        }
            return super.delete(id);  
            
        }
        return false;
    }

    @Override
    public boolean update(Tarefa tarefa) throws Exception {
        Tarefa tarefaAntiga = super.read(tarefa.getId());
    
            if (tarefaAntiga != null && (!tarefaAntiga.nome.equals(tarefa.nome) ||  tarefaAntiga.getIdCategoria() != tarefa.getIdCategoria())) {
            arvore.delete(new ParIdId(tarefaAntiga.getIdCategoria(), tarefaAntiga.getId()));

            List<String> listasemStop = carregarStopwords(tarefaAntiga.nome);  
        LinkedHashSet<String> set = new LinkedHashSet<>(listasemStop);  
        for (String palavra : set) {
            lista.delete(palavra, tarefaAntiga.id);  
        }
           
        List<String> listasemStop2 = carregarStopwords(tarefa.nome);
        float[] frequenciaPalavras = calcularFrequencia(listasemStop2);
        LinkedHashSet<String> set2 = new LinkedHashSet<>();
         for (String palavra : listasemStop2) {
             set2.add(palavra);  
         }
 
         List<String> listaPalavras = new ArrayList<>(set2);
          for(int i=0;i<frequenciaPalavras.length;i++){
              lista.create(listaPalavras.get(i), new ElementoLista(tarefa.id, frequenciaPalavras[i]));
             }



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
   
     public  List<String> carregarStopwords(String tarefa) throws IOException {
        List<String> stopwords = new ArrayList<>();
        List<String> palavrasFiltradas = new ArrayList<>();
        String arquivo= "stopwords.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo)) ){
            String linha;
            while ((linha = br.readLine()) != null) {
                stopwords.add(linha.trim());
            }
            String nome = transforma(tarefa);
            String[] nomes =nome.split(" ");
            
            for (String palavra : nomes) {
                if (!stopwords.contains(palavra.toLowerCase())) {
                    palavrasFiltradas.add(palavra);
                }
            }
        }
        return palavrasFiltradas;
       
    }

    public float[] calcularFrequencia(List<String> palavrasFiltradas) {
        int totalPalavras = palavrasFiltradas.size();
        Set<String> palavrasProcessadas = new LinkedHashSet<>(palavrasFiltradas);
        List<String> palavrassemrepeticao = new ArrayList<>(palavrasProcessadas);
        float[] frequencia = new float[palavrassemrepeticao.size()]; 
    
        for (int i = 0; i < palavrassemrepeticao.size(); i++) {
            String palavra = palavrassemrepeticao.get(i); 
            int contagem = 0;
    
            for (String p : palavrasFiltradas) {
                if (palavra.equals(p)) {
                    contagem++;
                }
            }
            frequencia[i] = (float) contagem / totalPalavras;
        }
    
        return frequencia;
    }
 

      public ElementoLista[] buscarTarefasPorFrase(String frase) throws Exception {
        frase = transforma(frase);
        List<String> chaves = carregarStopwords(frase);
        List<ElementoLista[]> resultados = new ArrayList<>();
    
        for (String chave : chaves) {
            ElementoLista[] elementos = lista.read(chave);
            if (elementos.length > 0) { 
                for (ElementoLista elemento : elementos) {
                   
                    float novaFrequencia = (elemento.getFrequencia() * lista.numeroEntidades()) / elementos.length;
                    elemento.setFrequencia(novaFrequencia);
                }
                resultados.add(elementos);
            }
        }
        ElementoLista[] resultadoFinal = somarFrequencias(resultados);
        return resultadoFinal;
    }
    
    
    public ElementoLista[] somarFrequencias(List<ElementoLista[]> resultados) {
        Map<Integer, Float> somaFrequencias = new HashMap<>();
        for (ElementoLista[] elementos : resultados) {
            for (ElementoLista elemento : elementos) {
                int id = elemento.getId();
                float frequencia = elemento.getFrequencia();
    
             
                somaFrequencias.put(id, somaFrequencias.getOrDefault(id, 0f) + frequencia);
            }
        }
        List<ElementoLista> resultadoFinal = new ArrayList<>();
        for (Map.Entry<Integer, Float> entry : somaFrequencias.entrySet()) {
            resultadoFinal.add(new ElementoLista(entry.getKey(), entry.getValue()));
        }
        Collections.sort(resultadoFinal);
        return resultadoFinal.toArray(new ElementoLista[0]);
    }
     public static String transforma(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
  }
    
    
}
