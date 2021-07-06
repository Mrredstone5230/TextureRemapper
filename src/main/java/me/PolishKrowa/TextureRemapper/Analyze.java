package me.PolishKrowa.TextureRemapper;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analyze {

    public static String basepath;
    public static int amount = 0;
    public static List<String> buffer = new ArrayList<>();

    //1. Original folder location

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        path = path.replace("\\ ", " ");
        path = path.trim();
        path = path + File.separator;
        basepath = path;

        analyse("");
        System.out.println(amount);
        for (String s : buffer) {
            System.out.println(s);
        }
    }

    private static void analyse(String path) {
        File folder;
        if (!path.equals(""))
            folder = new File(basepath + path + File.separator);
        else
            folder = new File(basepath);

        if (folder == null || !folder.exists())
            System.exit(1);
        if (!folder.isDirectory())
            System.exit(2);

        FilenameFilter fileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".png");
            }
        };
        FilenameFilter folderFilter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                File folder;
                folder = new File(f + File.separator + name);

                return folder.exists() && folder.isDirectory();
            }
        };

        for (String s : folder.list(fileFilter)) {
            amount++;
            if (path.equals(""))
                buffer.add(s);
            else
                buffer.add(path.substring(1) + File.separator + s);
        }

        for (String s : folder.list(folderFilter)) {
            analyse(path + File.separator + s);
        }
    }
}
