package com.avito.android.ui.test

import com.avito.android.ui.IdenticalCellsRecyclerActivity
import org.junit.Rule
import org.junit.Test

class IdenticalCellsRecyclerTest {

    @get:Rule
    val rule = screenRule<IdenticalCellsRecyclerActivity>()

    @Test
    fun typedItemWithMatcher_foundSecondItem_withAllDifferentValues() {
        rule.launchActivity(IdenticalCellsRecyclerActivity.intent(arrayListOf("1", "2", "3")))

        Screen.identicalCellsRecycler.list.cellWithTitle("2").title.checks.displayedWithText("2")
    }

    @Test
    fun typedItemWithMatcher_failedToFailValues_withIdenticalValues() {
        rule.launchActivity(IdenticalCellsRecyclerActivity.intent(arrayListOf("2", "2", "2")))

        Screen.identicalCellsRecycler.list.cellWithTitle("2").title.checks.displayedWithText("2")
    }

    @Test
    fun typedItemAtPosition_foundSecondItem_withAllDifferentValues() {
        rule.launchActivity(IdenticalCellsRecyclerActivity.intent(arrayListOf("1", "2", "3")))

        Screen.identicalCellsRecycler.list.cellAt(position = 1).title.checks.displayedWithText("2")
    }
}
