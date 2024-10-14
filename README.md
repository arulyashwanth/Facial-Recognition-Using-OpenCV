# Real-Time Facial Recognition, Age & Gender Prediction, Expression Detection, and Face Counting using OpenCV and Deep Learning

This project provides a comprehensive solution for real-time facial recognition, age and gender prediction, expression detection, and face counting using **OpenCV** and **deep learning models**. It integrates several key functionalities to analyze and count human faces in real-time through a live video feed. This README outlines the setup, functionality, and usage instructions for running the project.

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Requirements](#requirements)
4. [Installation](#installation)
5. [Project Structure](#project-structure)
6. [Usage](#usage)
7. [Modules Overview](#modules-overview)
8. [Future Enhancements](#future-enhancements)
9. [License](#license)

## Project Overview

The system is designed to:
- Detect faces in real-time from a live camera feed.
- Predict the age and gender of detected faces.
- Identify facial expressions such as happy, sad, angry, and others.
- Count the number of faces in each frame and display the count.

By leveraging **OpenCV** for computer vision tasks and deep learning models for predictions, this project demonstrates practical applications of **machine learning** in human-computer interaction, security, retail analytics, and more.

## Features

1. **Face Detection**: Efficient real-time face detection using Haar Cascade Classifier.
2. **Age and Gender Prediction**: Predicts age and gender using deep learning models.
3. **Expression Recognition**: Detects facial expressions with CNNs trained on facial emotion datasets.
4. **Face Counting**: Provides real-time count of faces detected in a frame.
5. **User-Friendly Interface**: A GUI to control and visualize the real-time video feed with analysis results.
6. **Modular Architecture**: Extensible design to allow future updates and additional functionality.

## Requirements

- **Java** (JDK 8 or higher)
- **OpenCV** (4.x or higher) with Java bindings
- **Deep Learning Models** (Pre-trained models for age, gender, and expression recognition)
- **Swing** (For GUI)
- **Maven** (For dependency management)

### Libraries and Dependencies:
- OpenCV (`opencv-java`)
- DNN module of OpenCV for age and gender recognition
- CNN-based models for expression recognition
- Pre-trained models for face detection (Haar Cascades)

## Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/arulyashwanth/Facial-Recognition-Using-OpenCV
    cd RealTimeFacialRecognition
    ```

2. **Set Up OpenCV**:
    - Download and install OpenCV from the official website: [OpenCV Downloads](https://opencv.org/releases/)
    - Add OpenCV's native library to your system path.
    - Add the OpenCV JAR to your project build path.

3. **Download Pre-trained Models**:
    - Ensure the required pre-trained models for age, gender, and expression recognition are available in the `resources` directory. These models should include:
      - `age_deploy.prototxt`
      - `age_net.caffemodel`
      - `gender_deploy.prototxt`
      - `gender_net.caffemodel`
      - Haar Cascades for face detection (`haarcascade_frontalface_default.xml`)
      
    You can download them from OpenCV’s GitHub or other trusted sources.

4. **Build the Project**:
    - Use **Maven** to resolve dependencies:
    ```bash
    mvn clean install
    ```

## Project Structure

```bash
|-- src
|   |-- main
|       |-- java
|           |-- com.example.facialrecognition
|               |-- LiveFacialRecognition.java    # Handles real-time video capture and face detection
|               |-- Main.java                    # Main class integrating all functionalities
|               |-- AgeGenderRecognition.java    # Predicts age and gender
|               |-- ExpressionRecognition.java   # Recognizes facial expressions
|               |-- Count.java                   # Counts the number of faces
|       |-- resources
|           |-- haarcascade_frontalface_default.xml  # Haar Cascade for face detection
|           |-- age_deploy.prototxt                 # Age prediction model
|           |-- gender_deploy.prototxt              # Gender prediction model
|           |-- emotion_model.h5                    # Emotion prediction model
|-- pom.xml    # Maven configuration file
|-- README.md

```

## Usage

1. **Run the Project**:
    After setting up OpenCV and the models, execute the `Main` class:
    ```bash
    mvn exec:java -Dexec.mainClass="com.example.facialrecognition.Main"
    ```

2. **User Interface**:
    - The GUI will open and start capturing video from your camera.
    - Detected faces will be highlighted with bounding boxes.
    - Age, gender, and facial expression predictions will be displayed near each face.
    - The number of faces detected will be shown on the screen in real-time.

3. **Stopping the Application**:
    - You can stop the video feed and exit the program using the GUI controls.

## Modules Overview

### 1. **LiveFacialRecognition Module**:
- **Purpose**: Detects faces in real-time using Haar Cascade Classifier.
- **Key Class**: `LiveFacialRecognition`
- **Dependencies**: OpenCV Haar Cascade.

### 2. **AgeGenderRecognition Module**:
- **Purpose**: Predicts the age and gender of detected faces using pre-trained deep learning models.
- **Key Class**: `AgeGenderRecognition`
- **Dependencies**: Pre-trained models for age and gender.

### 3. **ExpressionRecognition Module**:
- **Purpose**: Recognizes facial expressions (e.g., happy, sad, angry) using CNN models.
- **Key Class**: `ExpressionRecognition`
- **Dependencies**: Pre-trained CNN for expression classification.

### 4. **Count Module**:
- **Purpose**: Counts the number of faces detected in each frame and displays the count on the video feed.
- **Key Class**: `Count`
- **Dependencies**: Uses face detection results from `LiveFacialRecognition`.

### 5. **Main Module**:
- **Purpose**: Integrates all modules and provides the main GUI to run and manage the real-time video feed.
- **Key Class**: `Main`
- **Dependencies**: All other modules, OpenCV, and Java Swing.

## Future Enhancements

- **Face Recognition**: Add the ability to recognize and identify known individuals from a pre-defined database.
- **Emotion Intensity**: Enhance the expression recognition module to detect the intensity of emotions.
- **Performance Optimization**: Improve the real-time processing speed using GPU acceleration or optimizing the deep learning models.
- **Multi-Language Support**: Add support for other languages in the user interface for broader accessibility.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---
