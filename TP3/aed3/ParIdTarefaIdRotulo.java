package aed3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdTarefaIdRotulo implements RegistroArvoreBMais<ParIdTarefaIdRotulo> {

    private int idTarefa; // ID da Tarefa
    private int idRotulo; // ID do Rótulo
    private final short TAMANHO = 8;

    public ParIdTarefaIdRotulo() {
        this(-1, -1);
    }

    public ParIdTarefaIdRotulo(int idTarefa) {
        this(idTarefa, -1);
    }

    public ParIdTarefaIdRotulo(int idTarefa, int idRotulo) {
        this.idTarefa = idTarefa;
        this.idRotulo = idRotulo;
    }

    public int getIdTarefa() {
        return idTarefa;
    }

    public int getIdRotulo() {
        return idRotulo;
    }

    @Override
    public ParIdTarefaIdRotulo clone() {
        return new ParIdTarefaIdRotulo(this.idTarefa, this.idRotulo);
    }

    @Override
    public short size() {
        return this.TAMANHO;
    }

    @Override
    public int compareTo(ParIdTarefaIdRotulo outro) {
        if (this.idTarefa != outro.idTarefa) {
            return Integer.compare(this.idTarefa, outro.idTarefa);
        } else {
            return (this.idRotulo == -1 || outro.idRotulo == -1) ? 0 : Integer.compare(this.idRotulo, outro.idRotulo);
        }
    }

    @Override
    public String toString() {
        return "ID Tarefa: " + this.idTarefa + ", ID Rótulo: " + this.idRotulo;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idTarefa);
        dos.writeInt(this.idRotulo);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idTarefa = dis.readInt();
        this.idRotulo = dis.readInt();
    }

    public int compareTo(ParIdId obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
