package com.rsschool.android2021

interface ActionEventListener {
    fun onFirstFragmentActionEvent(min: Int, max: Int)
    fun onSecondFragmentActionEvent(previousNumber: Int)
}