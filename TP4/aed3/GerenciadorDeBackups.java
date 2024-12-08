package aed3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//para controle dos backups
public class GerenciadorDeBackups {

    public static List<String> listarBackupsNaPasta(String backupFolderPath) {
        File backupFolder = new File(backupFolderPath);
        List<String> backups = new ArrayList<>();

        if (backupFolder.exists() && backupFolder.isDirectory()) {
            File[] files = backupFolder.listFiles((dir, name) -> name.endsWith(".db"));
            if (files != null) {
                for (File file : files) {
                    backups.add(file.getName());
                }
            }
        }

        return backups;
    }

    public static void listarEEscolherDescompactacao(String backupFolderPath, String outputFolderPath) {
        Scanner scanner = new Scanner(System.in);
    
        try {
            // Listar todos os backups disponíveis
            List<String> backups = listarBackupsNaPasta(backupFolderPath);
            if (backups.isEmpty()) {
                System.out.println("Nenhum backup encontrado na pasta: " + backupFolderPath);
                return;
            }
    
            System.out.println("Backups disponíveis:");
            for (int i = 0; i < backups.size(); i++) {
                System.out.println((i + 1) + ". " + backups.get(i));
            }
    
            // Escolher um backup
            System.out.print("Escolha um número para selecionar o backup: ");
            int escolhaBackup;
            if (scanner.hasNextInt()) {
                escolhaBackup = scanner.nextInt();
                scanner.nextLine(); //limpar buffer de entrada
            } else {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                return;
            }
    
            if (escolhaBackup < 1 || escolhaBackup > backups.size()) {
                System.out.println("Escolha inválida. O número deve estar na lista.");
                return;
            }
    
            String backupSelecionado = backups.get(escolhaBackup - 1);
            String backupFilePath = backupFolderPath + "/" + backupSelecionado;
    
            // Chama o método para descompactar arquivos
            System.out.println("Descompactando o backup selecionado: " + backupSelecionado);
            LZW.descompactarArquivos(backupFilePath, outputFolderPath);
            System.out.println("Backup descompactado com sucesso na pasta: " + outputFolderPath);
    
        } catch (Exception e) {
            System.err.println("Erro ao processar a descompactação: " + e.getMessage());
            e.printStackTrace();
        } 
    }
    
}
