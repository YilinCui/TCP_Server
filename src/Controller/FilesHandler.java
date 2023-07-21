package Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public interface FilesHandler {
    static void creatFolder(String filePath){
        Path dirPath = Paths.get(filePath);

        try {
            System.out.println("Current working directory: " + System.getProperty("user.dir"));

            // 检查目录是否存在
            if (Files.exists(dirPath)) {
                // 如果目录存在，删除它
//                Files.walk(dirPath)
//                        .sorted(Comparator.reverseOrder())
//                        .forEach(path -> {
//                            try {
//                                Files.delete(path);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
            }
            // 创建新的目录，包括所有不存在的父目录
            Files.createDirectories(dirPath);

        } catch (IOException e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }
    }

    static void deleteFile(String filePath){
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try{
                Files.delete(path);
            } catch (Exception e){
                System.out.println("Deleting file operation failed!!");
            }

        } else {
            System.out.println(filePath + " non-exists!!");
        }
    }
}
