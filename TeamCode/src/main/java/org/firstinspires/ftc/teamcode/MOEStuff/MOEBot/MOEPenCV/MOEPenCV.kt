package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEPenCV

import android.graphics.Bitmap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.constants.ReferenceHolder
import org.firstinspires.ftc.teamcode.constants.ReferenceHolder.Companion.hardwareMap
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvInternalCamera


class MOEPenCV(val config: MOEOpenCVConfig) {
    lateinit var webcam: OpenCvCamera
    val pipeline = MOEPipeline(this)

    init {
        webcam = if (config.useInternalCamera) getInternalCamera() else getExternalCamera()
        webcam.setPipeline(pipeline)
        webcam.openCameraDevice()
        webcam.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT)
    }

    private fun getInternalCamera(): OpenCvCamera {
        return if (config.enablePreview) {
            val cameraMonitorViewId: Int = getCameraMonitorView()
            OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId)
        } else {
            OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK)
        }
    }

    private fun getExternalCamera(): OpenCvCamera {
        val webcamName = hardwareMap.get(WebcamName::class.java, "Webcam 1")
        val instance = OpenCvCameraFactory.getInstance()
        return if (config.enablePreview) instance.createWebcam(webcamName, getCameraMonitorView()) else instance.createWebcam(webcamName)
    }

    private fun getCameraMonitorView() =
            ReferenceHolder.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)

    fun stop() {
        webcam.stopStreaming()
    }

    fun getBitmap(): Bitmap? {
        pipeline.requestFrame()
        while (ReferenceHolder.moeOpMode.iOpModeIsActive() && pipeline.lastFrame == null) {
        }
        return pipeline.lastFrame!!.toBitMap()!!
    }
}