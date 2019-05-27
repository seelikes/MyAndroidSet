package com.example.myjetpackapplication.business.speech.baidu

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.baidu.tts.client.SpeechError
import com.baidu.tts.client.SpeechSynthesizer
import com.baidu.tts.client.SpeechSynthesizerListener
import com.baidu.tts.client.TtsMode
import com.example.myjetpackapplication.BuildConfig
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicActivity
import com.example.myjetpackapplication.databinding.ActivitySpeechBaiduBinding
import com.example.myjetpackapplication.utils.copyAssets
import com.orhanobut.logger.Logger

@Route(path = "/business/speech/baidu")
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

            override fun onSynthesizeDataArrived(utteranceId: String?, bytes: ByteArray?, progress: Int) {
                Logger.i("onCreate.synthesizer.onSpeechStart, utteranceId: $utteranceId; bytes: ${bytes?.joinToString(separator = "") { byte -> byte.toString(16) }}; progress: $progress")
            }

            override fun onError(utteranceId: String?, error: SpeechError?) {
                Logger.i("onCreate.synthesizer.onSpeechStart, utteranceId: $utteranceId; error.code: ${error?.code}; error.description: ${error?.description}")
            }
        })
        synthesizer.setAppId(BuildConfig.BAIDU_SPEECH_APP_ID)
        synthesizer.setApiKey(BuildConfig.BAIDU_SPEECH_APP_KEY, BuildConfig.BAIDU_SPEECH_APP_SECRET)
        synthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0")
        synthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9")
        synthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5")
        synthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5")
        synthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT)
        synthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, copyAssets(this, "bd_etts_text.dat"))
        synthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, copyAssets(this, "bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat"))
        val authInfo = synthesizer.auth(TtsMode.MIX)
        if (!authInfo.isSuccess) {
            Toast.makeText(this, R.string.speech_baidu_err_auth, Toast.LENGTH_LONG).show()
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
}