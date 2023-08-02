import Controller.RandomData;
import ParseData.DataConvert;

import java.util.zip.CRC32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class Animal {
    void makeSound() {
        System.out.println("The animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("The dog says: woof woof");
    }
}

class Cat extends Animal {
    @Override
    void makeSound() {
        System.out.println("The cat says: meow");
    }
}

public class Main {
    public static void main(String[] args) {
//        // 创建一个Animal引用变量，但实际类型是Dog
//        Animal myDog = new Dog();
//        myDog.makeSound();  // 输出 "The dog says: woof woof"
//
//        // 创建一个Animal引用变量，但实际类型是Cat
//        Animal myCat = new Cat();
//        myCat.makeSound();  // 输出 "The cat says: meow"

        Dog myDog = new Dog();
        Cat myCat = new Cat();

        handleSound(myDog);  // 输出 "The dog says: woof woof"
        handleSound(myCat);  // 输出 "The cat says: meow"

    }

    public static void handleSound(Animal animal) {
        animal.makeSound();
    }

}
//
//
//public class Main {
//    public static void main(String[] args) {
//
//
////        String hexData = "5A 06 63 23 CD 79 BA 04 11 70 09 22 77 44 52 5F 16 7C 7E 1A 44 36 D1 04 45 16 51 3E 06 26 5B 23 37 66 44 1C 3E 8C BC 04 07 5D 2F 79 00 64 04 55 11 5B 66 04 B7 E8 BA 04 5C 05 75 70 26 29 50 52 5C 01 24 76 1C C6 B3 04 5B 2A 56 17 7E 69 46 39 22 25 08 4D 37 AA CE 04 36 20 55 3F 2A 6B 08 42 79 77 12 2B AF 54 CF 04 4F 57 6E 1A 2C 35 0D 34 33 FA 0F F5";
////        System.out.println("数据长度: " + getDataLength(hexData));
//
////        String data = "AC 7A D2 04";
////        byte[] arr = DataConvert.hexStringToByteArray(data);
////        System.out.println(DataConvert.getFormattedTimestampFromBytes(arr));
////        byte bb = 0x01;
////        System.out.println(bb);
//
//
//    }
//
//    public static int getDataLength (String data){
//        // 我们用空格来分割这个字符串，得到的数组的长度就是数据的长度
//        String[] hexValues = data.split(" ");
//        return hexValues.length;
//    }
//
//
//
//
//}
