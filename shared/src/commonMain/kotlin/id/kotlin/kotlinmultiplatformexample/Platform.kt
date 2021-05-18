package id.kotlin.kotlinmultiplatformexample

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class Platform() {
    val platform: String
}