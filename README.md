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
    git clone https://github.com/yourusername/RealTimeFacialRecognition.git
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
