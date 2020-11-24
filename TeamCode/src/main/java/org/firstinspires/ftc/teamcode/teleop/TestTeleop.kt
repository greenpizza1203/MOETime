package org.firstinspires.ftc.teamcode.teleop

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.WrapperHandler
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "TestTeleop", group = "Teleop")
class TestTeleop() : OpMode() {
    lateinit var frontLeftMotor: DcMotor
    lateinit var frontRightMotor: DcMotor
    lateinit var backLeftMotor: DcMotor
    lateinit var backRightMotor: DcMotor
    lateinit var intakeMotor: DcMotor
    lateinit var outerShooterMotor: DcMotorEx
    lateinit var innerShooterMotor: DcMotorEx
    lateinit var trigger: CRServo
    lateinit var ma3: AnalogInput
    var ma3Wrapped: WrapperHandler = WrapperHandler(5.0){
        ma3.voltage
    }

    override fun init() {
        frontLeftMotor = hardwareMap.dcMotor["FLM"] as DcMotorEx
        frontRightMotor = hardwareMap.dcMotor["FRM"] as DcMotorEx
        backLeftMotor = hardwareMap.dcMotor["BLM"] as DcMotorEx
        backRightMotor = hardwareMap.dcMotor["BRM"] as DcMotorEx
        frontLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        backLeftMotor.direction = DcMotorSimple.Direction.REVERSE
        intakeMotor = hardwareMap.dcMotor["intake_motor"] as DcMotorEx
        outerShooterMotor = hardwareMap.dcMotor["ORSM20"] as DcMotorEx
        innerShooterMotor = hardwareMap.dcMotor["IRSM21"] as DcMotorEx
        ma3 = hardwareMap.analogInput["SPE21"]
        trigger = hardwareMap.crservo["RTS21"]
        trigger.power
        gamepad1.setJoystickDeadzone(0.1f)

    }

    override fun loop() {

        var y = gamepad1.left_stick_y.toDouble()
        var x = gamepad1.left_stick_x.toDouble()
        var rx = gamepad1.right_stick_x.toDouble()

        frontLeftMotor.setPower((-y + x + rx) * 0.6)
        backLeftMotor.setPower((-y - x + rx) * 0.6)
        frontRightMotor.setPower((-y - x - rx) * 0.6)
        backRightMotor.setPower((-y + x -rx) * 0.6)

//        if (gamepad1.x) {
//            trigger.setPower(0.5)
//
//            if(ma3Wrapped.getValue() <= 0.0){
//                trigger.setPower(0.0)
//            }
//        }


//        if (!previousX && gamepad1.x) {
//            enabledX = !enabledX
//        }
//
//        previousX = gamepad1.x
//
//        if (!enabledX) {
//            trigger.setPower(0.5)
//            return
//        }

        if(gamepad1.x){
            trigger.setPower(0.5)
        }

        if(ma3Wrapped.getValue() <= triggerTarget) {
            trigger.setPower(0.0)
            triggerTarget = (triggerTarget - 2.5).toDouble()
        }

        if (!previousA && gamepad1.a) {
            enabled = !enabled
        }

        previousA = gamepad1.a

        if (!enabled) {
            intakeMotor.power = 1.0
            return
        }

        intakeMotor.power = 0.0

        telemetry.addData("prevB", previousB)
        telemetry.addData("b", gamepad1.b)
        if (!previousB && gamepad1.b) {
            enabledB = !enabledB
        }

        previousB = gamepad1.b
        if (!enabledB) {
            outerShooterMotor.velocity = 2000.0
            innerShooterMotor.velocity = 2000.0
            return
        }

        outerShooterMotor.velocity = targetVelocity
        innerShooterMotor.velocity = targetVelocity

//        targetVelocity += gamepad1.right_trigger.toDouble() * speed
//        targetVelocity -= gamepad1.left_trigger.toDouble() * speed
        telemetry.addData("ma3Raw", ma3.voltage)
        telemetry.addData("ma3Wrapped", ma3Wrapped.getValue())
        telemetry.addData("triggerTarget",triggerTarget)
        telemetry.addData("FLP", frontLeftMotor.power)
        telemetry.addData("FRP", frontRightMotor.power)
        telemetry.addData("BLP", backLeftMotor.power)
        telemetry.addData("BRP", backRightMotor.power)
        telemetry.addData("intake_motor", intakeMotor.power)
        telemetry.addData("a", gamepad1.a)
        telemetry.addData("enabled", enabled)
        telemetry.addData("b", gamepad1.b)
        telemetry.addData("enabledB", enabledB)
        telemetry.addData("outerVelocity", outerShooterMotor.velocity)
        telemetry.addData("innerVelocity", innerShooterMotor.velocity)
        telemetry.addData("targetVelocity", targetVelocity)
        telemetry.addData("right_stick_y", gamepad1.right_stick_y)
    }


        var targetVelocity = 0.0
        val speed = 10

        var triggerTarget:Double = 0.0

        var previousA = false
        var enabled = true
        var previousB = false
        var enabledB = true
        var previousX = false
        var enabledX = true

}