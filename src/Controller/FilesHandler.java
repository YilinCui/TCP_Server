package Controller;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public interface FilesHandler {
    static void creatFolder(String filePath){
        Path dirPath = Paths.get(filePath);

        try {
            System.out.println("Current working directory: " + System.getProperty("user.dir"));

            // 检查目录是否存在
//            if (Files.exists(dirPath)) {
//                // 如果目录存在，删除它
//                Files.walk(dirPath)
//                        .sorted(Comparator.reverseOrder())
//                        .forEach(path -> {
//                            try {
//                                Files.delete(path);
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//            }
            if (!Files.exists(dirPath)) {
                System.out.println("File does not exist. Creating new File/Folder");
            }
            // 创建新的目录，包括所有不存在的父目录
            Files.createDirectories(dirPath);

        } catch (IOException e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }
    }

    public static void deleteDirectoryRecursively(String dirPath) {
        System.out.println("删除本地LocalData目录");
        Path path = Paths.get(dirPath);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("删目录的时候出问题了！目录为空！");
        }
    }
}
