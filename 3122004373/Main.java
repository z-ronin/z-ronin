import java.io.*;
import java.util.*;

public class Main {
    //�������������в�������ԭ���ļ�·������Ϯ���ļ�·��������ļ�·��
    public static void main(String[] args) {
        // ��������в�������
        if (args.length != 3) {
            System.out.println("ʹ�÷���: java PaperPlagiarismChecker <ԭ���ļ�·��> <��Ϯ���ļ�·��> <����ļ�·��>");
            return;
        }

        // ��ȡ�����в���
        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            // ��ȡ�ļ�����
            String originalText = readFile(originalFilePath);
            String plagiarizedText = readFile(plagiarizedFilePath);

            // �������ƶ�
            double similarity = calculateSimilarity(originalText, plagiarizedText);

            // �����ƶ�д������ļ�
            String similarityString = String.format("similarity: %.2f%%", similarity * 100);
            writeToFile(outputFilePath, similarityString);

            System.out.println("���ƶȼ�����ɣ�����д�뵽����ļ���");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("��ȡ�ļ���д���ļ�ʱ��������");
        }
    }
    //��ȡ�ļ����ݡ�
    private static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }

        // ȥ������ո�ת��ΪСд
        return contentBuilder.toString().replaceAll("\\s+", " ").toLowerCase();
    }
    //���������ı�������ƶȣ�originalΪԭ�����ݣ�plagiarzedΪ��Ϯ���ݣ��������ƶȡ�
    private static double calculateSimilarity(String original, String plagiarized) {
        // ʹ��HashSet�洢�ʻ㣬�Զ�ȥ��
        Set<String> originalWords = new HashSet<>(Arrays.asList(original.split("\\s+")));
        Set<String> plagiarizedWords = new HashSet<>(Arrays.asList(plagiarized.split("\\s+")));

        // ���㽻���Ͳ���
        Set<String> commonWords = new HashSet<>(originalWords);
        commonWords.retainAll(plagiarizedWords);

        Set<String> allWords = new HashSet<>(originalWords);
        allWords.addAll(plagiarizedWords);

        // ���ƶ� = (������С) / (������С)
        return (double) commonWords.size() / allWords.size();
    }
    //������д���ļ���filePathΪ�ļ�·����content��Ҫд�������
    private static void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        }
    }
}