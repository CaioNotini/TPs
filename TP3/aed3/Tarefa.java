package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Tarefa implements Registro {

    int id;
    String nome;
    LocalDate dataCriacao;
    LocalDate dataConclusao;
    String status;
    int prioridade;
    int id_categoria; // Novo atributo para o ID da categoria

    public Tarefa() {
        this(-1, "", LocalDate.of(1970, 1, 1), LocalDate.of(1970, 1, 1), "", -1, -1);
    }

    public Tarefa(String n, LocalDate dCri, LocalDate dCon, String s, int p, int idCategoria) {
        this(-1, n, dCri, dCon, s, p, idCategoria);
    }

    public Tarefa(int i, String n, LocalDate criacao, LocalDate conclusao, String s, int p, int idCategoria) {
        this.id = i;
        this.nome = n;
        this.dataCriacao = criacao;
        this.dataConclusao = conclusao;
        this.status = s;
        this.prioridade = p;
        this.id_categoria = idCategoria; // Inicializa o ID da categoria
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
    }

    public String toString() {
        return "\nID..................: " + this.id +
               "\nNome................: " + this.nome +
               "\nData criacao........: " + this.dataCriacao +
               "\nData conclusao......: " + this.dataConclusao +
               "\nStatus..............: " + this.status +
               "\nPrioridade..........: " + this.prioridade +
               "\nID Categoria........: " + this.id_categoria;
    }
}
