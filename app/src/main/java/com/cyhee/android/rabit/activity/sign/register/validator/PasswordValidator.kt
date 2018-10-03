package com.cyhee.android.rabit.activity.sign.register.validator

import android.widget.TextView

class PasswordValidator(
        private val special: Boolean = false, private val upper: Boolean = false,
        private val min: Int = 8, private val max: Int = 20) {

    fun valid(textView: TextView): Boolean {
        val password = textView.text
        if(password.length !in min..max) {
            textView.error = "$min-$max 길이의 패스워드를 입력하세요."
            return false
        }
        if(!password.matches("[A-Za-z\\d@\$!%*#?&]+".toRegex())) {
            textView.error = "영문자 대소문자, 숫자, 특수문자(@$!%*#?&)로 이루어진 패스워드를 입력하세요."
            return false
        }
        if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@\$!%*#?&]+".toRegex())) {
            textView.error = "하나 이상의 영문자와 숫자가 포함되지 않았습니다."
            return false
        }
        if(special and !password.contains("[@\$!%*#?&]".toRegex())) {
            textView.error = "특수문자(@$!%*#?&)가 포함되지 않았습니다."
            return false
        }
        if(upper and !password.matches("^(?=.*[A-Z])(?=.*[a-z]).+".toRegex())){
            textView.error = "하나 이상의 대소문자가 포함되지 않았습니다."
            return false
        }
        return true
    }
}