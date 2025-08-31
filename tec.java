import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class tec {
    // Lista padrão ampliada de palavras para treino
    private static final String[] PALAVRAS_PADRAO = {
        "teste", "areia", "carro", "porta", "mesa", "casa", "livro", "copo", "pena", "gato",
        "canto", "banco", "braco", "dado", "foco", "lago", "vento", "chuva", "sol", "lua",
        "porta", "janela", "parede", "teto", "chao", "portao", "cadeira", "sofa", "cama", "armario",
        "computador", "teclado", "mouse", "monitor", "impressora", "internet", "rede", "senha", "usuario", "arquivo",
        "programa", "sistema", "aplicativo", "software", "hardware", "memoria", "disco", "processador", "placa", "video",
        "musica", "som", "audio", "video", "filme", "serie", "livro", "revista", "jornal", "noticia",
        "alimento", "comida", "bebida", "agua", "suco", "cafe", "cha", "leite", "pao", "queijo",
        "fruta", "verdura", "legume", "carne", "peixe", "frango", "ovo", "arroz", "feijao", "macarrao",
        "escola", "universidade", "professor", "aluno", "aula", "curso", "disciplina", "prova", "exame", "nota",
        "trabalho", "emprego", "empresa", "negocio", "cliente", "fornecedor", "produto", "servico", "venda", "compra",
        "dinheiro", "banco", "conta", "cartao", "credito", "debito", "pagamento", "recebimento", "salario", "preco",
        "tempo", "clima", "temperatura", "calor", "frio", "vento", "chuva", "neve", "sol", "nuvem",
        "familia", "pai", "mae", "filho", "filha", "irmao", "irma", "avo", "neto", "primo",
        "amigo", "colega", "vizinho", "pessoa", "gente", "homem", "mulher", "crianca", "adulto", "idoso",
        "saude", "doenca", "medico", "hospital", "remedio", "vacina", "exame", "consulta", "tratamento", "cirurgia",
        "esporte", "jogo", "time", "equipe", "campeonato", "competicao", "vitoria", "derrota", "empate", "gol",
        "viagem", "passeio", "turismo", "hotel", "restaurante", "praia", "montanha", "campo", "cidade", "estrada"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        
        List<String> palavrasTreino = new ArrayList<>();
        
        // Verifica se foi passado um arquivo como argumento
        if (args.length > 0) {
            try {
                palavrasTreino = carregarPalavrasDoArquivo(args[0]);
                System.out.println("Carregadas " + palavrasTreino.size() + " palavras do arquivo: " + args[0]);
            } catch (IOException e) {
                System.out.println("Erro ao carregar arquivo: " + e.getMessage());
                System.out.println("Usando lista padrão de palavras.");
                palavrasTreino = new ArrayList<>(Arrays.asList(PALAVRAS_PADRAO));
            }
        } else {
            // Usa a lista padrão
            palavrasTreino = new ArrayList<>(Arrays.asList(PALAVRAS_PADRAO));
            System.out.println("Usando lista padrão com " + palavrasTreino.size() + " palavras.");
        }
        
        // Verifica se há palavras suficientes
        if (palavrasTreino.size() < 5) {
            System.out.println("Erro: É necessário pelo menos 5 palavras para treino.");
            System.out.println("Adicione mais palavras ao arquivo ou use a lista padrão.");
            return;
        }

        List<String> palavrasAtuais = new ArrayList<>();

        System.out.println("Iniciando treino de digitação. Pressione Ctrl+C para parar.");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        while (true) {
            // Gera 5 palavras aleatórias
            palavrasAtuais.clear();
            for (int i = 0; i < 5; i++) {
                int index = rand.nextInt(palavrasTreino.size());
                palavrasAtuais.add(palavrasTreino.get(index));
            }

            // Limpa a tela
            clearScreen();

            // Centraliza as palavras para digitar
            String palavrasParaDigitar = String.join(" ", palavrasAtuais);
            centralizarTexto(palavrasParaDigitar);
            System.out.println();
            
            // Centraliza o cursor para digitação
            centralizarCursor();
            
            // Armazenar as respostas do usuário
            String entrada = scanner.nextLine().trim().toLowerCase();
            String[] respostas = entrada.split("\\s+");

            // Verifica se o usuário digitou exatamente 5 palavras
            if (respostas.length != 5) {
                clearScreen();
                centralizarTexto("Por favor, digite exatamente 5 palavras separadas por espaço.");
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
                continue;
            }

            // Processa cada palavra e constrói a saída formatada
            StringBuilder resultadoLinha = new StringBuilder();
            
            for (int i = 0; i < 5; i++) {
                String original = palavrasAtuais.get(i);
                String digitada = respostas[i];
                
                if (i > 0) resultadoLinha.append(" ");
                
                // Se a palavra está correta, mostra normal, senão mostra com asteriscos
                if (original.equals(digitada)) {
                    resultadoLinha.append(original);
                } else {
                    int maxLen = Math.max(original.length(), digitada.length());
                    
                    for (int j = 0; j < maxLen; j++) {
                        char cOrig = j < original.length() ? original.charAt(j) : ' ';
                        char cDigit = j < digitada.length() ? digitada.charAt(j) : ' ';
                        
                        if (j < original.length() && j < digitada.length() && cOrig == cDigit) {
                            resultadoLinha.append(cDigit);
                        } else {
                            resultadoLinha.append('*');
                        }
                    }
                }
            }

            // Limpa a tela e mostra apenas o resultado centralizado
            clearScreen();
            centralizarTexto(resultadoLinha.toString());
            
            // Pequena pausa antes de continuar
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }

    // Método para centralizar texto
    private static void centralizarTexto(String texto) {
        try {
            // Obtém a largura do terminal (aproximada)
            int terminalWidth = 80; // valor padrão
            try {
                terminalWidth = Integer.parseInt(System.getenv("COLUMNS"));
            } catch (Exception e) {
                terminalWidth = 80;
            }
            
            int espacos = (terminalWidth - texto.length()) / 2;
            if (espacos < 0) espacos = 0;
            
            for (int i = 0; i < espacos; i++) {
                System.out.print(" ");
            }
            System.out.print(texto);
            
        } catch (Exception e) {
            System.out.println(texto);
        }
    }

    // Método para centralizar cursor
    private static void centralizarCursor() {
        try {
            int terminalWidth = 80;
            try {
                terminalWidth = Integer.parseInt(System.getenv("COLUMNS"));
            } catch (Exception e) {
                terminalWidth = 80;
            }
            
            int espacos = terminalWidth / 2;
            for (int i = 0; i < espacos; i++) {
                System.out.print(" ");
            }
        } catch (Exception e) {
            // Se falhar, não faz nada
        }
    }

    // Método para carregar palavras de um arquivo
    private static List<String> carregarPalavrasDoArquivo(String nomeArquivo) throws IOException {
        List<String> palavras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim().toLowerCase();
                if (!linha.isEmpty() && linha.matches("[a-z]+")) {
                    palavras.add(linha);
                }
            }
        }
        return palavras;
    }

    // Método para limpar a tela no Windows
    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Se falhar, apenas imprime várias linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}