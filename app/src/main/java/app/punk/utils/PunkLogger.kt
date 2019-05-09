package app.punk.utils

import app.punk.util.Logger
import com.crashlytics.android.Crashlytics
import timber.log.Timber

class PunkLogger : Logger {
    fun setup(debugMode: Boolean) {
        if (debugMode) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun v(message: String, vararg args: Any) {
        Timber.v(message, args)
        logToCrashlytics(message, args)
    }

    override fun v(t: Throwable, message: String, vararg args: Any) {
        Timber.v(t, message, args)
        logToCrashlytics(message, args)
    }

    override fun v(t: Throwable) {
        Timber.v(t)
    }

    override fun d(message: String, vararg args: Any) {
        Timber.d(message, args)
        logToCrashlytics(message, args)
    }

    override fun d(t: Throwable, message: String, vararg args: Any) {
        Timber.d(t, message, args)
        logToCrashlytics(message, args)
    }

    override fun d(t: Throwable) {
        Timber.d(t)
    }

    override fun i(message: String, vararg args: Any) {
        Timber.i(message, args)
        logToCrashlytics(message, args)
    }

    override fun i(t: Throwable, message: String, vararg args: Any) {
        Timber.i(t, message, args)
        logToCrashlytics(message, args)
    }

    override fun i(t: Throwable) {
        Timber.i(t)
    }

    override fun w(message: String, vararg args: Any) {
        Timber.w(message, args)
        logToCrashlytics(message, args)
    }

    override fun w(t: Throwable, message: String, vararg args: Any) {
        Timber.w(t, message, args)
        logToCrashlytics(message, args)
    }

    override fun w(t: Throwable) {
        Timber.w(t)
    }

    override fun e(message: String, vararg args: Any) {
        Timber.e(message, args)
        logToCrashlytics(message, args)
    }

    override fun e(t: Throwable, message: String, vararg args: Any) {
        Timber.e(t, message, args)
        logToCrashlytics(message, args)
    }

    override fun e(t: Throwable) {
        Timber.e(t)
    }

    override fun wtf(message: String, vararg args: Any) {
        Timber.wtf(message, args)
        logToCrashlytics(message, args)
    }

    override fun wtf(t: Throwable, message: String, vararg args: Any) {
        Timber.wtf(t, message, args)
        logToCrashlytics(message, args)
    }

    override fun wtf(t: Throwable) {
        Timber.wtf(t)
    }

    private fun logToCrashlytics(message: String, vararg args: Any) {
        if (args.isNotEmpty()) {
            Crashlytics.log(message.format(args))
        } else {
            Crashlytics.log(message)
        }
    }
}