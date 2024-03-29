package com.xxh.jetpacksample.ioc.hilt.codelab.logs.data

interface LoggerDataSource {
    fun addLog(msg:String)
    fun getAllLogs(callback:(List<Log>)->Unit)
    fun removeLogs()
}