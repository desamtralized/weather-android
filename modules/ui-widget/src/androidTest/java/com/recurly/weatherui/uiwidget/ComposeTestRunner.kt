package com.recurly.weatherui.uiwidget

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class ComposeTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // Use the base Application class since we don't need Hilt for these tests
        return super.newApplication(cl, Application::class.java.name, context)
    }
}