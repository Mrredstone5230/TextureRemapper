package me.PolishKrowa.TextureRemapper;

import java.io.IOException;
import java.util.Properties;

public class EntryPoint {

    public static void main(String[] args) throws IOException {
        if (args.length == 0 || args.length != 1) {
            final Properties properties = new Properties();
            properties.load(EntryPoint.class.getClassLoader().getResourceAsStream("project.properties"));

            System.out.println("You need to put only one argument. Either analyze or apply");
            System.out.println("If you never used this tool before, please read README.md on the github page. Current jar version: " + properties.getProperty("version"));
            System.exit(1);
        }

        if (args[0].equals("analyze")) {
            System.out.println("Inputs (In order): \n1. Original folder location");
            Analyze.main(null);
        } else if (args[0].equals("apply")) {
            System.out.println("Inputs (In order): \n1. Images Folder\n2. Output Folder\n3 and more... Output data from analyze");
            Apply.main(null);
        } else {
            System.out.println("You need to put only one argument. Either analyze or apply");
            System.exit(1);
        }
    }
}
