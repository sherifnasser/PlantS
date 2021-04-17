package dependencies

object InstrumentationTest {

  object AndroidX{
    const val junit_ktx = "androidx.test.ext:junit:${Versions.androidx_junit_ktx}"
    const val runner = "androidx.test:runner:${Versions.androidx_test}"
  }

  object Hilt{
    const val hilt = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val kapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
  }

  const val compose_ui = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"

  const val mockk = "io.mockk:mockk-android:${Versions.mockk}"
}