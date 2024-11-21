package aed3;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class IO {

    public static void main(String[] args) {
        Tarefa t;
        Scanner console = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            File file = new File("dados");
            if (!file.exists()) {
                file.mkdir();
            }

            // Inicializa o arquivo de tarefas e categorias dentro da pasta "dados"
            ArquivoTarefas arqTarefas = new ArquivoTarefas("tarefas.db", "dados/arvoreTarefas.db","dados/dicionario.listainv.db","dados/blocos.listainv.db");
            ArquivoCategorias arqCategorias = new ArquivoCategorias("categorias.db", "dados/arvoreCategoria.db");
            ArquivoRotulos arqRotulos = new ArquivoRotulos("rotulos.db", "dados/arvoreRotulos.db");


            int opcao;
            do {
                System.out.println("\n\n-------------------------------");
                System.out.println("              MENU");
                System.out.println("-------------------------------");
                System.out.println("1 - Tarefa");
                System.out.println("2 - Categoria");
                System.out.println("3 - Rótulo");
                System.out.println("0 - Sair");
                try {
                    opcao = Integer.parseInt(console.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        int op;
                        do {
                            System.out.println("\n\n-------------------------------");
                            System.out.println("              TAREFA");
                            System.out.println("-------------------------------");
                            System.out.println("1 - Inserir");
                            System.out.println("2 - Buscar");
                            System.out.println("3 - Atualizar");
                            System.out.println("4 - Excluir");
                            System.out.println("5 - Buscar por Categoria");
                            System.out.println("0 - Sair");
                            try {
                                op = Integer.parseInt(console.nextLine());
                            } catch (NumberFormatException e) {
                                op = -1;
                            }

                           switch (op) {
                            case 1 -> {
                                System.out.println("\nInserir nova tarefa:");
                                Tarefa novaTarefa = criarTarefa(console, formatter, arqCategorias);
                                int idNovaTarefa = arqTarefas.create(novaTarefa);
                                System.out.println("Tarefa inserida com ID: " + idNovaTarefa);
                            }

                            case 2 -> {
                                System.out.println("\nBuscar tarefa por termos relacionados:");
                                System.out.print("Digite uma tarefa: ");
                                String chave = console.nextLine();
                              
                                ElementoLista[]resultadoFinal=arqTarefas.buscarTarefasPorFrase(chave);
                                 Arrays.sort(resultadoFinal, (e1, e2) -> Float.compare(e2.getFrequencia(), e1.getFrequencia()));
                                for(int i=0;i<resultadoFinal.length && i<10;i++){
                                    if(resultadoFinal[i]!=null){
                                        int idBuscar=resultadoFinal[i].getId();
                                        t  = arqTarefas.read(idBuscar);
                                        if (t != null) {
                                            System.out.println("Tarefa encontrada:");
                                            System.out.println(t);
                                            } 
                                    }
                                      
                                }
                              
                                
                            }

                            case 3 -> {
                                System.out.print("\nDigite o ID da tarefa que deseja atualizar: ");
                                int idAtualizar = Integer.parseInt(console.nextLine());

                                t = arqTarefas.read(idAtualizar);
                                if (t != null) {
                                    System.out.println("Tarefa atual encontrada:");
                                    System.out.println(t);

                                    System.out.println("\nAtualize os dados da tarefa:");
                                    Tarefa tarefaAtualizada = criarTarefa(console, formatter, arqCategorias);
                                    tarefaAtualizada.setId(idAtualizar);
                                    arqTarefas.update(tarefaAtualizada);
                                    System.out.println("Tarefa atualizada com sucesso!");
                                } else {
                                    System.out.println("Tarefa não encontrada!");
                                }
                            }

                            case 4 -> {
                                System.out.print("\nDigite o ID da tarefa que deseja excluir: ");
                                int idExcluido = Integer.parseInt(console.nextLine());

                                t = arqTarefas.read(idExcluido);
                                if (t != null) {
                                    System.out.println("Tarefa encontrada:");
                                    System.out.println(t);
                                    System.out.print("Tem certeza que deseja excluir essa tarefa? (S/N): ");
                                    String confirmacao = console.nextLine();
                                    if (confirmacao.equalsIgnoreCase("S")) {
                                        arqTarefas.delete(idExcluido);
                                        System.out.println("Tarefa excluída com sucesso!");
                                    } else {
                                        System.out.println("Exclusão cancelada.");
                                    }
                                } else {
                                    System.out.println("Tarefa não encontrada!");
                                }
                            }

                            case 5 -> {
                                System.out.println("\nDigite a categoria que deseja buscar: ");
                                ArrayList<Categoria> categorias = arqCategorias.listarCategorias();
                                categorias.sort(Comparator.comparing(Categoria::getNome));

                                for (int i = 0; i < categorias.size(); i++) {
                                    System.out.println((i + 1) + " - " + categorias.get(i).getNome());
                                }

                                int escolhaCategoria;
                                while (true) {
                                    System.out.print("numero: ");
                                    escolhaCategoria = Integer.parseInt(console.nextLine()) - 1;
                                    if (escolhaCategoria >= 0 && escolhaCategoria < categorias.size()) {
                                        break;
                                    }
                                    System.out.println("Opção inválida. Tente novamente.");
                                }

                                int idCategoria = categorias.get(escolhaCategoria).getId();

                                // Buscar tarefas diretamente pelo idCategoria
                                buscarTarefas(idCategoria, arqTarefas);
                            }

                            case 0 -> System.out.println("Voltando ao menu principal...");
                            
                            default -> System.out.println("Opção inválida! Selecione uma opção válida.");
                        }
                        } while (op != 0);
                        break;

                   case 2:
                    
                   int o;
                    do {
                        System.out.println("\n\n-------------------------------");
                        System.out.println("              CATEGORIA");
                        System.out.println("-------------------------------");
                        System.out.println("1 - Inserir");
                        System.out.println("2 - Buscar");
                        System.out.println("3 - Excluir");
                        System.out.println("4 - Listar todas");
                        System.out.println("0 - Sair");
                        try {
                            o = Integer.parseInt(console.nextLine());
                        } catch (NumberFormatException e) {
                            o = -1;
                        }

                        switch (o) {
                            case 1 -> {
                                System.out.println("\nINCLUSÃO");
                                String nome;
                                try {
                                    System.out.print("Nome: ");
                                    nome = console.nextLine();
                                    Categoria novaCategoria = new Categoria(nome);
                                    int idNovaCategoria = arqCategorias.create(novaCategoria);
                                    System.out.println("Categoria inserida com ID: " + idNovaCategoria);
                                } catch (Exception e) {
                                    System.out.println("Dados inválidos!");
                                    break;
                                }
                            }
                            case 2 -> {
                                System.out.println("\nBUSCA");
                                System.out.print("Nome: ");
                                String nome = console.nextLine();
                                Categoria categoriaEncontrada = arqCategorias.buscarPorNome(nome);
                                if (categoriaEncontrada != null) {
                                    System.out.println("Categoria encontrada: " + categoriaEncontrada);
                                } else {
                                    System.out.println("Categoria não encontrada!");
                                }
                            }
                            case 3 -> {
                                System.out.println("\nEXCLUSÃO");
                                 ArrayList<Categoria> categorias = arqCategorias.listarCategorias();
                                categorias.sort(Comparator.comparing(Categoria::getNome));

                                for (int i = 0; i < categorias.size(); i++) {
                                    System.out.println((i + 1) + " - " + categorias.get(i).getNome());
                                }

                                int escolhaCategoria;
                                while (true) {
                                    System.out.print("numero: ");
                                    escolhaCategoria = Integer.parseInt(console.nextLine()) - 1;
                                    if (escolhaCategoria >= 0 && escolhaCategoria < categorias.size()) {
                                        break;
                                    }
                                    System.out.println("Opção inválida. Tente novamente.");
                                }

                                int id = categorias.get(escolhaCategoria).getId();


                                // Verifica se há tarefas vinculadas antes de excluir
                                ArrayList<Tarefa> tarefasVinculadas = arqTarefas.buscarPorCategoria(id);
                                if (!tarefasVinculadas.isEmpty()) {
                                    System.out.println("Categoria não pode ser excluída, pois possui tarefas vinculadas.");
                                } else {
                                    boolean excluida = arqCategorias.delete(id);
                                    if (excluida) {
                                        System.out.println("Categoria excluída com sucesso!");
                                    } else {
                                        System.out.println("Categoria não encontrada ou erro ao excluir.");
                                    }
                                }
                            }
                            case 4 -> {
                                System.out.println("\nLISTA COMPLETA");
                                ArrayList<Categoria> lista = arqCategorias.listarCategorias();
                                lista.forEach(System.out::println);
                            }
                            case 0 -> System.out.println("Voltando ao menu principal...");
                            default -> System.out.println("Opção inválida");
                        }
                    } while (o != 0);
                    break;

                    case 3:
                        int opcao3;
                        do {
                            System.out.println("\n\n-------------------------------");
                            System.out.println("              RÓTULO");
                            System.out.println("-------------------------------");
                            System.out.println("1 - Inserir");
                            System.out.println("2 - Buscar");
                            System.out.println("3 - Excluir");
                            System.out.println("4 - Listar todas");
                            System.out.println("0 - Sair");
                            try {
                                opcao3 = Integer.parseInt(console.nextLine());
                            } catch (NumberFormatException e) {
                                opcao3 = -1;
                            }

                            switch (opcao3) {
                                case 1 -> {
                                    System.out.println("\nINCLUSÃO");
                                    String nome;
                                    try {
                                        System.out.print("Nome: ");
                                        nome = console.nextLine();
                                        Rotulo novoRotulo = new Rotulo(nome);
                                        int idNovoRotulo = arqRotulos.create(novoRotulo);
                                        System.out.println("Rótulo inserido com ID: " + idNovoRotulo);
                                    } catch (Exception e) {
                                        System.out.println("Dados inválidos!");
                                        break;
                                    }
                                }
                                case 2 -> {
                                    System.out.println("\nBUSCA");
                                    System.out.print("Nome: ");
                                    String nome = console.nextLine();
                                    Rotulo rotuloEncontrado = arqRotulos.buscarPorNome(nome);
                                    if (rotuloEncontrado != null) {
                                        System.out.println("Categoria encontrada: " + rotuloEncontrado);
                                    } else {
                                        System.out.println("Categoria não encontrada!");
                                    }
                                }
                                case 3 -> {
                                    System.out.println("\nEXCLUSÃO");
                                    ArrayList<Rotulo> rotulos = arqRotulos.listarRotulos();
                                    rotulos.sort(Comparator.comparing(Rotulo::getNome));

                                    for (int i = 0; i < rotulos.size(); i++) {
                                        System.out.println((i + 1) + " - " + rotulos.get(i).getNome());
                                    }

                                    int escolhaRotulo;
                                    while (true) {
                                        System.out.print("numero: ");
                                        escolhaRotulo = Integer.parseInt(console.nextLine()) - 1;
                                        if (escolhaRotulo >= 0 && escolhaRotulo < rotulos.size()) {
                                            break;
                                        }
                                        System.out.println("Opção inválida. Tente novamente.");
                                    }

                                    int id = rotulos.get(escolhaRotulo).getId();
                                    boolean excluida = arqRotulos.delete(id);
                                    if (excluida) {
                                        System.out.println("Rótulo excluído com sucesso!");
                                    } else {
                                        System.out.println("Rótulo não encontrado ou erro ao excluir.");
                                    }
                                }
                                case 4 -> {
                                    System.out.println("\nLISTA COMPLETA");
                                    ArrayList<Rotulo> lista = arqRotulos.listarRotulos();
                                    lista.forEach(System.out::println);
                                }
                                case 0 -> System.out.println("Voltando ao menu principal...");
                                default -> System.out.println("Opção inválida");
                            }

                        } while(opcao3 != 0);
                    break;

                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;

                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } while (opcao != 0);

            console.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static Tarefa criarTarefa(Scanner input, DateTimeFormatter formatter, ArquivoCategorias arqCategorias) throws Exception {
            System.out.print("Descrição: ");
            String descricao = input.nextLine();

            System.out.print("Data de início (dd-MM-yyyy): ");
            LocalDate dataInicio = LocalDate.parse(input.nextLine(), formatter);

            System.out.print("Data de término (dd-MM-yyyy): ");
            LocalDate dataTermino = LocalDate.parse(input.nextLine(), formatter);

            System.out.print("Status: ");
            String status = input.nextLine();

            System.out.print("Prioridade (0 = Baixa, 1 = Média, 2 = Alta): ");
            int prioridade = Integer.parseInt(input.nextLine());

            // Listar categorias
            ArrayList<Categoria> categorias = arqCategorias.listarCategorias();  // Lê todas as categorias
            categorias.sort(Comparator.comparing(Categoria::getNome));  // Ordenar por nome

            System.out.println("Categoria:");
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i + 1) + " - " + categorias.get(i).getNome());
            }

            int escolhaCategoria;
            while (true) {
                System.out.print("Número da categoria: ");
                escolhaCategoria = Integer.parseInt(input.nextLine()) - 1;
                if (escolhaCategoria >= 0 && escolhaCategoria < categorias.size()) {
                    break;
                }
                System.out.println("Opção inválida. Tente novamente.");
            }

            int idCategoria = categorias.get(escolhaCategoria).getId();

            return new Tarefa(0, descricao, dataInicio, dataTermino, status, prioridade, idCategoria);
        }

        public static void buscarTarefas(int idCategoria, ArquivoTarefas arqTarefas) {
            try {
                ArrayList<Tarefa> tarefasAssociadas = arqTarefas.buscarPorCategoria(idCategoria);

                if (tarefasAssociadas.isEmpty()) {
                    System.out.println("Nenhuma tarefa encontrada para esta categoria.");
                    return;
                }

                System.out.println("Tarefas associadas à categoria:");
                for (Tarefa tarefa : tarefasAssociadas) {
                    System.out.println(tarefa);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

