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

    public Tarefa() {
        this(-1, "", LocalDate.of(1970, 1, 1), LocalDate.of(1970, 1, 1), "", -1);
    }

    public Tarefa(String n, LocalDate dCri, LocalDate dCon, String s, int p) {  
        this(-1, n, dCri, dCon, s, p);
    }

    public Tarefa(int i, String n, LocalDate criacao, LocalDate conclusao, String s, int p) {  
        this.id = i;
        this.nome = n;
        this.dataCriacao = criacao;
        this.dataConclusao = conclusao;
        this.status = s;
        this.prioridade = p;      
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] toByteArray() throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt((int)this.dataCriacao.toEpochDay());
        dos.writeInt((int)this.dataConclusao.toEpochDay());
        dos.writeUTF(this.status);
        dos.writeInt(this.prioridade);


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

    }

    public String toString() {
       
        return
             "\nID..................: " + this.id +
             "\nNome................: " + this.nome +
             "\nData criacao........: " + this.dataCriacao +
             "\nData conclusao......: " + this.dataConclusao +
             "\nStatus..............: " + this.status +
             "\nPrioridade..........: " + this.prioridade;



    }

}
