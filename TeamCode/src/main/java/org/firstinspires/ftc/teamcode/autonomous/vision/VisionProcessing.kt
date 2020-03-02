package org.firstinspires.ftc.teamcode.autonomous.vision

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import org.firstinspires.ftc.teamcode.utilities.external.AdvancedMath.Rectangle
import org.firstinspires.ftc.teamcode.utilities.external.minIndex
import org.firstinspires.ftc.teamcode.utilities.internal.crop
import org.firstinspires.ftc.teamcode.utilities.internal.saveTo
import org.firstinspires.ftc.teamcode.utilities.internal.scale
import java.util.*

enum class SkyStoneLocation { LEFT, MIDDLE, RIGHT }

//1920 x 1080 | 800 x 448
fun getSkyStoneLocationFromBitmap(bm: Bitmap?, frame: Rectangle, flipSkystoneIndex: Boolean): SkyStoneLocation {
    Log.e("vuforia", "error")
    if (bm == null) return SkyStoneLocation.LEFT
    val randomUUID = UUID.randomUUID()
    val file = Environment.getExternalStorageDirectory().absolutePath + "/FirstTest/skystone_$randomUUID.png"
    Log.e("imagePath", file)
    bm.saveTo(file)
    val crop = bm.crop(frame)
    val cropped_file = Environment.getExternalStorageDirectory().absolutePath + "/FirstTest/skystone_${randomUUID}_cropped.png"
    crop.saveTo(cropped_file)
    val image = crop.scale(4, 1)!!
    val vList = List(4) { image.getPixel(it, 0).toHSV().V }

    // Averaging the values of the middle skystone (the left and right have only one unit).
    val mergedList = listOf(vList[0], (vList[1] + vList[2]) / 2, vList[3])
    val minIndex = mergedList.minIndex()!!
    val skystoneIndex = if (flipSkystoneIndex) 2 - minIndex else minIndex

//    val leftV = image.getPixel(0, 0).toHSV().V
//    val centerV = image.getPixel(1, 0).toHSV().V
//    val rightV = image.getPixel(2, 0).toHSV().V

//    val minV = min(min(leftV, centerV), rightV)

    return SkyStoneLocation.values()[skystoneIndex]
}

