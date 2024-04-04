import java.io.*;
import java.net.*;
public class Server {
    public static final int PORT = 2525;

    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Servidor foi iniciado na porta " + PORT);

            while (true){
                Socket socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                long tamanhoArquivo = inputStream.readLong();
                byte[] buffer = new byte[4096];

                String localSalvamento = "salvos/" + System.nanoTime() + ".png";
                FileOutputStream fileOutputStream = new FileOutputStream(localSalvamento);

                int byteRead;

                while (tamanhoArquivo > 0 && (byteRead = inputStream.read(buffer, 0, (int)Math.min(buffer.length, tamanhoArquivo))) != -1){
                    fileOutputStream.write(buffer, 0, byteRead);
                    tamanhoArquivo -= byteRead;
                }

                fileOutputStream.close();

                outputStream.writeUTF(localSalvamento);
                inputStream.close();
                outputStream.close();
                socket.close();

                System.out.println("Imagem recebida com sucesso e salva em: " + localSalvamento);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
