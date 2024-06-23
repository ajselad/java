import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;

public class KontrolliLojes {
    public Labirint labirint = new Labirint();
    public Lojtar lojtar;
    public String status;
    private final JFrame mainFrame;
    public static String drejtimi = "poshte";

    public KontrolliLojes(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void playSound(String soundFilePath, float volume) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Adjust volume using FloatControl
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // Calculate the volume in decibels
                float dB = (float) (Math.log10(volume) * 20);
                gainControl.setValue(dB);
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void filloLojen() {
        this.status = "vazhdim";
        playSound("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\carol-of-the-bells-xmas-background-hip-hop-music-for-video-60-second-178242.wav", 0.25f);
        this.labirint.gjeneroLabirint();
        this.labirint.caktoFillimDheDalje();
        this.labirint.gjeneroThesar(KonstanteLoje.THESARE_TOTALE);
        this.lojtar = new Lojtar(this.labirint.rreshtiFillimit, this.labirint.kolonaFillimit);
    }

    public void filloLojen(String path) {
        this.status = "vazhdim";
        playSound("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\carol-of-the-bells-xmas-background-hip-hop-music-for-video-60-second-178242.wav", 0.25f);
        this.lojtar = new Lojtar(this.labirint.rreshtiFillimit, this.labirint.kolonaFillimit);
        this.ngarkoLojen(path);
    }


    public boolean gjendjaThesarit() {
        return (this.lojtar.thesareTeMbledhura == KonstanteLoje.THESARE_TOTALE);
    }


    public void luajRradhen(String s) {
        int tempRresht = this.lojtar.pozicioniRresht;
        int tempKolone = this.lojtar.pozicioniKolone;

        switch (s) {
            case "lart":
                tempRresht = this.lojtar.pozicioniRresht - 1;
                drejtimi = s;
                break;
            case "djathtas":
                tempKolone = this.lojtar.pozicioniKolone + 1;
                drejtimi = s;
                break;
            case "poshte":
                tempRresht = this.lojtar.pozicioniRresht + 1;
                drejtimi = s;
                break;
            case "majtas":
                tempKolone = this.lojtar.pozicioniKolone - 1;
                drejtimi = s;
                break;
        }

        if (this.labirint.eshteMur(tempRresht, tempKolone)) {
            this.status = "humbur";
            this.perfundoLojen();
            //JOptionPane.showMessageDialog(null, "Levizje e gabuar", "Gabim", JOptionPane.INFORMATION_MESSAGE);


        } else if (this.labirint.eshteThesar(tempRresht, tempKolone)) {
            this.lojtar.mblidhThesar();
            this.lojtar.leviz(tempRresht, tempKolone);
        } else if (this.labirint.eshteDalje(tempRresht, tempKolone)) {
            if (this.gjendjaThesarit()) {
                status = "fituar";
                this.perfundoLojen();
            } else {
                status = "humbur";
                this.perfundoLojen();
            }
        } else {
            this.lojtar.leviz(tempRresht, tempKolone);
        }
    }

    public void perfundoLojen() {
        if (this.status.equals("fituar")) {
            JOptionPane.showMessageDialog(null, "You win!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        } else if (this.status.equals("humbur")) {
            playSound("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\mixkit-cartoon-whistle-game-over-606.wav", 1.0f);
            JOptionPane.showMessageDialog(null, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        mainFrame.dispose();
        System.exit(0);
    }

    public void ruajLojen() {
        File file = new File("save.dat");

        try {
            if (!file.exists()) {
                if (file.createNewFile())
                    System.out.println("Loja u ruajt: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Kishte nje problem ne ruajtjen e lojes");
        }

        byte[] data = new byte[(KonstanteLoje.RRESHTA * KonstanteLoje.KOLONA) + 9];
        int k = 0;
        for (int i = 0 ; i < KonstanteLoje.RRESHTA ; i++) {
            for (int j = 0 ; j < KonstanteLoje.KOLONA ; j++) {
                data[k] = (byte) Labirint.labirint[i][j];
                k++;
            }
        }
        data[k] = (byte) this.lojtar.pozicioniRresht;
        data[k + 1] = (byte) this.lojtar.pozicioniKolone;
        data[k + 2] = (byte) this.lojtar.piket;
        data[k + 3] = (byte) this.lojtar.thesareTeMbledhura;
        data[k + 4] = (byte) KonstanteLoje.RRESHTA;
        data[k + 5] = (byte) KonstanteLoje.KOLONA;
        data[k + 6] = (byte) KonstanteLoje.THESARE_TOTALE;
        data[k + 7] = (byte) Labirint.rreshtiDaljes;
        data[k + 8] = (byte) Labirint.kolonaDaljes;

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data);
            bos.close();
        } catch (IOException e) {
            System.err.println("Dicka shkoi gabim");
        }

    }

    public void ngarkoLojen(String filename) {
        File file = new File(filename);

        byte[] data = new byte[(KonstanteLoje.RRESHTA * KonstanteLoje.KOLONA) + 9];

        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(data);

            int k = 0;
            for (int i = 0 ; i < KonstanteLoje.RRESHTA ; i++) {
                for (int j = 0 ; j < KonstanteLoje.KOLONA ; j++) {
                    Labirint.labirint[i][j] = data[k];
                    k++;
                }
            }

            this.lojtar.pozicioniRresht = data[k];
            this.lojtar.pozicioniKolone = data[k + 1];
            this.lojtar.piket = data[k + 2];
            this.lojtar.thesareTeMbledhura = data[k + 3];
            KonstanteLoje.RRESHTA = data[k + 4];
            KonstanteLoje.KOLONA = data[k + 5];
            KonstanteLoje.THESARE_TOTALE = data[k + 6];
            Labirint.rreshtiDaljes = data[k + 7];
            Labirint.kolonaDaljes = data[k + 8];

            bis.close();

        } catch (FileNotFoundException e) {
            System.err.println("Dicka shkoi gabim");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}