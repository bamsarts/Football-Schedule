package me.bamsarts.footballschedule.detail

import android.service.autofill.FieldClassification
import me.bamsarts.footballschedule.model.Match

interface DetailView {
    fun showLoading()
    fun hideLoading()

    fun initValue(match: Match)
}