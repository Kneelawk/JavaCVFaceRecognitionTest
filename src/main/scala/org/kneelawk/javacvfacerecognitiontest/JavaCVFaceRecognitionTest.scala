package org.kneelawk.javacvfacerecognitiontest

import java.io.File

import scala.collection.mutable.ListBuffer

import org.bytedeco.javacpp.Loader
import org.bytedeco.javacpp.opencv_imgcodecs.imread
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.RectVector
import org.bytedeco.javacpp.opencv_core.Rect
import org.bytedeco.javacpp.opencv_face.FaceRecognizer

object JavaCVFaceRecognitionTest {
  def main(args: Array[String]) {
    var inputDir = "."
    var outputDir = "."
    if (args.length > 0) {
      inputDir = args(0)
    }
    if (args.length > 1) {
      outputDir = args(1)
    }
    
    val classifier = new CascadeClassifier;
    classifier.load(Loader.extractResource("/lbpcascade_frontalface.xml", null, "classifier", ".xml").getAbsolutePath)
    
    val faceRects = new ListBuffer[Rect]
    val faces = new ListBuffer[Mat]
    val images = new ListBuffer[Mat]
    
    val inputImages = new File(inputDir).listFiles()
    inputImages withFilter(_.getName().toLowerCase().endsWith(".jpg")) foreach { f =>
      val mat = imread(f.getAbsolutePath)
      
      images += mat
      
      val detected = new RectVector
      classifier.detectMultiScale(mat, detected)
      val size = detected.size()
      
      for (i <- 0l to size) {
        val faceRect = detected.get(i)
        faceRects += faceRect
        faces += new Mat(mat, faceRect)
      }
    }
    
    classifier.close()
    
    val recognizer = null
    
    faceRects foreach { r =>
      
    }
  }
}