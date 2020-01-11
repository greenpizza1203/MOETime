package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOESlam

import android.util.Log
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcontroller.moeglobal.slam.SlamData
import org.firstinspires.ftc.robotcontroller.moeglobal.slam.SlamHandler
import org.firstinspires.ftc.robotcontroller.moeglobal.slam.SlamT265Handler
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEChassis.Transformation
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.MOESlamConfig
import org.firstinspires.ftc.teamcode.constants.MOEConstants.Units.ASTARS_PER_METER
import org.firstinspires.ftc.teamcode.utilities.external.quaternionToHeading
import org.firstinspires.ftc.teamcode.constants.ReferenceHolder.Companion.moeOpMode
import org.firstinspires.ftc.teamcode.constants.ReferenceHolder.Companion.telemetry
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.*

class MOESlam(val config: MOESlamConfig) {
    val transformation: Transformation
        get() = getTrans()
    //    val pose
//        get() = getTrans().pose
    val transHandler = MOERohanTrans(config)
//    lateinit var config: MOESlamConfig

    //TODO: change this once the rest of the stuff works
//    val pose: Point
//        get() = getCameraPose() * MOEConstants.Units.ASTARS_PER_METER


    //
    //    private fun setOptions(options: MOESlamOptions) {
    //        this.options = options;
    //    }

    val handler: SlamT265Handler?
        get() = SlamHandler.t265Handler
    var slamOffset: FloatArray = floatArrayOf(0.0F, 0.0F, 0.0F)
    var thetaOffset: Double = 0.0

    init {
        resetValues()
    }


    fun checkConnection() {
        //        handler?.killStream()
        handler?.startStream()
    }

    fun getRawOffsetPose(): Point = getRawPose().let {
        return Point(it[0] - slamOffset[0].toDouble(), it[2] - slamOffset[2].toDouble())
    }

    fun getAstarPose() = getRawOffsetPose() * ASTARS_PER_METER
//    @Deprecated("")
//    fun getCameraPose(): Point {
//        val rawPose = getRawOffsetPose()
//        return Point(rawPose.x, rawPose.y)
//    }

//    @Deprecated("")
//    fun getRobotPoseInCameraAxis(): Point {
//        return getCameraPose().getRelativePoint(
//                distanceFromThis = -SLAM.CAMERA_DISTANCE,
//                degTheta = (getTheta() + SLAM.INITIAL_CAMERA_THETA)
//        )
//                rawPose.getRelativePoint(Localization.CAMERA_DISTANCE, Localization.)
//                return rawPose.rotateAroundOrigin(angle)
//
//                val theta = toRadians(getTheta() + Localization.CAMERA_THETA)
//                return getCameraPose().getRelativePointLocalization.CAMERA_DISTANCE, theta)
//    }

//    @Deprecated("")
//    fun getRobotPose(): Point {
//        val untranslatedPose = getRobotPoseInCameraAxis().rotateAroundOrigin(config.robotToFieldTheta)
//        return Point(untranslatedPose.x + config.xOffset, untranslatedPose.y + config.yOffset)
//    }

//    @Deprecated("")
//    fun getScaledRobotPose(): Point = getRobotPose() * MOEConstants.Units.ASTARS_PER_METER
    //
    //    fun getRawEulerTheta(): Double {
    //
    //    }

    fun getTheta(): Double = -(getRawTheta().toEulerAngle() - thetaOffset)

    fun getRawTheta(): Double = quaternionToHeading(getQuadTheta())

    fun getRawPose() = SlamData.curPose

//    override fun toString(): String {
//        return getCameraPose().toString()
//    }

    fun resetValues() {
        setOffset(SlamData?.curPose!!)
        this.thetaOffset = -getRawTheta()
    }

    private fun setOffset(curPose: FloatArray) {
        this.slamOffset = curPose.copyOf()
    }

    //
//    private fun setInitialOffsets() {
//        val point = Point(0.0, 0.0).getRelativePoint(
//                distanceFromThis = SLAM.CAMERA_DISTANCE,
//                degTheta = (getTheta() + SLAM.INITIAL_CAMERA_THETA)
//        );
//        setOffset(floatArrayOf(point.x.toFloat(), point.y.toFloat()))
//    };
    val zeros = doubleArrayOf(0.0, 0.0, 0.0, 0.0)

    fun restart() {
        Log.e("restart", "started")
        Log.e("datas", SlamData.quatAngle.contentToString())
        handler?.restart()
        var last = SlamData.lastTimestamp
        val timer = ElapsedTime().apply { seconds() }
        while (!moeOpMode.iIsStopRequested && SlamData.lastTimestamp != 0L) {
            if (last != SlamData.lastTimestamp) {
                last = SlamData.lastTimestamp
                timer.reset()
            }
            if (timer.seconds() > 2.5) {
                break
            }

            //            telemetry.addData("datas", SlamData.lastTimestamp.toString())
            //            telemetry.update()

        }
        checkConnection()
        waitForData()
        Log.e("restart", "done")
        Log.e("datae", SlamData.quatAngle.contentToString())

        //        handler?.waitFor
    }

    private fun getQuadTheta(): DoubleArray = SlamData.quatAngle
    fun waitForData() {
        while (!moeOpMode.iIsStopRequested && SlamData.lastTimestamp == 0L) {
            //            Log.e("info2", SlamData?.lastTimestamp.toString())
            //            telemetry.addData("datar", SlamData.lastTimestamp.toString())
            telemetry.update()
        }
    }

    private fun getTrans(): Transformation {
        return transHandler.getTrans(getRawTrans())
    }

    fun getRawTrans(): Transformation {
        return Transformation(getAstarPose(), getTheta())
    }
}
