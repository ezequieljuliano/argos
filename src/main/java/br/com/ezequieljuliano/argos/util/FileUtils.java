package br.com.ezequieljuliano.argos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileUtils {

    public void createDir(String dir) {
        File f = new File(dir);
        f.mkdirs();
        f = null;
    }

    public String readFileToString(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        FileChannel fc = stream.getChannel();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        String stringContent = Charset.defaultCharset().decode(bb).toString();
        stream.close();
        return stringContent;
    }

    public boolean moverArquivo(String source, String destination) {
        File file = new File(source);
        File dest = new File(destination);
        if (dest.exists())
            dest.delete();
        
        return file.renameTo(dest);
    }
}
