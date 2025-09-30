package lab3;

import java.io.*;
import java.util.LinkedList;

public class Shell {
    private boolean done = false;
    private static File wd = new File(System.getProperty("user.dir"));

    public void run() throws IOException {
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        while (!done) {
            System.out.print("> ");
            String line = new String();
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            String[] cmd = line.split(" ");
            if (cmd[0].equals("hello")) {
                System.out.println("Hello world!");
            }
            if (cmd[0].equals("exit")) {
                System.exit(0);
            }

            if (cmd[0].equals("pwd")) {
                pwd();
            }
            if (cmd[0].equals("ls")) {
                ls(wd, "", cmd);
            }
            if (cmd[0].equals("cd")) {
                cd(cmd);
            }
            if (cmd[0].equals("rm")) {
                rm(cmd);
            }
            if (cmd[0].equals("mkdir")) {
                mkdir(cmd);
            }
            if (cmd[0].equals("cp")) {
                cp(cmd);
            }
            if (cmd[0].equals("head")) {
                head(cmd);
            }
            if (cmd[0].equals("mv")) {
                cp(cmd);
                rm(cmd);
            }
            if (cmd[0].equals("cat")) {
                cat(cmd);
            }
            if (cmd[0].equals("length")) {
                length(cmd);
            }
            if (cmd[0].equals("tail")) {
                tail(cmd);
            }
            if (cmd[0].equals("wc")) {
                wc(cmd);
            }
            if (cmd[0].equals("grep")) {
                grep(cmd);
            }
        }
    }

    protected void pwd() {
        File dir = wd;
        File[] list = dir.listFiles();
        int size = list.length;
        System.out.println(dir + " Files in this directory: " + size);
    }

    protected void ls(File f, String tab, String[] cmd) {
        {
            File[] list = f.listFiles();
            for (int i = 0; i < list.length; i++) {
                System.out.print(tab + list[i].getName());
                if (cmd.length > 1 && cmd[1].equals("-l")) {
                    String type = list[i].isDirectory() ? " d" : "f";
                    double size = list[i].length();
                    System.out.println(" type: " + type + " size:" + size);
                } else {
                    System.out.print("\n");
                }
                /*
                 * if (list[i].isDirectory()) {
                 * ls(list[i], tab + " ", cmd);
                 * }
                 */

            }
        }
    }

    protected void cd(String[] args) {
        if (args.length < 2) {
            System.out.println("No path given");
            return;
        }
        if (!args[1].equals("..")) {
            File newdir = new File(wd, args[1]);

            if (newdir.exists() && newdir.isDirectory()) {
                wd = newdir;
            } else {
                System.out.println("Invalid directory name!");
            }
        } else {
            File parent = wd.getParentFile();
            if (parent != null) {
                wd = parent;
            } else {
                System.out.println("Already at root directory!");
            }
        }
    }

    protected void rm(String[] args) {
        try {
            File f = new File(wd, args[1]);
            f.delete();
        } catch (SecurityException ex) {
            System.out.println(ex);
        }
    }

    protected void mkdir(String[] args) {
        File f = new File(wd, args[1]);
        if (f.exists() && f.isDirectory()) {
            System.out.println("This directory already exists!");
        } else {
            f.mkdir();
        }
    }

    protected void cp(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: cp source destination");
            return;
        }
        File source = new File(wd, args[1]);
        File dest = new File(wd, args[2]);
        if (!source.exists() || !source.isFile()) {
            System.out.println("Error: Source file does not exist or is not a file!");
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    protected void head(String[] args) {
        int n = 10;
        String filename;

        if (args.length < 2) {
            System.out.println("Usage: head -n lines file");
            return;
        }

        int fileArgIndex = 1;

        if (args[1].equals("-n")) {
            if (args.length < 4) {
                System.out.println("Usage: head -n lines file");
                return;
            }
            try {
                n = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("The second arg is not an int: " + args[2]);
                return;
            }
            fileArgIndex = 3;
        }

        filename = args[fileArgIndex];
        File file = new File(wd, filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist or is not a file!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < n) {
                System.out.println(line);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }}

    protected void cat(String[] args) {
        String filename;

        if (args.length < 2) {
            System.out.println("Useage cat filename");
            return;
        }

        filename = args[1];
        File file = new File(wd, filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist or is not a file!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    protected void length(String[] args) {
        File source = new File(wd, args[1]);
        if (!source.exists() || !source.isFile()) {
            System.out.println("Error: File does not exist or is not a file!");
            return;
        }
        double size = source.length();
        System.out.println("size:" + size);
    }

    protected void tail(String[] args) {
        int n = 10;
        String filename;

        if (args.length < 2) {
            System.out.println("Usage: tail -n lines file");
            return;
        }

        int fileArgIndex = 1;

        if (args[1].equals("-n")) {
            if (args.length < 4) {
                System.out.println("Usage: tail -n lines file");
                return;
            }
            try {
                n = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("The second arg is not an int: " + args[2]);
                return;
            }
            fileArgIndex = 3;
        }

        filename = args[fileArgIndex];
        File file = new File(wd, filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist or is not a file!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> list=new LinkedList<>();
            while ((line = br.readLine()) != null) {
                list.add(line);
                if (list.size()>n) {
                    list.removeFirst();
                }
            }
            for(String item : list){
                System.out.println(item);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }}

    protected void wc(String[] args){
        String filename;
        int lc=0;
        int wc=0;
        int bc=0;
        if (args.length < 2) {
            System.out.println("Useage wc filename");
            return;
        }

        filename = args[1];
        File file = new File(wd, filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist or is not a file!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lc++;
                String[] l = line.split(" ");
                wc+=l.length;
                bc+=line.length();
            }
            System.out.println("line count:"+lc+", word count:"+wc+ ", letter count:"+bc);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    protected void grep(String[] args) {
        String filename;

        if (args.length < 3) {
            System.out.println("Useage grep pattern filename");
            return;
        }
        String pattern= ".*"+args[1]+"*.";
        filename = args[2];
        File file = new File(wd, filename);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Error: File does not exist or is not a file!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.matches(pattern)){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

}