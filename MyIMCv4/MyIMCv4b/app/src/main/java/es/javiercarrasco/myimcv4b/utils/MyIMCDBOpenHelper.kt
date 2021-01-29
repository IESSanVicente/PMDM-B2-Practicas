package es.javiercarrasco.myimcv4b.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import es.javiercarrasco.myimcv4b.data.MyIMC

class MyIMCDBOpenHelper(
    context: Context, factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    val TAG = "SQLite"

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "imcDB.db"
        val TABLA_IMC = "imc"
        val COLUMNA_ID = "_id"
        val COLUMNA_FECHA = "fecha"
        val COLUMNA_SEXO = "sexo"
        val COLUMNA_IMC = "imc"
        val COLUMNA_PESO = "peso"
        val COLUMNA_ALT = "altura"
        val COLUMNA_EST = "estado"
    }

    /**
     * Método opcional. Se llamará a este método después de abrir la base de
     * datos, antes de ello, comprobará si está en modo lectura. Se llama justo
     * después de establecer la conexión y crear el esquema.
     */
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d("$TAG (onOpen)", "¡¡Base de datos abierta!!")
    }

    /**
     * Este método es llamado cuando se crea la base por primera vez. Debe
     * producirse la creación de todas las tablas que formen la base de datos.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val crearTablaAmigos = "CREATE TABLE $TABLA_IMC " +
                    "($COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMNA_FECHA TEXT, " +
                    "$COLUMNA_SEXO TEXT, " +
                    "$COLUMNA_IMC TEXT, " +
                    "$COLUMNA_PESO TEXT, " +
                    "$COLUMNA_ALT TEXT, " +
                    "$COLUMNA_EST TEXT)"
            db!!.execSQL(crearTablaAmigos)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }
    }

    /**
     * Este método se invocará cuando la base de datos necesite ser actualizada.
     * Se utiliza para hacer DROPs, añadir tablas o cualquier acción que
     * actualice el esquema de la BD.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropTablaIMCs = "DROP TABLE IF EXISTS $TABLA_IMC"
            db!!.execSQL(dropTablaIMCs)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    // Añade un nuevo IMC a la BD.
    fun addIMC(datos: MyIMC) {
        // Se crea un ArrayMap<>() con los datos a insertar.
        val data = ContentValues()
        data.put(COLUMNA_FECHA, datos.fecha)
        data.put(COLUMNA_SEXO, datos.sexo)
        data.put(COLUMNA_IMC, datos.imc.toString())
        data.put(COLUMNA_PESO, datos.peso.toString())
        data.put(COLUMNA_ALT, datos.altura.toString())
        data.put(COLUMNA_EST, datos.estado)

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase
        db.insert(TABLA_IMC, null, data)
        db.close()

        Log.d("$TAG (addIMC)", "IMC añadido correctamente")
    }

    // Elimina el registro IMC indicado por el identificador.
    fun delIMC(identificador: Int): Int {
        val args = arrayOf(identificador.toString())

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase
        val result = db.delete(TABLA_IMC, "$COLUMNA_ID = ?", args)
        db.close()

        Log.d("$TAG (delIMC)", "IMC ${identificador} eliminado correctamente")
        return result
    }
}