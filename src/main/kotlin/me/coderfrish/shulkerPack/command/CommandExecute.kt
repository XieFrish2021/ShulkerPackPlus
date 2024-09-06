package me.coderfrish.shulkerPack.command

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandExecute(val name: String)
