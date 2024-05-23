package kh.edu.rupp.dse.mobileapplicationproject.Domain

class Location {
    var Id: Int = 0
    var loc: String? = null

    override fun toString(): String {
        return loc ?: ""
    }
}