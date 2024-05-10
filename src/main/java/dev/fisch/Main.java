package dev.fisch;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.net.URL;
import java.util.Scanner;

public class Main {

    private static JTextArea textArea;
    public static String getUsername() {
        String username = System.getenv("USERNAME");
        return (username != null) ? username : "";
    }

    public static void downloadFile(String url, String downloadDir) {
        File dir = new File(downloadDir);
        if (!dir.exists()) {
            System.err.println("The mods directory does not exist. Aborting download.");
            return;
        }

        String[] parts = url.split("/");
        String fileName = parts[parts.length - 1];
        String filePath = downloadDir + File.separator + fileName;

        try {
            URL website = new URL(url);
            try (InputStream in = website.openStream()) {
                Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Downloaded: Yes");

                System.out.println("U can close it now");
            }
        } catch (IOException e) {
            System.err.println("Failed to download: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String url = "https://breeze.rip/dashboard/breeze-forge.jar";
        String pcName = getUsername();
        String downloadDir = "C:\\Users\\" + pcName + "\\AppData\\Roaming\\.minecraft\\mods";
        String forge = "C:\\Users\\" + pcName + "\\AppData\\Roaming\\.minecraft\\config\\forge.cfg";

        if(Files.exists(Paths.get(forge))){
            System.out.println("Forge Installed: Yes");
        }else{
            System.err.println("Forge isnt installed");
            Scanner sc = new Scanner(System.in);
            System.out.println("U wanna install Forge? (yes/no)");
            String choice = sc.next();
            choice.toLowerCase();

            if(choice == "yes"){
                installForge();
            }else{
                System.out.println("Then Please install Forge by ur self");
            }
        }

        if (Files.exists(Paths.get(downloadDir))) {
            downloadFile(url, downloadDir);
        } else {
            System.err.println("The mods directory does not exist. Aborting download.");
        }

    }

    private static void installForge(){
        try {
            String jarFilePath = Main.class.getResource("/forge.jar").getFile();
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFilePath);
            processBuilder.directory(new File(jarFilePath).getParentFile());
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
