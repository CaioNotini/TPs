package aed3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class IO {

    public static void main(String[] args) {
        ArvoreBMais<ParNomeId> arvore;
        ArvoreBMais<ParIdId> arvoreIds;
        Tarefa t;
        Scanner console = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            File d = new File("dados");
            if (!d.exists())
                d.mkdir();
            arvore = new ArvoreBMais<>(ParNomeId.class.getConstructor(), 5, "dados/arvoreCategoria.db");
            arvoreIds = new ArvoreBMais<>(ParIdId.class.getConstructor(), 5, "dados/arvoreIds.db");

            Arquivos<Tarefa> arqTarefas = new Arquivos<>("tarefas", Tarefa.class.getConstructor());

            int opcao;
            do {
                System.out.println("\n\n-------------------------------");
                System.out.println("              MENU");
                System.out.println("-------------------------------");
                System.out.println("1 - Tarefa");
                System.out.println("2 - Categoria");
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
                                    Tarefa novaTarefa = criarTarefa(console, formatter, arvore);
                                    int idNovaTarefa = arqTarefas.create(novaTarefa);
                                    System.out.println("Tarefa inserida com ID: " + idNovaTarefa);

                                    // Vincula a tarefa à categoria na arvoreIds
                                    arvoreIds.create(new ParIdId(novaTarefa.getIdCategoria(), idNovaTarefa));
                                }

                                case 2 -> {
                                    System.out.println("\nBuscar tarefa por ID:");
                                    System.out.print("Digite o ID da tarefa: ");
                                    int idBuscar = Integer.parseInt(console.nextLine());
                                    t = arqTarefas.read(idBuscar);
                                    if (t != null) {
                                        System.out.println("Tarefa encontrada:");
                                        System.out.println(t);
                                    } else {
                                        System.out.println("Tarefa não encontrada!");
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
                                        Tarefa tarefaAtualizada = criarTarefa(console, formatter, arvore);
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
                                            arvoreIds.delete(new ParIdId(t.getIdCategoria(), idExcluido));
                                            System.out.println("Tarefa excluída com sucesso!");
                                        } else {
                                            System.out.println("Exclusão cancelada.");
                                        }
                                    } else {
                                        System.out.println("Tarefa não encontrada!");
                                    }
                                }
                                case 5 ->{
                                    System.out.println("\nDigite a categoria que deseja buscar: ");
                                    ArrayList<ParNomeId> categorias = arvore.read(null);
                                    categorias.sort(ParNomeId::compareTo);

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

                                    buscarTarefas(idCategoria, arvoreIds, arqTarefas);

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
                            System.out.println("5 - Imprimir");
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
                                    int id = 0;
                                    try {
                                        System.out.print("Nome: ");
                                        nome = console.nextLine();
                                        System.out.print("ID: ");
                                        id = Integer.parseInt(console.nextLine());
                                    } catch (Exception e) {
                                        System.out.println("Dados inválidos!");
                                        break;
                                    }
                                    arvore.create(new ParNomeId(nome, id));
                                    arvore.print();
                                }
                                case 2 -> {
                                    System.out.println("\nBUSCA");
                                    System.out.print("Nome: ");
                                    String nome = console.nextLine();
                                    ArrayList<ParNomeId> lista = arvore.read(new ParNomeId(nome, -1));
                                    System.out.print("Resposta: ");
                                    lista.forEach(System.out::println);
                                }
                                case 3 -> {
                                    System.out.println("\nEXCLUSÃO");
                                    System.out.print("Nome: ");
                                    String nome = console.nextLine();
                                    System.out.print("Num2: ");
                                    int id = Integer.parseInt(console.nextLine());

                                    // Verifica se há tarefas vinculadas antes de excluir
                                    ArrayList<ParIdId> tarefasVinculadas = arvoreIds.read(new ParIdId(id, -1));
                                    if (!tarefasVinculadas.isEmpty()) {
                                        System.out.println("Categoria não pode ser excluída, pois possui tarefas vinculadas.");
                                    } else {
                                        arvore.delete(new ParNomeId(nome, id));
                                        arvore.print();
                                    }
                                }
                                case 4 -> {
                                    System.out.println("\nLISTA COMPLETA");
                                    ArrayList<ParNomeId> lista = arvore.read(null);
                                    lista.forEach(System.out::println);
                                }
                                case 5 -> arvore.print();
                                case 0 -> System.out.println("Voltando ao menu principal...");
                                default -> System.out.println("Opção inválida");
                            }
                        } while (o != 0);
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

    public static Tarefa criarTarefa(Scanner input, DateTimeFormatter formatter, ArvoreBMais<ParNomeId> arvore) throws Exception {
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

        ArrayList<ParNomeId> categorias = arvore.read(null);
        categorias.sort(ParNomeId::compareTo);

        System.out.println("Categoria:");
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println((i + 1) + " - " + categorias.get(i).getNome());
        }

        int escolhaCategoria;
        while (true) {
            System.out.print("numero: ");
            escolhaCategoria = Integer.parseInt(input.nextLine()) - 1;
            if (escolhaCategoria >= 0 && escolhaCategoria < categorias.size()) {
                break;
            }
            System.out.println("Opção inválida. Tente novamente.");
        }

        int idCategoria = categorias.get(escolhaCategoria).getId();

        return new Tarefa(0, descricao, dataInicio, dataTermino, status, prioridade, idCategoria);
    }

    public static void buscarTarefas(int idCategoria, ArvoreBMais<ParIdId> arvoreIds, Arquivos<Tarefa> arqTarefa) {
            try {
                ArrayList<ParIdId> tarefasAssociadas = arvoreIds.read(new ParIdId(idCategoria, -1));

                if (tarefasAssociadas.isEmpty()) {
                    System.out.println("Nenhuma tarefa encontrada para esta categoria.");
                    return;
                }

                System.out.println("Tarefas associadas à categoria: ");
                for (ParIdId par : tarefasAssociadas) {
                    Tarefa tarefa = arqTarefa.read(par.getIdTarefa());
                    if (tarefa != null) {
                        System.out.println(tarefa);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

