package cz.payola.domain.entities.plugins

import cz.payola.domain.entities.Entity

/**
  * @param _parameter The corresponding parameter.
  * @param _value Value of the parameter.
  * @tparam A Type of the parameter value.
  */
abstract class ParameterValue[A](protected val _parameter: Parameter[A], protected var _value: A)
    extends Entity with cz.payola.common.entities.plugins.ParameterValue[A]
{
    checkConstructorPostConditions()

    type ParameterType = Parameter[A]

    override def canEqual(other: Any): Boolean = {
        other.isInstanceOf[ParameterValue[_]]
    }

    override protected def checkInvariants() {
        super[Entity].checkInvariants()
        require(parameter != null, "The parameter mustn't be null.")
    }
}