package aed3;

import java.io.File;
import java.time.LocalDate;



public class IO {


    public static void main(String[] args) {

        int id1, id2, id3, id4;
        
        (new File(".\\dados\\tarefas.db")).delete();
        (new File(".\\dados\\tarefas.hash_d.db")).delete();
        (new File(".\\dados\\tarefas.hash_c.db")).delete();

        Tarefa t1 = new Tarefa(1, "Revisar código", LocalDate.of(2024, 9, 3), LocalDate.of(2024, 9, 4), "Em andamento", 0);
        Tarefa t2 = new Tarefa(2, "Documentar projeto", LocalDate.of(2024, 9, 2), LocalDate.of(2024, 9, 5), "Pendente", 1);
        Tarefa t3 = new Tarefa(3, "Testar integração", LocalDate.of(2024, 8, 30), LocalDate.of(2024, 9, 6), "Concluído", 1);
        Tarefa t4 = new Tarefa(4, "Atualizar dependências", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 7), "Em andamento", 2);
        Tarefa t;


        try {

            Arquivos<Tarefa> arqTarefas = new Arquivos<>("tarefas", Tarefa.class.getConstructor());
            
            //Criação dos objetos
           id1 = arqTarefas.create(t1);
           id2 = arqTarefas.create(t2);
           id3 = arqTarefas.create(t3);
           id4 = arqTarefas.create(t4);



            // Leitura dos objetos

            System.out.println("Lendo registros:");
            t = arqTarefas.read(id1);
            if(t!=null)
                System.out.println(t);
            else
                System.out.println("Tarefa nao encontrada");


            System.out.println("\n\nLendo e excluindo tarefa:");
            System.out.println(arqTarefas.read(3));
            



            //Exclusão de tarefa
            if(arqTarefas.delete(id3)){
                System.out.println("\nPessoa 3 excluida!");
            }else{
                System.out.println("Erro ao excluir pessoa 3");
            }
            t = arqTarefas.read(id3);
            if(t!=null)
                System.out.println(t);
            else
                System.out.println("Pessoa 3 não encontrada");
            
       
            System.out.println("\n\nTarefas atualizadas:");
            //Atualizaca de tarefa para tamanho maior
            t1.status = "Em andamento";
            arqTarefas.update(t2);
            System.out.println(arqTarefas.read(id2));

            //Atualizaca de tarefa para tamanho menor
            t1.status = "Concluido";
            arqTarefas.update(t1);
            System.out.println(arqTarefas.read(id1));
        } catch(Exception e) {
            e.printStackTrace();;
        }

    }
}