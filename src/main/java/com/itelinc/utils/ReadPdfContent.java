package com.itelinc.utils;

import io.github.jonathanlink.PDFLayoutTextStripper;
import nu.pattern.OpenCV;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ReadPdfContent {

    public String getContent(String file) throws IOException {
        String st="";
        PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File(file), "r"));
        pdfParser.parse();
        PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
        PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
        st = pdfTextStripper.getText(pdDocument);
        pdDocument.close();
        return st;
    }
    public void getImage(String file) throws IOException {
        PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File(file), "r"));
        pdfParser.parse();
        PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
        PDPageTree list = pdDocument.getPages();
        int count=0;
        for (PDPage page : list) {
            PDResources pdResources = page.getResources();
            for (COSName c : pdResources.getXObjectNames()) {
                System.out.println(c.getName());
                count++;
                    PDXObject imageObj = pdResources.getXObject(c);
                    if (imageObj instanceof PDImageXObject) {
                        BufferedImage bImage = ((PDImageXObject) imageObj).getImage();
                        ImageIO.write(bImage,"PNG",new File("src/test/resources/images/image_"+count+".png"));
                    }
            }
        }
    }

    public int sendImageCompare(String expectedImage, String actualImage)
    {

        int retVal = 0;
        long startTime = System.currentTimeMillis();

        String filename1 = expectedImage;
        String filename2 = actualImage;


        OpenCV.loadLocally();


        // Load images to compare
        Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.IMREAD_ANYCOLOR);
        Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.IMREAD_ANYCOLOR);


        // Declare key point of images
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();


        // Definition of ORB key point detector and descriptor extractors

        ORB detector = ORB.create();
        //DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);


        // Detect key points
        detector.detect(img1, keypoints1);
        detector.detect(img2, keypoints2);

        // Extract descriptors
        detector.compute(img1, keypoints1, descriptors1);
        detector.compute(img2, keypoints2, descriptors2);


        // Definition of descriptor matcher
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);


        // Match points of two images
        MatOfDMatch matches = new MatOfDMatch();

        // Avoid to assertion failed
       if (descriptors2.cols() == descriptors1.cols()) {
            matcher.match(descriptors1, descriptors2 ,matches);

            // Check matches of key points
            DMatch[] match = matches.toArray();
            double max_dist = 0; double min_dist = 100;

            for (int i = 0; i < descriptors1.rows(); i++) {
                double dist = match[i].distance;
                if( dist < min_dist ) min_dist = dist;
                if( dist > max_dist ) max_dist = dist;
            }
            System.out.println("max_dist=" + max_dist + ", min_dist=" + min_dist);

            // Extract good images (distances are under 10)
            for (int i = 0; i < descriptors1.rows(); i++) {
                if (match[i].distance <= 10) {
                    retVal++;
                }
            }
            System.out.println("matching count=" + retVal);
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("estimatedTime=" + estimatedTime + "ms");

        return retVal;
    }
}
