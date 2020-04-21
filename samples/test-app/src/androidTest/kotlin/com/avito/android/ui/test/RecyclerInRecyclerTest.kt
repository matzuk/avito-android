package com.avito.android.ui.test

import com.avito.android.ui.RecyclerInRecyclerActivity
import com.google.common.truth.ThrowableSubject
import com.google.common.truth.Truth
import org.junit.Rule
import org.junit.Test

class RecyclerInRecyclerTest {

    @get:Rule
    val rule = screenRule<RecyclerInRecyclerActivity>()

    @Test
    fun typedItemAtPosition_foundFirstValue() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        Screen.recyclerInRecycler.list.horizontalList.cellAt(position = 0)
            .title.checks.displayedWithText("0")
    }

    @Test
    fun typedItemWithMatcher_foundFirstValue() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        Screen.recyclerInRecycler.list.horizontalList.cellWithTitle("0")
            .title.checks.displayedWithText("0")
    }

    @Test
    fun typedItemAtPosition_foundThirdValue() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        Screen.recyclerInRecycler.list.horizontalList.cellAt(position = 2)
            .title.checks.displayedWithText("2")
    }

    @Test
    fun typedItemWithMatcher_foundThirdValue() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        Screen.recyclerInRecycler.list.horizontalList.cellWithTitle("2")
            .title.checks.displayedWithText("2")
    }

    @Test
    fun typedItemWithMatcher_noItem() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        try {
            Screen.recyclerInRecycler.list.horizontalList.cellWithTitle("3")
                .title.checks.displayedWithText("3")
        } catch (e: Exception) {
            Truth.assertThat(e)
                .checkCausesDeeply {
                    isInstanceOf(AssertionError::class.java)
                    hasMessageThat()
                        .contains("No item in recycler. Recycler size: 3")
                }
        }
    }

    @Test
    fun typedItemWithMatcher_noItemAtPosition() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        try {
            Screen.recyclerInRecycler.list.horizontalList.cellWithTitle("2", 0)
                .title.checks.displayedWithText("3")
        } catch (e: Exception) {
            Truth.assertThat(e)
                .checkCausesDeeply {
                    isInstanceOf(AssertionError::class.java)
                    hasMessageThat()
                        .contains("No matched item in recycler at position 0. Search near items from 0 to 3 has matches at positions: [2]")
                }
        }
    }

    @Test
    fun typedItemWithMatcher_indexOutOfBound() {
        rule.launchActivity(RecyclerInRecyclerActivity.intent(arrayListOf("0", "1", "2")))

        try {
            Screen.recyclerInRecycler.list.horizontalList.cellAt(3)
                .title.checks.displayedWithText("3")
        } catch (e: Exception) {
            Truth.assertThat(e)
                .checkCausesDeeply {
                    isInstanceOf(AssertionError::class.java)
                    hasMessageThat()
                        .contains("Tried to match item at position 3. But recycler size is 3")
                }
        }
    }

    private fun ThrowableSubject.checkCausesDeeply(check: ThrowableSubject.() -> Unit) {
        try {
            check(this)
        } catch (e: java.lang.AssertionError) {
            hasCauseThat().checkCausesDeeply(check)
        }
    }
}
