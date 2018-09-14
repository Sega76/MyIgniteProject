package stepik.io;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.util.Arrays;

public class MainSerializ {
    public static void main(String[] args) throws IOException {
        Animal[] animalM1 = {new Animal("Cat"), new Animal("Dog"), new Animal("Elephant"),
                new Animal("Cock"), new Animal("Bull"), new Animal("Ant"),
                new Animal("Tentecles"), new Animal("Worm")};
        ByteArrayOutputStream bai = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bai);
        oos.writeInt(animalM1.length);
        for (int i = 0; i < animalM1.length; i++) {
            oos.writeObject(animalM1[i]);
        }
        oos.flush();
        oos.close();
        Animal[] animalM2 = deserializeAnimalArray(bai.toByteArray());
        System.out.println(Arrays.toString(animalM1));
        System.out.println(Arrays.toString(animalM2));
    }

    private static Animal[] deserializeAnimalArray(byte[] data) {
        try (ObjectInputStream oIS = new ObjectInputStream(new ByteArrayInputStream(data))) {
            Integer i = oIS.readInt();
            System.out.println(i);
            Animal[] arr = new Animal[i];
            for (int j = 0; j < i; j++) {
                arr[j] = (Animal)oIS.readObject();
//                System.out.println(arr[j]);
            }
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

    }

}
