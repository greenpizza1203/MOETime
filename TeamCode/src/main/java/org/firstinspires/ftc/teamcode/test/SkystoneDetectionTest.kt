package org.firstinspires.ftc.teamcode.test


import com.google.firebase.database.DatabaseReference
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.firstinspires.ftc.teamcode.MOEStuff.MOEOpmodes.MOEAuton
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Rectangle
import org.firstinspires.ftc.teamcode.utilities.internal.addData

@Disabled
@Autonomous(name = "SkystoneDetection", group = "test")
class SkystoneDetectionTest : MOEAuton() {

    override fun getCustomRef(ref: DatabaseReference): DatabaseReference = ref

    //    lateinit var data: DataSnapshot
    var cropRectangle: Rectangle? = null

    override fun initOpMode() {

        telemetry.apply {
            addData("data")
        }
    }


    override fun run() {
        telemetry.addData("running")
        while (opModeIsActive()) {
            telemetry.update()
        }
    }


}


