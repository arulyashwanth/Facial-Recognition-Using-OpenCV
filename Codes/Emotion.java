import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.HashMap;
import java.util.Map;

public class Emotion {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load pre-trained face detector (Haar Cascade classifier)
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("E:\\Java\\Project\\Second Sem Project\\src\\haarcascade_frontalface_default.xml"); // Load the XML file for face detection

        // Load images and associate them with names
        Map<Integer, String> idToName = new HashMap<>();
        idToName.put(0, "Alice");
        idToName.put(1, "Bob");
        // Add more mappings as needed

        // Load known faces for recognition
        Map<Integer, Mat> idToFace = new HashMap<>();
        idToFace.put(0, Imgcodecs.imread("E:\\Java\\Project\\Second Sem Project\\src\\image.jpg")); // Load images for Alice
        idToFace.put(1, Imgcodecs.imread("E:\\Java\\Project\\Second Sem Project\\src\\image1.jpg"));   // Load images for Bob
        // Add more mappings as needed

        // Load test image
        Mat image = Imgcodecs.imread("E:\\Java\\Project\\Second Sem Project\\src\\image.jpg"); // Replace "test_image.jpg" with your image file path

        // Convert image to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grayImage, grayImage);

        // Detect faces in the image
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faceDetections);

        System.out.println("Number of faces detected: " + faceDetections.toArray().length);

        // Recognize faces
        for (Rect rect : faceDetections.toArray()) {
            Mat croppedFace = new Mat(grayImage, rect);
            Imgproc.resize(croppedFace, croppedFace, new org.opencv.core.Size(100, 100)); // Resize the face image for recognition

            // Perform face recognition
            // Here you can use methods from the Face class for recognition
            // Example:
            // Face.predict()
            // Face.train()
            // Face.load()

            // Draw rectangle around face
            Imgproc.rectangle(image, new org.opencv.core.Point(rect.x, rect.y),
                    new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0)); // BGR color (green)
        }

        // Display the image with detected faces
        Imgcodecs.imwrite("detected_faces.jpg", image); // Save the result to a file
        System.out.println("Image with detected faces saved.");
    }
}
