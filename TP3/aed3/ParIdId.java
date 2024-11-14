package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdId implements RegistroArvoreBMais<ParIdId> {

  private int idCategoria; // ID da categoria 
  private int idTarefa;     // ID da tarefa
  private final short TAMANHO = 8;

  public ParIdId() {
    this(-1, -1);
  }

  public ParIdId(int idCategoria) {
    this(idCategoria, -1);
  }

  public ParIdId(int idCategoria, int idTarefa) {
    this.idCategoria = idCategoria;
    this.idTarefa = idTarefa;
  }

  public int getIdCategoria() {
    return idCategoria;
  }

  public int getIdTarefa() {
    return idTarefa;
  }

  @Override
  public ParIdId clone() {
    return new ParIdId(this.idCategoria, this.idTarefa);
  }

  @Override
  public short size() {
    return this.TAMANHO;
  }

  @Override
  public int compareTo(ParIdId outro) {
    if (this.idCategoria != outro.idCategoria) {
      return Integer.compare(this.idCategoria, outro.idCategoria);
    } else {
      return (this.idTarefa == -1 || outro.idTarefa == -1) ? 0 : Integer.compare(this.idTarefa, outro.idTarefa);
    }
  }

  @Override
  public String toString() {
    return "ID Categoria: " + this.idCategoria + ", ID Tarefa: " + this.idTarefa;
  }

  @Override
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.idCategoria);
    dos.writeInt(this.idTarefa);
    return baos.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idCategoria = dis.readInt();
    this.idTarefa = dis.readInt();
  }
}
