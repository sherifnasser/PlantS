package com.sherifnasser.plants.register.domain.util

class NoSimCardException:IllegalStateException(
    "No sim card available for running device"
)