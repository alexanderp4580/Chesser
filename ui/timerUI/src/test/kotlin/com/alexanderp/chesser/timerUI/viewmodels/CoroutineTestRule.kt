package com.alexanderp.chesser.timerUI.viewmodels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.ContinuationInterceptor

/**
 * Set dispatchers rule.
 *
 * Credit: https://proandroiddev.com/eliminating-coroutine-leaks-in-tests-3af825e7cde2
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class CoroutineTestRule : TestRule, TestCoroutineScope by TestCoroutineScope() {

    val dispatcher = coroutineContext[ContinuationInterceptor] as TestCoroutineDispatcher

    override fun apply(
        base: Statement,
        description: Description?
    ) = object : Statement() {

        override fun evaluate() {
            Dispatchers.setMain(dispatcher)
            base.evaluate()

            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
    }
}
