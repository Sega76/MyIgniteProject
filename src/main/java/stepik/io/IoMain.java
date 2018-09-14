package stepik.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class IoMain {
    public static void main(String[] args) throws IOException {


        byte[] bytes = {65, 66, 13, 13, 10, 10, 13, 67, 13, 13};
        for (byte b1:bytes) {
            System.in.read(bytes);
        }

    }
    public static int checkSumOfStream(InputStream inputStream) throws IOException {
        int b = 0;
        int cn = 0;
        while ((b=inputStream.read())!=-1){
            System.out.println(b);
            cn=(Integer.rotateLeft(cn,1)^Byte.toUnsignedInt((byte)b));
        }
        return cn;
    }


    public static int mySistem(InputStream inputStream) {
        int b = 0;
        int c=0;
        int cn = 0;
        try {
            while (true) {
                b = System.in.read();
                if (b == 13) {
                    if ((c = System.in.read()) == 10) {
                        b = c;
                    } else {
                        System.out.write(b);
                        System.out.flush();
                        b = c;
                    }
                }
                System.out.write(b);
                System.out.flush();

            }
        } catch (IOException e){

        }
        return cn;
    }

    public static String readAsString(InputStream inputStream, Charset charset) throws IOException {

        StringBuilder sb = new StringBuilder();
        InputStreamReader iSR = new InputStreamReader(inputStream,charset);
        int i;
        while((i =iSR.read())!=-1){
            char ch = (char)i;
            sb.append(ch);
        }
        return sb.toString();


    }


}
