package kh.edu.rupp.dse.mobileapplicationproject.domain

class Time(var id: Int = 0, var Value: String = "") {
    override fun toString(): String {
        return Value
    }
}