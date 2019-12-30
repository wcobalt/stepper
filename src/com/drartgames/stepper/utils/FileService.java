package com.drartgames.stepper.utils;

import java.io.File;
import java.io.IOException;

public interface FileService {
    String readAllContent(File file) throws IOException;

    byte[] readAllBinContent(File file) throws IOException;

    void writeBinToFile(File file, byte[] content, boolean append) throws IOException;

    void writeToFile(File file, String content, boolean mode) throws IOException;
}
