import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Count {

    private static final String FACE_DETECTOR_XML = "haarcascade_frontalface_default.xml";
    private JFrame frame;
    private JLabel imageLabel;
    private VideoCapture camera;
    private CascadeClassifier faceDetector;
    private boolean running;

    public Count() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        faceDetector = new CascadeClassifier(FACE_DETECTOR_XML);
        camera = new VideoCapture(0);
        running = true;

        if (!camera.isOpened()) {
            System.err.println("Error opening camera.");
            System.exit(1);
        }

        frame = new JFrame("Face Detection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        imageLabel = new JLabel();
        frame.add(imageLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!running) {
                    running = true;
                    new VideoFeedTaker().start();
                }
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false;
            }
        });

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);

        new VideoFeedTaker().start();
    }

    private class VideoFeedTaker extends Thread {
        @Override
        public void run() {
            while (running) {
                Mat frame = new Mat();
                camera.read(frame);

                if (!frame.empty()) {
                    Mat gray = new Mat();
                    Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

                    MatOfRect faceDetections = new MatOfRect();
                    faceDetector.detectMultiScale(gray, faceDetections);

                    for (Rect rect : faceDetections.toArray()) {
                        Imgproc.rectangle(frame, new org.opencv.core.Point(rect.x, rect.y), new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                                new Scalar(0, 255, 0), 3);
                    }

                    String faceCount = "Number of faces: " + faceDetections.toArray().length;
                    Imgproc.putText(frame, faceCount, new org.opencv.core.Point(10, 30), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 0, 255), 2);

                    ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
                    imageLabel.setIcon(image);
                    imageLabel.repaint();

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private BufferedImage Mat2BufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Count();
            }
        });
    }
}
