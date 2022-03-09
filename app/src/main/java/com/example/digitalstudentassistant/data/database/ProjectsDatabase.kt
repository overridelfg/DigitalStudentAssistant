package com.example.digitalstudentassistant.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    ProjectEntity::class
], version = 1, exportSchema = true)
abstract class ProjectsDatabase : RoomDatabase() {

    abstract fun projectsDao(): ProjectsDao

    companion object{
        @Volatile
        private var INSTANCE: ProjectsDatabase? = null

        fun create(context: Context): ProjectsDatabase{
            synchronized(this){
                if(INSTANCE == null){
                    INSTANCE =  Room.databaseBuilder(context.applicationContext, ProjectsDatabase::class.java, "projects_table").build()
                }
            }
            return INSTANCE!!
        }
    }
}