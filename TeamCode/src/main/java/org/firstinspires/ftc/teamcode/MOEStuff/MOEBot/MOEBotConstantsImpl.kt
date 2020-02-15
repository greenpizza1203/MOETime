package org.firstinspires.ftc.teamcode.MOEStuff.MOEBot

import org.firstinspires.ftc.teamcode.MOEStuff.MOEBot.MOEConfig.*

interface MOEBotConstantsImpl {
    fun getRobotSubSystemConfig(): MOEBotSubSystemConfig {
        return MOEBotSubSystemConfig()
    }

    fun createRobot(): MOEBot {
        return MOEBot(this)
    }


    fun getRobotInitialState(): MOERobotInitialStateConfig {
        return MOERobotInitialStateConfig()
    }

    fun getGyroConfig(): MOEGyroConfig {
        return MOEGyroConfig(getRobotInitialState().robotInitial.degAng)
    }

    fun getVuforiaConfig(): MOEVuforiaConfig {
        return MOEVuforiaConfig()
    }


}