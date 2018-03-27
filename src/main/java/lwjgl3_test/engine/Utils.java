package lwjgl3_test.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static String loadResource(String fileName) throws IOException {
        String result = "";
        FileReader fileReader = null;

        fileReader = new FileReader("src/main/java/lwjgl3_test/resources" + fileName);
        Scanner scanner = new Scanner(fileReader);
        result = scanner.useDelimiter("\\A").next();

        if (fileReader != null) {
            fileReader.close();
        }

        return result;
    }

    public static List<String> readAllLines(String fileName) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = null;

        bufferedReader = new BufferedReader(new FileReader("src/main/java/lwjgl3_test/resources" + fileName));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }

        bufferedReader.close();

        return list;
    }

    public static InputStream pngToInputStream(String fileName) throws IOException {
        BufferedImage img = ImageIO.read(new File("src/main/java/lwjgl3_test/resources" + fileName));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "png", os);
        InputStream fis = new ByteArrayInputStream(os.toByteArray());

        os.close();
        fis.close();

        return fis;
    }

    public static float[] listToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }
}