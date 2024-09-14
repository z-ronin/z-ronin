import java.io.*;
import java.util.*;

public class Main {
    //主方法，命令行参数包含原文文件路径，抄袭版文件路径和输出文件路径
    public static void main(String[] args) {
        // 检查命令行参数数量
        if (args.length != 3) {
            System.out.println("使用方法: java PaperPlagiarismChecker <原文文件路径> <抄袭版文件路径> <输出文件路径>");
            return;
        }

        // 读取命令行参数
        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            // 读取文件内容
            String originalText = readFile(originalFilePath);
            String plagiarizedText = readFile(plagiarizedFilePath);

            // 计算相似度
            double similarity = calculateSimilarity(originalText, plagiarizedText);

            // 将相似度写入输出文件
            String similarityString = String.format("similarity: %.2f%%", similarity * 100);
            writeToFile(outputFilePath, similarityString);

            System.out.println("相似度计算完成，并已写入到输出文件。");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取文件或写入文件时发生错误。");
        }
    }
    //读取文件内容。
    private static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }

        // 去除多余空格并转换为小写
        return contentBuilder.toString().replaceAll("\\s+", " ").toLowerCase();
    }
    //计算两个文本间的相似度，original为原文内容，plagiarzed为抄袭内容，返回相似度。
    private static double calculateSimilarity(String original, String plagiarized) {
        // 使用HashSet存储词汇，自动去重
        Set<String> originalWords = new HashSet<>(Arrays.asList(original.split("\\s+")));
        Set<String> plagiarizedWords = new HashSet<>(Arrays.asList(plagiarized.split("\\s+")));

        // 计算交集和并集
        Set<String> commonWords = new HashSet<>(originalWords);
        commonWords.retainAll(plagiarizedWords);

        Set<String> allWords = new HashSet<>(originalWords);
        allWords.addAll(plagiarizedWords);

        // 相似度 = (交集大小) / (并集大小)
        return (double) commonWords.size() / allWords.size();
    }
    //将内容写进文件，filePath为文件路径，content是要写入的内容
    private static void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        }
    }
}