package kh.edu.rupp.dse.mobileapplicationproject.domain

class Location(var id: Int = 0, var loc: String = "") {
    override fun toString(): String {
        return loc
    }
}
