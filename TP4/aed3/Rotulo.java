package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Rotulo implements Registro {

    int id_rotulo;
    String nome_rotulo;

    public Rotulo() {
        this(-1, "");
    }

    public Rotulo(String n) {
        this(-1, n);
    }

    public Rotulo(int i, String n) {
        this.id_rotulo = i;
        this.nome_rotulo = n;
        }

    public void setNome(String nome) {
        this.nome_rotulo = nome;
    }

    public String getNome() {
        return nome_rotulo;
    }

    public void setId(int id) {
        this.id_rotulo= id;
    }

    public int getId() {
        return id_rotulo;
    }

        public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id_rotulo);
        dos.writeUTF(this.nome_rotulo);
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.id_rotulo = dis.readInt();
        this.nome_rotulo = dis.readUTF();
        
    }

    public String toString() {
        return "\nID..................: " + this.id_rotulo +
               "\nNome................: " + this.nome_rotulo;
               
    }
    
}
