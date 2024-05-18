package kh.edu.rupp.dse.mobileapplicationproject.Domain

class Price {
    var Id: Int = 0
    var Value: String? = null

    override fun toString(): String {
        return Value ?: ""
    }
}