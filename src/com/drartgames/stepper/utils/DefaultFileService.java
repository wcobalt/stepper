package by.bsuirhc.client.util;

import java.io.*;
import java.util.logging.Logger;

public class DefaultFileService implements FileService {
    private static Logger logger = Logger.getLogger(DefaultFileService.class.getName());

    @Override
    public String readAllContent(File file) throws IOException {
        if (file == null) throw new IllegalArgumentException("File must not be null");

        logger.info("Reading all content from " + file.getName());

        BufferedReader br = new BufferedReader(new FileReader(file));
        String content = br.readLine();
        String line;

        while ((line = br.readLine()) != null) content += "\n" + line;

        br.close();

        return content;
    }

    @Override
    public byte[] readAllBinContent(File file) throws IOException {
        if (file == null) throw new IllegalArgumentException("File must not be null");

        logger.info("Reading all binary content from " + file.getName());

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        byte[] bytes =  bis.readAllBytes();
        bis.close();

        return bytes;
    }

    @Override
    public void writeBinToFile(File file, byte[] content, boolean append) throws IOException {
        if (file == null) throw new IllegalArgumentException("File must not be null");
        if (content == null) throw new IllegalArgumentException("Content must not be null");

        logger.info("Writing binary content to " + file.getName() + " Append: " + append);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, append));

        bos.write(content);
        bos.close();
    }

    @Override
    public void writeToFile(File file, String content, boolean append)  throws IOException {
        if (file == null) throw new IllegalArgumentException("File must not be null");
        if (content == null) throw new IllegalArgumentException("Content must not be null");

        logger.info("Writing content to " + file.getName() + " Append: " + append);

        BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));

        bw.write(content);
        bw.close();
    }
}
