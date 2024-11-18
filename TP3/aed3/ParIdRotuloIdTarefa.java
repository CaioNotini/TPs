package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdRotuloIdTarefa implements RegistroArvoreBMais<ParIdRotuloIdTarefa> {

  private int idRotulo; // ID rotulo
  private int idTarefa;     // ID da tarefa
  private final short TAMANHO = 8;

  public ParIdRotuloIdTarefa() {
    this(-1, -1);
  }

  public ParIdRotuloIdTarefa(int idCategoria) {
    this(idCategoria, -1);
  }

  public ParIdRotuloIdTarefa(int idRotulo, int idTarefa) {
    this.idRotulo = idRotulo;
    this.idTarefa = idTarefa;
  }

  public int getIdRotulo() {
    return idRotulo;
  }

  public int getIdTarefa() {
    return idTarefa;
  }

  public ParIdRotuloIdTarefa clone() {
    return new ParIdRotuloIdTarefa(this.idRotulo, this.idTarefa);
  }

  @Override
  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParIdRotuloIdTarefa outro) {
    if (this.idRotulo != outro.idRotulo) {
      return Integer.compare(this.idRotulo, outro.idRotulo);
    } else {
      return (this.idTarefa == -1 || outro.idTarefa == -1) ? 0 : Integer.compare(this.idTarefa, outro.idTarefa);
    }
  }

  @Override
  public String toString() {
    return "ID Categoria: " + this.idTarefa + ", ID Tarefa: " + this.idTarefa;
  }

  @Override
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(this.idRotulo);
    dos.writeInt(this.idTarefa);
    return baos.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idRotulo = dis.readInt();
    this.idTarefa = dis.readInt();
  }

  public int compareTo(ParIdId obj) {
      throw new UnsupportedOperationException("Not supported yet.");
  }
}