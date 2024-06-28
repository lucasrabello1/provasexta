import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Aluno> listaDeAlunos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("alunos.csv"))) {
            String linhaCabecalho = br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    int matricula = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    double nota = Double.parseDouble(dados[2].replace(",", ".").trim()); // Substitui ',' por '.' para garantir o formato correto
                    Aluno aluno = new Aluno(matricula, nome, nota);
                    listaDeAlunos.add(aluno);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato numérico: " + e.getMessage());
            return;
        }

        int quantidadeAlunos = listaDeAlunos.size();
        int quantidadeAprovados = 0;
        int quantidadeReprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : listaDeAlunos) {
            double nota = aluno.getNota();
            somaNotas += nota;
            if (nota >= 6.0) {
                quantidadeAprovados++;
            } else {
                quantidadeReprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double mediaGeral = somaNotas / quantidadeAlunos;

        try (PrintWriter pw = new PrintWriter(new FileWriter("resumo.csv"))) {
            pw.println("Quantidade de alunos na turma: " + quantidadeAlunos);
            pw.println("Quantidade de alunos aprovados: " + quantidadeAprovados);
            pw.println("Quantidade de alunos reprovados: " + quantidadeReprovados);
            pw.println("Menor nota da turma: " + menorNota);
            pw.println("Maior nota da turma: " + maiorNota);
            pw.println("Média geral da turma: " + mediaGeral);
        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo: " + e.getMessage());
        }
    }
}
