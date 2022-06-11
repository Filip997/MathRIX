package com.company.mathrix

import android.content.Context
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object FileHelperSubtraction {

    private const val FILENAME = "listUsersSubtraction.dat"

    fun saveData(users: ArrayList<User>, context: Context) {
        try {
            var fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
            var ous = ObjectOutputStream(fos)
            ous.writeObject(users)
            ous.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readData(context: Context) : ArrayList<User> {
        var users: ArrayList<User> = arrayListOf()

        try {
            var fis = context.openFileInput(FILENAME)
            var ois = ObjectInputStream(fis)
            users = ois.readObject() as ArrayList<User> /* = java.util.ArrayList<com.company.mathrix.User> */
        } catch (e: FileNotFoundException) {
            users = arrayListOf()
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return users
    }
}