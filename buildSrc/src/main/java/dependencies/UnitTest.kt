package dependencies

object UnitTest {
	object Junit5Jupiter{
		const val api = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_jupiter}"
		const val engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit_jupiter}"
		const val params = "org.junit.jupiter:junit-jupiter-params:${Versions.junit_jupiter}"
	}
	const val truth = "com.google.truth:truth:${Versions.truth}"
}