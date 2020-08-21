package com.delains.ui.sales;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFileToPrint {

    private File CreateFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
} else {
}
        } catch (IOException e) {
e.printStackTrace();
        }
        return file;
    }

    public void writeToFile(String fileName, String textToWrite){
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(textToWrite);
            myWriter.close();
} catch (IOException e) {
e.printStackTrace();
        }
    }

    private void DeleteFile(String fileName){
        File myObj = new File(fileName);
        if (myObj.delete()) {
} else {
}
    }

}
