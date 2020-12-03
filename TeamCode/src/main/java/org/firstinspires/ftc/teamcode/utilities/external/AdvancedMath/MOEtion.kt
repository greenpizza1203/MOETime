package org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath

data class MOEtion(var pose: Point = Point(0.0, 0.0), var ang: Double = 90.0.toRadians()) {
    constructor(x: Double, y: Double, degAng: Double) : this(Point(x, y), degAng)

    val degAng
        get() = ang.toRadians()

    override fun toString(): String {
        return "pose: $pose\n angle:$degAng"
    }

}
