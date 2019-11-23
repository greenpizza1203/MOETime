package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEGyro

import org.firstinspires.ftc.teamcode.constants.ReferenceHolder.Companion.robot
import toNormalAngle


class MOESlamGyro : MOEGyro() {
    override fun getRawEulerAngle(): Double = -robot.slam.getRawTheta()
    override fun getRawAngle(): Double = getRawEulerAngle().toNormalAngle()
}

