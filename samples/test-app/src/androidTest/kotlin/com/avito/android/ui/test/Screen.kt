package com.avito.android.ui.test

import com.avito.android.ui.test.retry.RetryScreen

object Screen {

    val distantViewOnScroll: DistantViewOnScrollScreen
        get() = DistantViewOnScrollScreen()

    val visibility: VisibilityScreen
        get() = VisibilityScreen()

    val overflow: OverflowMenuScreen
        get() = OverflowMenuScreen()

    val swipeRefresh: SwipeRefreshScreen
        get() = SwipeRefreshScreen()

    val textsElements: TextElementsScreen
        get() = TextElementsScreen()

    val retry: RetryScreen
        get() = RetryScreen()

    val buttons: ButtonsScreen
        get() = ButtonsScreen()

    val movingButton: MovingButtonScreen
        get() = MovingButtonScreen()

    val appBarScreen: AppBarScreen
        get() = AppBarScreen()

    val snackbarScreen: SnackbarScreen
        get() = SnackbarScreen()

    val identicalCellsRecycler: IdenticalCellsRecyclerScreen
        get() = IdenticalCellsRecyclerScreen()

    val buttonsOverRecycler: ButtonsOverRecyclerScreen
        get() = ButtonsOverRecyclerScreen()

    val statefulRecyclerViewAdapterScreen: StatefulRecyclerViewAdapterScreen
        get() = StatefulRecyclerViewAdapterScreen()

    val recyclerAsLayout: RecyclerAsLayoutScreen
        get() = RecyclerAsLayoutScreen()

    val viewPagerScreen: ViewPagerScreen
        get() = ViewPagerScreen()

    val longRecycler: LongRecyclerScreen
        get() = LongRecyclerScreen()

    val recyclerInRecycler: RecyclerInRecyclerLayoutScreen
        get() = RecyclerInRecyclerLayoutScreen()

    val editTextScreen: EditTextScreen
        get() = EditTextScreen()

    val tabLayoutScreen: TabLayoutScreen
        get() = TabLayoutScreen()

    val backgroundDrawableScreen: BackgroundDrawableScreen
        get() = BackgroundDrawableScreen()

    val recyclerWithSingleLongItemScreen: RecyclerWithSingleLongItemScreen
        get() = RecyclerWithSingleLongItemScreen()

    val recyclerDescendantLevelsScreen: RecyclerDescendantLevelsScreen
        get() = RecyclerDescendantLevelsScreen()
}
