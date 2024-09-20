package me.coderfrish.shulkerPack.command

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Deprecated(message = "null.")
annotation class CommandExecute(val name: String)
