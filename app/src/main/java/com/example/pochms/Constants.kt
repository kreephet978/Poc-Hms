package com.example.myhmspushnoti.data

interface Constants {
    interface NotificationChannelOne {
        companion object {
            const val ID = "channel_1"
            const val NAME = "Channel 1"
            const val DESCRIPTION = "Notifications from Channel 1"
        }
    }

    interface NotificationChannelTwo {
        companion object {
            const val ID = "channel_2"
            const val NAME = "Channel 2"
            const val DESCRIPTION = "Notifications from Channel 2"
        }
    }
}