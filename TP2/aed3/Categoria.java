package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Categoria implements Registro {

    int id_categoria;
    String nome_categoria;

        public Categoria() {
        this(-1, "");
    }

    public Categoria(String n) {
        this(-1, n);
    }

    public Categoria(int i, String n) {
        this.id_categoria = i;
        this.nome_categoria = n;
        }

    public void setId(int id) {
        this.id_categoria = id;
    }

    public int getId() {
        return id_categoria;
    }

        public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id_categoria);
        dos.writeUTF(this.nome_categoria);
        
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.id_categoria = dis.readInt();
        this.nome_categoria = dis.readUTF();
        
    }

    public String toString() {
        return "\nID..................: " + this.id_categoria +
               "\nNome................: " + this.nome_categoria;
               
    }
    
}
