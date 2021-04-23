package dependencies

object AndroidX {
	const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
	const val viewModel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModel_ktx}"

	object Compose{
		const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
		const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
		const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
		const val compose_material = "androidx.compose.material:material:${Versions.compose}"
		const val compose_icons_core = "androidx.compose.material:material-icons-core:${Versions.compose}"
		const val compose_icons_extended = "androidx.compose.material:material-icons-extended:${Versions.compose}"

		object Integration{
			const val activity = "androidx.activity:activity-compose:${Versions.activity_compose}"
			const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModel_compose}"
			const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
			const val hilt_navigation = "androidx.hilt:hilt-navigation-compose:${Versions.hilt_nav_compose}"
			const val constraint_layout_compose = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraint_layout_compose}"
		}

	}

}