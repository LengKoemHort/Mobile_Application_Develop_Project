package kh.edu.rupp.dse.mobileapplicationproject.Domain

class Time {
    var Id: Int = 0 // Changed to match the field name in your Firebase database
    var Value: String? = null

    override fun toString(): String {
        return Value ?: ""
    }
}