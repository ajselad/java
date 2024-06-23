import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Lojtar {
    public int pozicioniRresht;
    public int pozicioniKolone;
    public int thesareTeMbledhura;
    public int piket;

    public Lojtar(int pozicioniRresht, int pozicioniKolone) {
        this.pozicioniRresht = pozicioniRresht;
        this.pozicioniKolone = pozicioniKolone;
        Labirint.labirint[pozicioniRresht][pozicioniKolone] = 9;
    }

    public void leviz(int rreshtIRi, int koloneERe) {
        Labirint.labirint[this.pozicioniRresht][this.pozicioniKolone] = 0;
        Labirint.labirint[rreshtIRi][koloneERe] = 9;
        this.pozicioniRresht = rreshtIRi;
        this.pozicioniKolone = koloneERe;
    }

    public void mblidhThesar() {
        this.thesareTeMbledhura++;
        this.piket += 10;
        Labirint.labirint[pozicioniRresht][pozicioniKolone] = 0;
        playSound("C:\\Users\\Kejdi\\IdeaProjects\\SantaMaze\\src\\figura\\collectcoin-6075.wav");

    }

    public boolean eshteNeDalje() {
        return (this.pozicioniRresht == Labirint.rreshtiDaljes && this.pozicioniKolone == Labirint.kolonaDaljes);
    }

    private void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
