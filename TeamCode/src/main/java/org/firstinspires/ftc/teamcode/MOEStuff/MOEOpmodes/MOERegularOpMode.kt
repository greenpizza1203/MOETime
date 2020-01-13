package org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes

//import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.opmodeutils.MOEGamePad
import com.google.firebase.database.DatabaseReference
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBot
import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEBotConstants
import org.firstinspires.ftc.teamcode.MOEStuff.MOEFirebase.MOEFirebase
import org.firstinspires.ftc.teamcode.constants.OpModeInterface
import org.firstinspires.ftc.teamcode.constants.ReferenceHolder
import org.firstinspires.ftc.teamcode.constants.ReferenceHolder.Companion.setRobotRef
import org.firstinspires.ftc.teamcode.utilities.internal.addData

abstract class MOERegularOpMode : LinearOpMode(), MOEFirebase, OpModeInterface, MOEBotConstants {
    lateinit var dataRef: DatabaseReference
    lateinit var robot: MOEBot
    override fun iOpModeIsActive(): Boolean = opModeIsActive()

    override val iTelemetry: Telemetry
        get() = this.telemetry

    override val iHardwareMap: HardwareMap
        get() = this.hardwareMap

    override val iIsStopRequested: Boolean
        get() = this.isStopRequested

    final override fun runOpMode() {
        moeDoubleInternalInit()
//        Log.e("wait", "wait")
        initRobot()
//        Log.e("wait2", "wait")

        moeInternalInit()
//        Log.e("wait3", "wait")
        setRobotRef(robot)
//        Log.e("wait4", "wait")

        initOpMode()
//        Log.e("wait5", "wait")

        moeInternalPostInit()
//        Log.e("start wait", "wait")
        waitForStart()
//        Log.e("end wait", "end")
        offsetRobotValues()
        run()
    }

    private fun initRobot() {
        robot = createRobot()
    }


    override fun waitForStart() {
        while (!isStarted) {
            notifyTelemetry()
        }
    }


    private fun moeDoubleInternalInit() {
        ReferenceHolder.setRefs(this)
        addListener()?.let {
            dataRef = it
        }
        //        ref = addListener()
    }


    //    private fun addListener() {
    //        val customRef = getCustomRef(MOEConfig.config) ?: return
    //        customRef.addValueEventListener(object : MOEEventListener() {
    //            override fun onDataChange(snapshot: DataSnapshot) {
    //                onConfigChanged(snapshot)
    //            }
    //        })
    //        customRef.addChildEventListener(this)
    //        ref = customRef
    //    }

    private fun notifyTelemetry() {
        telemetry.addData("waiting for start")
        telemetry.update()
    }

    open fun moeInternalInit() {

    }

    open fun initOpMode() {

    }

    open fun moeInternalPostInit() {

    }

    private fun offsetRobotValues() {
        robot.offsetValues(this)
    }

    abstract fun run()
}