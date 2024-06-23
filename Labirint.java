import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Labirint {
    public int rreshtat;
    public int kolonat;
    public static int[][] labirint;
    public int rreshtiFillimit, kolonaFillimit;
    public static int rreshtiDaljes, kolonaDaljes;
    Random rand = new Random();

    public Labirint() {
        rreshtat = KonstanteLoje.RRESHTA;
        kolonat = KonstanteLoje.KOLONA;

        labirint = new int[rreshtat][kolonat];

        for (int i = 0; i < rreshtat; i++) {
            for (int j = 0; j < kolonat; j++) {
                labirint[i][j] = 1;
            }
        }
    }

    public void gjeneroLabirint() {
        rreshtiFillimit = 2 * (rand.nextInt(rreshtat / 2) + 1) - 1;
        kolonaFillimit = 2 * (rand.nextInt(kolonat / 2) + 1) - 1;

        vizitoQelizen(rreshtiFillimit, kolonaFillimit);
    }

    public void vizitoQelizen(int rresht, int kolone) {
        labirint[rresht][kolone] = 0;

        int[][] drejtimet = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};

        perzgjidhDrejtimet(drejtimet);

        for (int[] drejtim : drejtimet) {
            int rreshtIri = rresht + drejtim[0];
            int kolonaEre = kolone + drejtim[1];

            if (eshteValid(rreshtIri, kolonaEre) && labirint[rreshtIri][kolonaEre] == 1) {
                labirint[(rresht + rreshtIri) / 2][(kolone + kolonaEre) / 2] = 0;
                rreshtiDaljes = rreshtIri;
                kolonaDaljes = kolonaEre;
                vizitoQelizen(rreshtIri, kolonaEre);
            }
        }
    }

    public void perzgjidhDrejtimet(int[][] drejtimet) {
        for (int i = drejtimet.length - 1; i > 0; i--) {
            int indexRastesishem = rand.nextInt(i + 1);
            int[] temp = drejtimet[i];
            drejtimet[i] = drejtimet[indexRastesishem];
            drejtimet[indexRastesishem] = temp;
        }
    }

    public boolean eshteValid(int rresht, int kolone) {
        return rresht >= 0 && rresht < rreshtat && kolone >= 0 && kolone < kolonat;
    }

    public void printoLabirintin(Graphics g, int xOffset, int yOffset) {
        for (int i = 0; i < rreshtat; i++) {
            for (int j = 0; j < kolonat; j++) {
                int x = xOffset + j * cellSize;
                int y = yOffset + i * cellSize;

                Image img = null;

                switch (labirint[i][j]) {
                    case 0:
                        img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\flokBore.png").getImage();
                        break;
                    case 1:
                        img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\pema.png").getImage();
                        break;
                    case 2:
                        img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\dhurata.png").getImage();
                        break;
                    case 3:
                        img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\NewDoor(1).png").getImage();
                        break;
                    case 4:
                        img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\NewDoor(1).png").getImage();
                        break;
                    case 9:
                        img = vendosDrejtimin();
                        break;
                }

                if (img != null) {
                    g.drawImage(img, x, y, cellSize, cellSize, null);
                }
            }
        }
    }

    public static int cellSize;

    public void gjeneroThesar(int numerThesaresh) {
        int i = 0;
        while (i < numerThesaresh) {
            int randRresht = rand.nextInt(this.rreshtat);
            int randKolone = rand.nextInt(this.kolonat);

            if (labirint[randRresht][randKolone] == 0) {
                labirint[randRresht][randKolone] = 2;
                i++;
            }
        }
    }

    public void caktoFillimDheDalje() {
        labirint[this.rreshtiFillimit][this.kolonaFillimit] = 3;
        labirint[rreshtiDaljes][kolonaDaljes] = 4;
    }

    public boolean eshteMur(int rresht, int kolone) {
        return labirint[rresht][kolone] == 1;
    }

    public boolean eshteDalje(int rresht, int kolone) {
        return labirint[rresht][kolone] == labirint[rreshtiDaljes][kolonaDaljes];
    }

    public boolean eshteThesar(int rresht, int kolone) {
        return eshteValid(rresht, kolone) && labirint[rresht][kolone] == 2;
    }

    public Image vendosDrejtimin() {
        Image img = null;
        switch (KontrolliLojes.drejtimi) {
            case "lart":
                img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\sprite_3.png").getImage();
                break;
            case "djathtas":
                img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\sprite_0 (2).png").getImage();
                break;
            case "poshte":
                img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\sprite_2.png").getImage();
                break;
            case "majtas":
                img = new ImageIcon("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\sprite_5.png").getImage();
                break;
    }
    return img;
}
}
