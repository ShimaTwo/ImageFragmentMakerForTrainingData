import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

class ImageFragmentation {

    // Constructor
    private ImageDisplayFrame imageDisplay;
    private JPanel imagePanel;
    private File[] fileList;
    private String myDataPath;
    private File mySourceFile;
    private String myDestPaths[];
    private File myDestFiles[];

    ImageDisplayPanel imageLabel;
    ImageFilePathPanel pathLabel;
    ImageFileCountLabel fileCount;

    int iterateNumber = 0;
    int fragmentIterateNumber = 0;
    ArrayList<BufferedImage> imageFragments;
    boolean ready = false;
    Random rndSeed = new Random(1234);

    int totalImage = 0;
    int totalFragmentationImage = 0;
    ImageFragmentation(String dataSourcePath, ArrayList<String> destPaths) {
        // データソースパスと保存先パスを格納
        myDataPath = dataSourcePath;
        mySourceFile = new File(myDataPath);
        myDestPaths = new String[destPaths.size()];
        myDestFiles = new File[destPaths.size()];
        for (int i = 0; i < myDestPaths.length; i++) {
            myDestPaths[i] = destPaths.get(i);
            myDestFiles[i] = new File(myDestPaths[i]);
        }
    }

    public boolean dataPathCheck(JFrame mainFrame) {
        // データソースパスのチェック
        if (myDataPath.equals("")) {
            JLabel pathEmpty = new JLabel("データソースパスが空です。");
            JOptionPane.showMessageDialog(mainFrame, pathEmpty);
            return false;
        }
        if (!mySourceFile.exists()) {
            JLabel fileNotExist = new JLabel("データソースディレクトリが見つかりません。");
            JOptionPane.showMessageDialog(mainFrame, fileNotExist);
            return false;
        }

        // データ保存先のチェック
        for (int i = 0; i < myDestPaths.length; i++) {
            if (myDestPaths[i].equals("")) {
                JLabel pathEmpty = new JLabel("データ保存先パスが空です。");
                JOptionPane.showMessageDialog(mainFrame, pathEmpty);
                return false;
            }
            if (!myDestFiles[i].exists()) {
                JLabel fileNotExist = new JLabel("データ保存先ディレクトリが見つかりません。");
                JOptionPane.showMessageDialog(mainFrame, fileNotExist);
                return false;
            }
        }
        return true;
    }

    public void start() {
        fileList = mySourceFile.listFiles();
        imageLabel = new ImageDisplayPanel();
        pathLabel = new ImageFilePathPanel();
        fileCount = new ImageFileCountLabel(fileList.length);
        fragmentNextImage();
        displayNextFragImage();
        imageDisplay = new ImageDisplayFrame((JPanel)imageLabel, (JPanel)pathLabel, (JPanel)fileCount);
        imageDisplay.setImageFragmentation(this);
        imageDisplay.setDestFileNumber(myDestFiles.length);
    }

    private void fragmentNextImage() {
        if (!(iterateNumber < fileList.length)) {
            // 終了処理
            JOptionPane.showMessageDialog(imageDisplay, totalImage +"枚の画像ファイルから"+ totalFragmentationImage +"枚の断片画像を生成/分類しました。");
            System.exit(0);
        } else  {
            // カウント、パス表示
            fileCount.setFileCount(iterateNumber+1);
            fileCount.revalidate();
            pathLabel.setFilePathLabel(fileList[iterateNumber].getAbsolutePath());
            pathLabel.revalidate();
            // ファイルが存在するか
            if (fileList[iterateNumber] != null && fileList[iterateNumber].isFile()) {
                BufferedImage bi = null;
                try {
                    bi = ImageIO.read(fileList[iterateNumber]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bi != null) {
                    // 画像トリミング
                    imageFragments = ImageTrimming(bi);
                    totalImage++;
                } else {
                    // 画像じゃない
                }
            } else {
                // ファイルじゃない
            }
            iterateNumber++;
        }
    }

    public void displayNextFragImage() {
        if (imageFragments == null || fragmentIterateNumber == imageFragments.size()) {
            fragmentNextImage();
            fragmentIterateNumber = 0;
            displayNextFragImage();
            return;
        }
        Image oneFragment = imageFragments.get(fragmentIterateNumber).getScaledInstance(300, 300, Image.SCALE_DEFAULT);                        
        ImageIcon iconFragment = new ImageIcon(oneFragment);
        imageLabel.setImageIcon(iconFragment);
        imageLabel.revalidate();
        fragmentIterateNumber++;
    }

    public void classificationImage(int destNumber) {
        // 今表示されている画像を分類、保存
        Image iDisplayed = imageLabel.getDisplayedImage().getImage();
        BufferedImage bDisplayed = changeImage2BufferedImage(iDisplayed);
        String path = pathLabel.getPathString();
        String fileName = getFileNameFromFullPath(path);
        String saveFileName = fileName+"_"+fragmentIterateNumber+".png";
        String saveFilePath = myDestFiles[destNumber] +"/"+ saveFileName;
        try {
            ImageIO.write(bDisplayed, "png", new File(saveFilePath));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(imageDisplay, saveFilePath+"の保存に失敗しました。");
        }
        // 次の画像を表示
        sleepNmilli(100);
        displayNextFragImage();
        totalFragmentationImage++;
    }

    private BufferedImage changeImage2BufferedImage(Image image) {
        BufferedImage ret = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics g = ret.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return ret;
    }

    private String getFileNameFromFullPath(String fullPath) {
        File file = new File(fullPath);
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex!=-1) {
            return fileName.substring(0, dotIndex);
        } else {
            JOptionPane.showMessageDialog(imageDisplay, "ファイル名なしの画像ファイルがありました。プログラムを終了します。");
            System.exit(0);
        }
        return "";
    }

    private ArrayList<BufferedImage> ImageTrimming(BufferedImage image) {
        ArrayList<BufferedImage> retArray = new ArrayList<BufferedImage>();
        int width = image.getWidth();
        int height = image.getHeight();
        int sumTrimmingArea = 0;
        int trimmingMaxSideLength = Math.min(width, height)*2/3;
        int trimmingMinSideLength = trimmingMaxSideLength / 10;
        // 総トリミング面積が画像面積の二倍を越えるまでループ
        BufferedImage oneFragment = null;
        do {
            int trimmingSideLength = rndSeed.nextInt(trimmingMaxSideLength-trimmingMinSideLength)+trimmingMinSideLength;
            int xCoor = rndSeed.nextInt(width-trimmingSideLength);
            int yCoor = rndSeed.nextInt(height-trimmingSideLength);
            oneFragment = image.getSubimage(xCoor, yCoor, trimmingSideLength, trimmingSideLength);
            retArray.add(oneFragment);
            sumTrimmingArea = sumTrimmingArea + trimmingSideLength*trimmingSideLength;
        } while (sumTrimmingArea < 2*width*height);
        return retArray;
    }

    private void sleepNmilli (int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}