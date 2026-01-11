package dev.nextftc.hardware.impl

import dev.nextftc.hardware.controllable.Controllable
import kotlin.properties.Delegates

class VoltageCompensatingMotor(
    private val controllable: Controllable,
    private val voltageFunction: () -> Double,
    private val nominalVoltage: Double = 12.0,
    private val staticFrictionCoeff: Double = 0.0
) : Controllable by controllable {

    override var power by Delegates.observable(0.0) { _, _, new ->
        val voltage = voltageFunction.invoke()
        controllable.power = new * (nominalVoltage - (nominalVoltage * staticFrictionCoeff)) / (voltage - ((nominalVoltage * nominalVoltage / voltage) * staticFrictionCoeff))
    }
}