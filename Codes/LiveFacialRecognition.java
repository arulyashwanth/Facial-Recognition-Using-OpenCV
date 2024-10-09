import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import static org.opencv.highgui.HighGui.*;

public class LiveFacialRecognition {

    private static final String FACE_DETECTOR_XML = "haarcascade_frontalface_default.xml";

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained face detection classifier
        CascadeClassifier faceDetector = new CascadeClassifier(FACE_DETECTOR_XML);

        // Open the default camera
        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.err.println("Error opening camera.");
            return;
        }

        while (true) {
            // Read a frame from the camera
            Mat frame = new Mat();
            camera.read(frame);

            if (frame.empty()) {
                System.err.println("Error capturing frame.");
                break;
            }

            // Convert the frame to grayscale
            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            // Detect faces in the frame
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(gray, faceDetections);

            // Draw rectangles around the detected faces
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0), 3);
            }

            // Display the output
            imshow("Live Face Tracking", frame);

            // Exit on key press
            if (waitKey(1) == 27) {
                break;
            }
        }

        // Release resources
        camera.release();
        destroyAllWindows();
    }
}