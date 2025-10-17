package lambeer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Comparator;

public class Main {
    static ArrayList<Beer> lista = new ArrayList<>();
    static Map<String, Comparator<Beer>> comps = new HashMap<>();
    static LinkedList<String> lparams = new LinkedList<>();

    static {
        comps.put("name", Comparator.comparing(Beer::getName));
        comps.put("strength", Comparator.comparingDouble(Beer::getErosseg));
        comps.put("style", Comparator.comparing(Beer::getJelleg));
    }

    protected static void list(String[] cmd) {
        if (!lparams.isEmpty()) {
            lparams.clear();
        }
        for (int i = 1; i < cmd.length; i++) {
            if (comps.containsKey(cmd[i])) {
                lparams.add(cmd[i]);
            }
        }
        if (lparams.isEmpty()) {
            lparams.add("name");
        }
        if (lista.isEmpty()) {
            System.out.println("Üres a lista");
        }

        Comparator<Beer> comp = comps.get(lparams.getFirst());
        for (int i = 1; i < lparams.size(); i++) {
            comp = comp.thenComparing(comps.get(lparams.get(i)));
        }

        Collections.sort(lista, comp);
        for (Beer b : lista) {
            System.out.println(b);
        }
    }

    protected static void add(String[] cmd) {
        try {
            lista.add(new Beer(cmd[1], cmd[2], Double.valueOf(cmd[3])));
        } catch (Exception e) {
            System.out.println("useage: add name type strength");
        }
    }

    protected static void load(String[] cmd) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(cmd[1]));
            String line;
            while ((line = br.readLine()) != null) {
                String args[] = line.split(" ");
                lista.add(new Beer(args[0], args[1], Double.parseDouble(args[2])));

            }
            br.close();
        } catch (Exception e) {
            System.out.println("Usage: load filename");
        }

    }

    protected static void save(String[] cmd) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cmd[1]));
            Iterator<Beer> it = lista.iterator();
            while (it.hasNext()) {
                bw.append(it.next().toString() + "\n");
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Usage: save filename");
        }
    }

    protected static void search(String[] cmd) {
        try {
            for (Beer b : lista) {
                if (b.getName().equals(cmd[1])) {
                    System.out.println(b);

                }
            }
        } catch (Exception e) {
            System.out.println("usage search name");
        }
    }

    protected static void find(String[] cmd) {
        try {
            for (Beer b : lista) {
                if (b.getName().contains(cmd[1])) {
                    System.out.println(b);
                }
            }
        } catch (Exception e) {
            System.out.println("usage find string");
        }
    }

    protected static void delete(String[] cmd) {
        Iterator<Beer> it = lista.iterator();
        try {
            while (it.hasNext()) {
                Beer b = it.next();
                if (b.getName().equals(cmd[1])) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            System.out.println("usage: delete name");
        }
    }

    public static void main(String[] args) {
        /*
         * Beer b1 = new Beer("Soproni", "IPA");
         * Beer b2 = new Beer("Dreher", "Barna");
         * System.out.println(b1.toString() + " " + b2.toString());
         */
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        while (true) {
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
            if (cmd[0].equals("exit")) {
                return;
            }

            Map<String, Command> commands = new HashMap<>();
            commands.put("list", Main::list);
            commands.put("add", Main::add);
            commands.put("load", Main::load);
            commands.put("save", Main::save);
            commands.put("find", Main::find);
            commands.put("search", Main::search);
            commands.put("delete", Main::delete);
            Command c = commands.get(cmd[0]);
            if (c != null) {
                c.execute(cmd);
            } else {
                System.out.println("Please give a valid command");
            }
        }
    }
}
