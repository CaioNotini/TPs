package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tarefa implements Registro {

    int id;
    String nome;
    LocalDate dataCriacao;
    LocalDate dataConclusao;
    String status;
    int prioridade;
    int id_categoria; // Novo atributo para o ID da categoria
    List<Integer> listaRotulos;

    public Tarefa() {
        this(-1, "", LocalDate.of(1970, 1, 1), LocalDate.of(1970, 1, 1), "", -1, -1, null);
    }

    public Tarefa(String n, LocalDate dCri, LocalDate dCon, String s, int p, int idCategoria, List<Integer> listaRotulos) {
        this(-1, n, dCri, dCon, s, p, idCategoria, listaRotulos);
    }

    public Tarefa(int i, String n, LocalDate criacao, LocalDate conclusao, String s, int p, int idCategoria, List<Integer> listaRotulos) {
        this.id = i;
        this.nome = n;
        this.dataCriacao = criacao;
        this.dataConclusao = conclusao;
        this.status = s;
        this.prioridade = p;
        this.id_categoria = idCategoria; // Inicializa o ID da categoria
        this.listaRotulos = listaRotulos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdCategoria() {
        return id_categoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.id_categoria = idCategoria;
    }
    
    public void setListaRotulos(List<Integer> listaRotulos) {
        this.listaRotulos = listaRotulos;
    }

    public List<Integer> getListaRotulos() {
        return this.listaRotulos;
    }

    public void removeRotulo (int idRotulo) {
        listaRotulos.remove(idRotulo);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt((int) this.dataCriacao.toEpochDay());
        dos.writeInt((int) this.dataConclusao.toEpochDay());
        dos.writeUTF(this.status);
        dos.writeInt(this.prioridade);
        dos.writeInt(this.id_categoria); // Serializa o ID da categoria
        // serializar o tamanho
        dos.writeInt(listaRotulos.size());
        // Serializa cada ID da lista
        for (Integer idRotulo : listaRotulos) {
            dos.writeInt(idRotulo);
        }

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.dataCriacao = LocalDate.ofEpochDay(dis.readInt());
        this.dataConclusao = LocalDate.ofEpochDay(dis.readInt());
        this.status = dis.readUTF();
        this.prioridade = dis.readInt();
        this.id_categoria = dis.readInt(); // Desserializa o ID da categoria

        int size = dis.readInt();
        listaRotulos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listaRotulos.add(dis.readInt());
        }
    }

    public String toString() {
        return "\nID..................: " + this.id +
               "\nNome................: " + this.nome +
               "\nData criacao........: " + this.dataCriacao +
               "\nData conclusao......: " + this.dataConclusao +
               "\nStatus..............: " + this.status +
               "\nPrioridade..........: " + this.prioridade +
               "\nID Categoria........: " + this.id_categoria +
               "\nIDs Rótulos.........: " + this.listaRotulos;
    }
}
