import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Struct;
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
    // フレーム・パネル
    private ImageDisplayFrame imageDisplay;
    ImageDisplayPanel imageLabelPanel;
    ImageFilePathPanel pathLabelPanel;
    ImageFileCountLabel fileCountPanel;

    // ソースファイル・保存先ファイル
    private File mySourceFile;    
    private File myDestFiles[];

    // ソースファイルのファイルリスト
    private File sourceFileList[];

    // 画像情報の構造体配列
    private ArrayList<ImageInfo> allImageInfo;
    // 処理中のファイルインデックス
    private int fileIndex;

    // 断片画像の保存履歴
    private ArrayList<FragImageSaveInfo> fragImagesSaveHistoryStack;
    // 断片画像の通し番号
    private int imageAllIndex;
    // 処理中の画像インデックス
    private int imageLocalIndex;

    // 素画像1つから生成される断片画像群保存用
    ArrayList<BufferedImage> imageFragments;

    // 乱数シード
    Random rndSeed = new Random(1234);

    int totalImage = 0;
    int totalFragmentationImage = 0;
    ImageFragmentation(String dataSourcePath, ArrayList<String> destPaths) {
        // データソースパスと保存先パスを格納
        mySourceFile = new File(dataSourcePath);
        myDestFiles = new File[destPaths.size()];
        for (int i = 0; i < myDestFiles.length; i++) {
            myDestFiles[i] = new File(destPaths.get(i));
        }
        // ソースファイルリストを初期化
        sourceFileList = mySourceFile.listFiles();
        // 画像情報の構造体配列を初期化
        allImageInfo = new ArrayList<>();
        // ファイルインデックスを初期化
        fileIndex = 0;
        // 保存履歴スタックの初期化
        fragImagesSaveHistoryStack = new ArrayList<>();
        // 画像インデックスを初期化
        imageAllIndex = 0;
        imageLocalIndex = 0;
    }

    // パスチェック
    public boolean dataPathCheck(JFrame mainFrame) {
        // データソースパスのチェック
        if ((mySourceFile.getPath()).equals("")) {
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
        for (int i = 0; i < myDestFiles.length; i++) {
            if ((myDestFiles[i].getPath()).equals("")) {
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
        // パネル生成
        imageLabelPanel = new ImageDisplayPanel();
        pathLabelPanel = new ImageFilePathPanel();
        fileCountPanel = new ImageFileCountLabel(sourceFileList.length);

        // 画像情報生成
        makeAllImageInfo();
        // ナルでなくなるまで断片画像を生成
        while (true) {
            imageFragments = fragmentNextImage();
            if (imageFragments != null || fileIndex == sourceFileList.length) break;
            fileIndex++;
        }

        // 画像情報をもとにパネルに表示
        displayNextFragImage();

        // フレームを生成
        imageDisplay = new ImageDisplayFrame((JPanel)imageLabelPanel, (JPanel)pathLabelPanel, (JPanel)fileCountPanel);
        imageDisplay.setImageFragmentation(this);
        imageDisplay.setDestFileNumber(myDestFiles.length);
    }

    // ソースファイルのファイルリストから画像生成に必要情報をスタック
    private void makeAllImageInfo() {
        for (int i = 0; i < sourceFileList.length; i++) {
            String filePath = sourceFileList[i].getPath();
            int seed = rndSeed.nextInt();
            ImageInfo tmpInfo = new ImageInfo(filePath, seed);
            allImageInfo.add(tmpInfo);
        }
    }

    // パネルに画像情報を表示させる
    private void displayNextFragImage() {
        // パネル表示情報
        ImageIcon icon = null;
        int fileNumber = 0;
        String filePath = null;
        // ファイルリストを全て処理したか(fileIndexは断片画像を生成する直前でインクリメントしているので、全部処理したかのチェック時は+1する)
        /*
        if (sourceFileList.length <= fileIndex) {
            // 終了処理
            JOptionPane.showMessageDialog(imageDisplay, fileIndex +"枚の画像ファイルから"+ imageAllIndex +"枚の断片画像を生成/分類しました。");
            System.exit(0);
        }*/
        // 表示できる断片画像があるかチェック
        if (imageFragments.size() > imageLocalIndex && imageLocalIndex >= 0) {
            // あればパネル表示のための情報を生成
            Image oneFragmentImage = imageFragments.get(imageLocalIndex).getScaledInstance(300, 300, Image.SCALE_DEFAULT);
            icon = new ImageIcon(oneFragmentImage);
            fileNumber = fileIndex;
            filePath = sourceFileList[fileIndex].getPath();
        } else {
            // なければ断片画像を生成、補充してもう一度自身を呼び出す
            if (imageFragments.size() == imageLocalIndex) {
                // 進めていった結果断片画像のストックがなくなった場合
                // ナルチェックをしつつ次のファイルの断片画像を生成する
                do {
                    fileIndex++;
                    if (sourceFileList.length <= fileIndex) {
                        // 終了処理
                        JOptionPane.showMessageDialog(imageDisplay, fileIndex +"枚の画像ファイルから"+ imageAllIndex +"枚の断片画像を生成/分類しました。");
                        System.exit(0);
                    }
                    imageFragments = fragmentNextImage();
                } while (imageFragments==null);
                imageLocalIndex = 0;
            } else {
                // 戻った結果ローカルナンバーがマイナスになった場合
                // ナルチェックをしつつ前の画像の断片画像を生成する
                do {
                    fileIndex--;
                    imageFragments = fragmentNextImage();
                    if (fileIndex == 0) {
                        // 最初からやり直し
                        start();
                    }
                } while (imageFragments==null);
                imageLocalIndex = imageFragments.size()-1;
            }
            displayNextFragImage();
            return;
        }
        // パネルに反映
        imageLabelPanel.setImageIcon(icon);
        imageLabelPanel.revalidate();
        fileCountPanel.setFileCount(fileNumber+1);
        fileCountPanel.revalidate();
        pathLabelPanel.setFilePathLabel(filePath);
        pathLabelPanel.revalidate();
    }

    // 1ファイルから画像を読み込んでトリミング画像群を返す
    private ArrayList<BufferedImage> fragmentNextImage() {
        ArrayList<BufferedImage> imageFragments = null;
        File file = new File(allImageInfo.get(fileIndex).originalFilePath);
        // ファイルが存在するか
        if (file != null && file.isFile()) {
            // 画像読み込み
            BufferedImage bi = null;
            try {
                bi = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bi != null) {
                // 画像トリミング
                imageFragments = ImageTrimming(bi);
                totalImage++;
            } else {
                // 画像じゃない
                System.out.println("Not a Image !!");
            }
        } else {
            // ファイルじゃない
            System.out.println("Not a File !!");
        }
        return imageFragments;
    }

    // 画像をトリミングする
    private ArrayList<BufferedImage> ImageTrimming(BufferedImage image) {
        ArrayList<BufferedImage> retArray = new ArrayList<BufferedImage>();
        // 画像の縦横
        int width = image.getWidth();
        int height = image.getHeight();
        // トリミングした総面積
        int totalTrimmingArea = 0;
        // トリミングサイズの最大最小を設定
        int trimmingMaxSideLength = Math.min(width, height) / 2;
        int trimmingMinSideLength = trimmingMaxSideLength / 8;
        // 総トリミング面積が画像面積を越えるまでループ
        Random localRnd = new Random(allImageInfo.get(fileIndex).randomSeed);
        BufferedImage oneFragment = null;
        do {
            int trimmingSideLength = localRnd.nextInt(trimmingMaxSideLength-trimmingMinSideLength)+trimmingMinSideLength;
            int xCoor = localRnd.nextInt(width-trimmingSideLength);
            int yCoor = localRnd.nextInt(height-trimmingSideLength);
            oneFragment = image.getSubimage(xCoor, yCoor, trimmingSideLength, trimmingSideLength);
            retArray.add(oneFragment);
            totalTrimmingArea = totalTrimmingArea + trimmingSideLength*trimmingSideLength;
        } while (totalTrimmingArea < width*height);
        return retArray;
    }

    // フルパスから末尾の拡張子なしのファイルネームだけを取得する
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
    
    // 分類を行ってimageIndexを加算
    public void classificationImage(int destNumber) {   
        // パネルに表示されている画像を取得してBufferedImage型に変換
        Image iDisplayed = imageLabelPanel.getDisplayedImage().getImage();
        BufferedImage bDisplayed = changeImage2BufferedImage(iDisplayed);
        String originalFilePath = allImageInfo.get(fileIndex).originalFilePath;
        String originalFileName = getFileNameFromFullPath(originalFilePath);
        String saveFilePath = myDestFiles[destNumber].getPath() + "/" + originalFileName + "_"+ imageLocalIndex +".png";
        try {
            ImageIO.write(bDisplayed, "png", new File(saveFilePath));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(imageDisplay, saveFilePath+"の保存に失敗しました。");
        }
        // 画像情報の保存先情報を更新
        FragImageSaveInfo info = new FragImageSaveInfo(saveFilePath, imageAllIndex);
        fragImagesSaveHistoryStack.add(info);
        // imageIndexを加算して次の画像を表示
        // sleepNmilli(100);
        imageLocalIndex++;
        imageAllIndex++;
        displayNextFragImage();
    }

    public void undo() {
        // 画像情報インデックスを一つ戻す
        if (imageAllIndex > 0) {
            imageAllIndex--;
            imageLocalIndex--;
        } else {
            return;
        }

        // 保存した画像を削除する
        File deleteFile = new File(fragImagesSaveHistoryStack.get(fragImagesSaveHistoryStack.size()-1).savePath);
        deleteFile.delete();
        // 保存スタックの末尾を削除する
        fragImagesSaveHistoryStack.remove(fragImagesSaveHistoryStack.size()-1);
        // パネルに表示
        displayNextFragImage();
    }

    private BufferedImage changeImage2BufferedImage(Image image) {
        BufferedImage ret = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics g = ret.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return ret;
    }

    private void sleepNmilli (int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 断片画像保存用の構造体
    private class FragImageSaveInfo {
        String savePath;
        int saveNumber;
        FragImageSaveInfo (String path, int num) {
            this.savePath = path;
            this.saveNumber = num;
        }
    }

    // 素画像から断片画像を生成するための情報の構造体
    private class ImageInfo {
        String        originalFilePath;
        int           randomSeed;
        ImageInfo(String originalPath, int seed) {
            this.originalFilePath = originalPath;
            this.randomSeed = seed;
        }
    }
}