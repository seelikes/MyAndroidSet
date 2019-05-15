package com.example.myjetpackapplication.sophix

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.example.myjetpackapplication.BuildConfig
import com.example.myjetpackapplication.MyJetPackApplication
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixApplication
import com.taobao.sophix.SophixEntry
import com.taobao.sophix.SophixManager

/**
 * Created by liutiantian on 2019-05-14 21:25 星期二
 */
class SophixStubApplication : SophixApplication() {
    companion object {
        const val SP_APP = "NvIL91HmJ1F35Dd2263gx1hVpT63"
        const val KEY_HOT_FIX_RELAUNCH = "foE62YCJYgRH4iw"
    }

    @SophixEntry(MyJetPackApplication::class)
    class RealApplicationStub

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        with(SophixManager.getInstance()) {
            setContext(this@SophixStubApplication)
            setAppVersion(BuildConfig.VERSION_NAME)
            setSecretMetaData("26128289", "2491fea9338215bae082b44d32ace68c", "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC3cEkM+YvWX9lrFwSpRGvTx6iQQAkXUlgNBfy619hcUHG06vS94oqz2ct+iqG9lZit+Q5fLY88ZfwZTPiRvw6TbCD/qU+z0tMv6UCQ3h0JXGCXOqtjsrsrsQq4SYVQia8/Lh7yNH2fhVZliZ2qE4xnzRg/WHrnYDE2L31Qyk5ko8Wp6WIyDlal2yBWimzkSKlXaSNC9zvMj3XezXSK7EwaJ9zuEp7zFb+P7R0uitqSPnywx6bXTgvBQXjkLtw2Dl2PCRutRs2/WnqVjMXkISLk68e+OlD4LkLd54dJGtSgMGv+EgX21hAFD6xqbfUgGVTu8kz9uhcIF24FMXUbL1T9AgMBAAECggEAMoFb25KpibCCMBX6VpBcD8cdBfpbhmxJiexoFRlwnJoZdHJsSAujnLkOahzJQ2V40A2Og24LzdlWe7DRj29HGBfhTgLTyvL06nASMtC0tesVPQ4OOjjvh6wLA4pahj4iXgbYxl7lZ5JSo0oserM/KSUy094MmL/HTN/uZNwOkW597MP/nYc/9thMT5bZryKuo9QwCG9ux+IQ8S75bvoUl8K47a0CdU6igo8YhvP62Wocv44mGlZSeoKYBUucJhPFH21LMnFGE6JOkYII/eG4T6ueUDnUuQgLTYWf2e0QoXIConIoYYfw3pJ4bH6g1ead+A0ItM/gFQqYFBR5TLTdgQKBgQDxSdL1rfRC20d1ABZXPoPiJcihQilMEX6lc72odE/zZnoPXaenXdkAnQ4DOiEbc5gsPspdtOpUsC70xLWjgyZiRvHDBDt7Oh3WEmU09+cfftPQKCelbB97Lyj0sv+ZerXvprlsET4wKjFWKCJ6QjSbKftN7qmx/kwM9L5xkP6DiQKBgQDCn4HKMFrO3ezHbj5hfoyR+7sgKDJWr2t9O60vX5ksscJWNn+CP1vY+uhASe2qRcsr0VhnJfVwHf8Dz/jGfltPK3RvpdwKxn5/khI/0/Swsa+TT5ZRKQSPa8QdWM2eBr05B249V3x8nrkCdCKLURqj4fpyhO0G0eoQqwdlXD/E1QKBgQDpGpsWAnJiTJniLK1op9qdlkcXk2V2OciSaxilL4k16qCtS5t2k7HbZ99rurLVZH108Uxr7yYbnwIeMhQQ3dktRMC32NTmHaVJdQgVfhHpWYukI8b5h1V6mnsEvBE241f1KKsl/hqISZrUU33Lgz5zUMGZ7qI+dSBfu+poqN1ZkQKBgQCwb9RFDSM1yOEAR0RMNGKRcYnJ9Eb2aR9kL/aoLN17NEd+yvc7h97nTUfCCINqSs2OksG2+SWRl8/5j8/zeteiZT2wfceHCT3ivvTGYovAp8UJIiyetTXKI6NuC3SUn31ptv/MooRbobUNsWUjscoZ6iKXMUSEW0b8B2C0FjZFZQKBgQC73ddtUwQUmm99vGKMKEYyKkIfkEFJQZk6pgEUT3pQLhQb0jUqZEhIIFZL3t6n80s9gqn3gOYc3wxTNlAGfn5LjCu0zPbkn6SFxFKV4oUqTxcyx7YODqsNxNkAVtKhsgubBZ+KoOuRGcsoH0OWHZLnQ385omlzXoncf9LywccXsQ==")
            setEnableDebug(BuildConfig.DEBUG)
            if (BuildConfig.DEBUG) {
                setEnableFullLog()
            }
            setPatchLoadStatusStub { _, code, _, _ ->
                when (code) {
                    PatchStatus.CODE_LOAD_SUCCESS -> Log.i(SophixStubApplication::class.java.simpleName, "attachBaseContext.UL1234LP, sophix load patch success!")
                    PatchStatus.CODE_LOAD_RELAUNCH -> {
                        Log.i(SophixStubApplication::class.java.simpleName, "attachBaseContext.UL1234LP, sophix preload patch success. restart app to make effect.")
                        getSharedPreferences(SP_APP, MODE_PRIVATE).edit().putBoolean(KEY_HOT_FIX_RELAUNCH, true).apply()
                    }
                }
            }
            initialize()
        }
    }
}