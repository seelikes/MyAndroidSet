package com.example.myjetpackapplication.business.speech.baidu

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.speech.baidu.databinding.ActivitySpeechBaiduBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.java.lib.oil.file.FileUtils
import com.orhanobut.logger.Logger
import java.io.File
import java.io.FileOutputStream

@Business(path = "/business/speech/baidu", title = "speech_baidu_title", parent = "speech_title")
class SpeechBaiduActivity : BasicActivity<SpeechBaiduActivity, SpeechBaiduViewModel, ActivitySpeechBaiduBinding>() {
    val synthesizer: SpeechSynthesizer = SpeechSynthesizer.getInstance()
    var utteranceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        synthesizer.setContext(this)
        synthesizer.setSpeechSynthesizerListener(object : SpeechSynthesizerListener {
            override fun onSynthesizeStart(utteranceId: String?) {
                Logger.i("onCreate.synthesizer.onSynthesizeStart, utteranceId: $utteranceId")
                this@SpeechBaiduActivity.utteranceId = utteranceId
            }

            override fun onSpeechFinish(utteranceId: String?) {
                Logger.i("onCreate.synthesizer.onSpeechFinish, utteranceId: $utteranceId")
            }

            override fun onSpeechProgressChanged(utteranceId: String?, progress: Int) {
                Logger.i("onCreate.synthesizer.onSpeechProgressChanged, utteranceId: $utteranceId; progress: $progress")
            }

            override fun onSynthesizeFinish(utteranceId: String?) {
                Logger.i("onCreate.synthesizer.onSynthesizeFinish, utteranceId: $utteranceId")
            }

            override fun onSpeechStart(utteranceId: String?) {
                Logger.i("onCreate.synthesizer.onSpeechStart, utteranceId: $utteranceId")
            }

            override fun onSynthesizeDataArrived(utteranceId: String?, bytes: ByteArray?, progress: Int, total: Int) {
                Logger.i("onCreate.synthesizer.onSpeechStart, utteranceId: $utteranceId; bytes: ${bytes?.joinToString(separator = "") { byte -> byte.toString(16) }}; progress: $progress")
            }

            override fun onError(utteranceId: String?, error: SpeechError?) {
                Logger.i("onCreate.synthesizer.onSpeechStart, utteranceId: $utteranceId; error.code: ${error?.code}; error.description: ${error?.description}")
            }
        })

        val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        Logger.i("BAIDU_SPEECH_APP_ID: ${applicationInfo.metaData.getInt("BAIDU_SPEECH_APP_ID")}")
        synthesizer.setAppId(applicationInfo.metaData.getInt("BAIDU_SPEECH_APP_ID").toString())
        Logger.i("BAIDU_SPEECH_APP_KEY: ${applicationInfo.metaData.getString("BAIDU_SPEECH_APP_KEY")}; BAIDU_SPEECH_APP_SECRET: ${applicationInfo.metaData.getString("BAIDU_SPEECH_APP_SECRET")}")
        synthesizer.setApiKey(applicationInfo.metaData.getString("BAIDU_SPEECH_APP_KEY"), applicationInfo.metaData.getString("BAIDU_SPEECH_APP_SECRET"))
        synthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0")
        synthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9")
        synthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5")
        synthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5")
        synthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT)
        synthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, copyAssets(this, "bd_etts_text.dat"))
        synthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, copyAssets(this, "bd_etts_common_speech_f7_mand_eng_high_am-mgc_v3.6.0_20190117.dat"))
        val authInfo = synthesizer.auth(TtsMode.MIX)
        if (!authInfo.isSuccess) {
            Toast.makeText(this, R.string.speech_baidu_err_auth, Toast.LENGTH_LONG).show()
            Logger.i("authInfo.notifyMessage: ${authInfo.notifyMessage}")
            for (field in authInfo.javaClass.fields) {
                Logger.i("authInfo.${field.name}: ${field.get(authInfo)}")
            }
            finish()
            return
        }
        val result = synthesizer.initTts(TtsMode.MIX)
        if (result != 0) {
            Toast.makeText(this, R.string.speech_baidu_err_init_tts, Toast.LENGTH_LONG).show()
            Logger.e("onCreate, result: $result")
            finish()
            return
        }
        Toast.makeText(this, R.string.speech_baidu_err_init_success, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        synthesizer.resume()
    }

    override fun onPause() {
        super.onPause()
        synthesizer.pause()
    }

    override fun onStop() {
        super.onStop()
        synthesizer.stop()
    }

    override fun initModel(savedInstanceState: Bundle?): SpeechBaiduViewModel = SpeechBaiduViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_speech_baidu))

    private fun copyAssets(context: Context, path: String): String {
        val input = context.assets.open(path)
        val targetFile = File(context.cacheDir, "${path.hashCode()}${path.substring(path.lastIndexOf("."))}")
        val output = FileOutputStream(targetFile)
        FileUtils.getInstance().writeStreamToStream(input, output)
        return targetFile.absolutePath
    }
}