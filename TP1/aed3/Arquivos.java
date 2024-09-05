package aed3;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;


public class Arquivos<T extends Registro> {

    RandomAccessFile arquivo;
    String nomeArquivo;
    final int TAM_CABECALHO = 4;
    Constructor<T> construtor;
    HashExtensivel<ParIDEndereco> indiceDireto;

    public Arquivos(String n, Constructor<T> c) throws Exception {
        // Cria o diretório, se necessário
        File f = new File(".\\dados");
        if(!f.exists())
            f.mkdir();

        this.nomeArquivo = ".\\dados\\"+n+".db";
        this.construtor = c;
        arquivo = new RandomAccessFile(this.nomeArquivo, "rw");
        if(arquivo.length()<TAM_CABECALHO)
            arquivo.writeInt(0);

        indiceDireto = new HashExtensivel<>(
            ParIDEndereco.class.getConstructor(), 
            4,   // tamanho do cesto
            ".\\dados\\"+n+".hash_d.db",   // arquivo para o diretório
            ".\\dados\\"+n+".hash_c.db"    // arquivo de cestos
        );
        
    }

    public int create(T entidade) throws Exception {

        // Determina o ID
        arquivo.seek(0);
        int novoId = arquivo.readInt() + 1;
        entidade.setId(novoId);
        arquivo.seek(0);
        arquivo.writeInt(novoId);

        // Grava o novo registro
        arquivo.seek(arquivo.length());
        long endereco = arquivo.getFilePointer();
        byte[] b = entidade.toByteArray();
        arquivo.writeByte(' ');
        arquivo.writeShort((short)b.length);
        arquivo.write(b);

        // Insere o registro no índice
        indiceDireto.create(new ParIDEndereco(novoId, endereco));

        return novoId;
    }

    public T read(int id) throws Exception {

        short tam;
        byte[] b;
        byte lapide;
        T entidade = this.construtor.newInstance();

        ParIDEndereco pie = indiceDireto.read(id);
        if(pie!=null) {
            arquivo.seek(pie.getEndereco());
            lapide = arquivo.readByte();
            if(lapide==' ') {
                tam = arquivo.readShort();
                b = new byte[tam];
                arquivo.read(b);
                entidade.fromByteArray(b);
                if(entidade.getId()==id)
                    return entidade; 
            }
        }
        return null;
    }
    

    public boolean delete(int id) throws Exception {

        short tam;
        byte[] b;
        byte lapide;
        T entidade = this.construtor.newInstance();
        
        ParIDEndereco pie = indiceDireto.read(id);
        if(pie!=null) {
            arquivo.seek(pie.getEndereco());
            lapide = arquivo.readByte();
            if(lapide==' ') {               
                tam = arquivo.readShort();
                b = new byte[tam];
                arquivo.read(b);
                entidade.fromByteArray(b);
                if(entidade.getId()==id) {
                    arquivo.seek(pie.getEndereco());
                    arquivo.writeByte('*');
                    indiceDireto.delete(id);
                    return true;
                }
            }
        }
        return false; 
    }
    

    public boolean update(T novaEntidade) throws Exception {

        short tam;
        byte[] b;
        byte lapide;
        T entidade = this.construtor.newInstance();
        ParIDEndereco pie = indiceDireto.read(novaEntidade.getId());
        if(pie != null) {
            arquivo.seek(pie.getEndereco());
            lapide = arquivo.readByte();
            if(lapide==' ') {                
                tam = arquivo.readShort();
                b = new byte[tam];
                arquivo.read(b);
                entidade.fromByteArray(b);
                if(entidade.getId()==novaEntidade.getId()) {
                    byte[] b2 = novaEntidade.toByteArray();
                    int tam2 = b2.length;
                    if(tam2<=tam){
                        arquivo.seek(pie.getEndereco()+3);
                        arquivo.write(b2);
                    } else {
                        arquivo.seek(pie.getEndereco());
                        arquivo.writeByte('*');  
                        arquivo.seek(arquivo.length());
                        long novoEndereco = arquivo.getFilePointer();
                        arquivo.writeByte(' ');
                        arquivo.writeShort(tam2);
                        arquivo.write(b2); 
                        indiceDireto.update(new ParIDEndereco(novaEntidade.getId(), novoEndereco));                     
                    }
                    return true;
                }
            }
        }
        return false; 
    }
    
}
