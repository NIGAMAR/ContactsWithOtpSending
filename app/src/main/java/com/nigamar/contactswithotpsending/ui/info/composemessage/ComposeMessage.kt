package com.nigamar.contactswithotpsending.ui.info.composemessage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.nigamar.contactswithotpsending.BuildConfig
import com.nigamar.contactswithotpsending.R
import com.nigamar.contactswithotpsending.data.db.entities.SentMessage
import com.nigamar.contactswithotpsending.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.compose_message_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.OffsetDateTime
import com.nigamar.contactswithotpsending.data.network.MessageObj
import com.nigamar.contactswithotpsending.internals.*



class ComposeMessage : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: ComposeMessageViewModelFactory by instance()
    private lateinit var viewModel: ComposeMessageViewModel
    private var otp:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.compose_message_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(ComposeMessageViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        checkSmsPermission(context!!)
    }

    private fun checkSmsPermission(context: Context) {
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(
                arrayOf(Manifest.permission.SEND_SMS),
                SEND_SMS_PERMISSION_REQUEST
            )
        }else{
            continueWithNormalFlow()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            SEND_SMS_PERMISSION_REQUEST->{
                val granted=permissions[0].equals(Manifest.permission.SEND_SMS) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                if(!granted){
                    disableButton()
                    composedMessage.setText(R.string.permission_denied_message)
                }else{
                    continueWithNormalFlow()
                }
            }
        }
    }

    private fun continueWithNormalFlow() {
        otp=(100001 until 999999).random()
        val message="Hi your OTP is ${otp}"
        composedMessage.setText(message)
        val contactName=ComposeMessageArgs.fromBundle(arguments).contactName
        sendOtp.setOnClickListener {
            launch {
                val sendMessage=SentMessage(otp,contactName,OffsetDateTime.now())
                val messageObj= MessageObj("Arpit Nigam",BuildConfig.CONTACT_NUMBER,message)
                viewModel.sendTextMessage(messageObj)
                viewModel.insertMesage(sendMessage)
            }
        }
    }

    private fun disableButton() {
        sendOtp.setBackgroundColor(resources.getColor(R.color.disableButton))
        sendOtp.isClickable=false
    }
}
