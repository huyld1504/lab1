/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class FileManager implements IFileManager<String> {

    public FileManager() {
    }

    @Override
    public List<String> loadFile(String fileName) throws Exception {
        FileReader fReader = null;
        BufferedReader bReader = null;
        List<String> result = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            fReader = new FileReader(fileName);
            bReader = new BufferedReader(fReader);

            while (bReader.ready()) {
                String dataLine = bReader.readLine();
                result.add(dataLine);
            }
        }

        if (fReader != null) {
            fReader.close();
        }
        if (bReader != null) {
            bReader.close();
        }

        return result;
    }

    @Override
    public boolean saveFile(String fileName, List<String> list) throws Exception {
        PrintWriter pWriter = null;
        File file = new File(fileName);

        if (file.exists()) {
            pWriter = new PrintWriter(fileName);
            for (String data : list) {
                String tmp = data + "\n";
                pWriter.print(tmp);
                pWriter.flush();
            }
        }

        if (pWriter != null) {
            pWriter.close();
            return true;
        } else {
            return false;
        }
    }
}
