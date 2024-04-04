import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER = "localhost";
    private static final int PORT = 2525;

    public static void enviarImagem(String caminhoImagem) throws IOException {

        try(Socket socket = new Socket(SERVER, PORT)){
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            FileInputStream fileInputStream = new FileInputStream(caminhoImagem);

            File file = new File(caminhoImagem);
            byte[] buffer = new byte[4096];

            outputStream.writeLong(file.length());

            int byteRead;

            while ((byteRead = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, byteRead);
            }

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String caminhoRecebido = inputStream.readUTF();

            System.out.println("O caminho da imagem salva no servidor foi esse: " + caminhoRecebido);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        enviarImagem("C://Users//Cliente//Pictures//Imagem do WhatsApp de 2024-04-02 à(s) 19.30.45_50f5ea8e.jpg");
    }
}
