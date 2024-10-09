import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.tensorflow.Graph;
import org.tensorflow.Tensor;
import org.tensorflow.Session;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.FloatBuffer;
import org.opencv.core.CvType;
import static org.opencv.highgui.HighGui.*;

public class liveem {

    private static final String FACE_DETECTOR_XML = "haarcascade_frontalface_default.xml";
    private static final String MODEL_FILE = "fer2013.h5";

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained face detection classifier
        CascadeClassifier faceDetector = new CascadeClassifier(FACE_DETECTOR_XML);

        // Load the pre-trained emotion recognition model
        try (Graph graph = new Graph()) {
            byte[] graphBytes = Files.readAllBytes(Paths.get(MODEL_FILE));
            graph.importGraphDef(graphBytes);

            try (Session session = new Session(graph)) {
                // Open the default camera
                VideoCapture camera = new VideoCapture(0);
                if (!camera.isOpened()) {
                    System.err.println("Error opening camera.");
                    return;
                }

                // Set camera properties (optional)
                camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
                camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

                while (true) {
                    // Read a frame from the camera
                    Mat frame = new Mat();
                    camera.read(frame);

                    if (frame.empty()) {
                        System.err.println("Error capturing frame.");
                        break;
                    }

                    // Convert the frame to grayscale
                    Mat grayFrame = new Mat();
                    Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

                    // Detect faces in the frame
                    MatOfRect faces = new MatOfRect();
                    faceDetector.detectMultiScale(grayFrame, faces);

                    // Loop over the detected faces
                    for (Rect face : faces.toArray()) {
                        // Preprocess the face image for emotion recognition
                        Mat faceMat = grayFrame.submat(face);
                        Mat resizedFace = new Mat();
                        Imgproc.resize(faceMat, resizedFace, new Size(48, 48));
                        float[] input = new float[48 * 48];
                        resizedFace.convertTo(resizedFace, CvType.CV_32F);
                        resizedFace.get(0, 0, input);

                        // Run inference
                        Tensor inputTensor = Tensor.create(input);
                        Tensor outputTensor = session.runner()
                                .feed("input", inputTensor)
                                .fetch("output")
                                .run()
                                .get(0);

                        float[] probabilities = new float[7]; // Assuming there are 7 emotions
                        outputTensor.copyTo(probabilities);

                        // Find the index of the emotion with the highest probability
                        int maxIndex = 0;
                        for (int i = 1; i < probabilities.length; i++) {
                            if (probabilities[i] > probabilities[maxIndex]) {
                                maxIndex = i;
                            }
                        }

                        // Map the index to an emotion label
                        String emotionLabel = "";
                        switch (maxIndex) {
                            case 0:
                                emotionLabel = "Angry";
                                break;
                            case 1:
                                emotionLabel = "Disgusted";
                                break;
                            case 2:
                                emotionLabel = "Fearful";
                                break;
                            case 3:
                                emotionLabel = "Happy";
                                break;
                            case 4:
                                emotionLabel = "Sad";
                                break;
                            case 5:
                                emotionLabel = "Surprised";
                                break;
                            case 6:
                                emotionLabel = "Neutral";
                                break;
                        }

                        // Draw a rectangle around the face and display the emotion label
                        Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
                        Imgproc.putText(frame, emotionLabel, new Point(face.x, face.y - 5), Imgproc.FONT_HERSHEY_SIMPLEX, 0.8, new Scalar(0, 255, 0), 2);
                    }

                    // Display the frame with the detected emotions
                    imshow("Emotion Recognition", frame);

                    // Wait for a key press and exit if the 'q' key is pressed
                    int key = waitKey(10);
                    if (key == 'q' || key == 27) {
                        break;
                    }
                }

                // Release the camera and close the OpenCV window
                camera.release();
                destroyAllWindows();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}