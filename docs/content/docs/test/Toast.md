---
title: Testing toasts
type: docs
---

# Testing toasts

[android.widget.Toast](https://developer.android.com/reference/android/widget/Toast)

{{< hint warning>}}
Testing toasts is a hard because other toasts can show up and yours will be queued. 
We hack app with proxy object to mitigate this.
{{< /hint >}}

{{< hint warning>}}
It is also recommended to use [snackbar](https://developer.android.com/reference/com/google/android/material/snackbar/Snackbar), 
or your custom way to display brief unintrusive messages instead. So you should consider not testing it at all.
{{< /hint >}}

## Proxy toast

Use provided extension methods to be able to spy on `showToast` functions.

```kotlin
import com.avito.android.util.showToast

showToast("I'am a toast!")
```

```kotlin
implementation("com.avito.android:proxy-toast:$version")
```

## ToastRule

```kotlin
@get:Rule
val toastRule = ToastRule()

fun test() {
    toastRule.checks.toastDisplayedWithText("I'am a toast!")
}
```

```kotlin
androidTestImplementation("com.avito.android:toast-rule:$version")
```
