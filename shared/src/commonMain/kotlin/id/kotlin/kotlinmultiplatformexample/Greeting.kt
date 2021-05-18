package id.kotlin.kotlinmultiplatformexample

class Greeting {
    fun greeting(): String {
        return "Demo My Multiplatform, ${Platform().platform}!"
    }
}