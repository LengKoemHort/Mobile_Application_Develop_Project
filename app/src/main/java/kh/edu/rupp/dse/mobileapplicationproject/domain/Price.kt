package kh.edu.rupp.dse.mobileapplicationproject.domain

class Price(var id: Int = 0, var Value: String = "") {
    override fun toString(): String {
        return Value
    }
}