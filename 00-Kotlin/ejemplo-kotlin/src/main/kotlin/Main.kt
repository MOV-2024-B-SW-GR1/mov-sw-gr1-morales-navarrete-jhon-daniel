package org.example
import java.util.*

fun main(args: Array<String>) {
    // INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "Jhon";
    // inmutable = "Daniel" // Error!
    // MUTABLES
    var mutable: String = "Jhon"
    mutable = "Daniel" // Ok
    // VAL > VAR

    // Duck Typing
    val ejemploVariable = " Jhon Morales "
    ejemploVariable.trim()
    val edadEjemplo: Int = 12
    // ejemploVariable = edadEjemplo // Error!
    // Variables Primitivas
    val nombreProfesor: String = "Jhon Morales"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad:Boolean = true
    // Clases en Java
    val fechaNacimiento: Date = Date()


    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito
    imprimirNombre("JhooNnN Moraleleles")

    calcularSueldo(10.00) // solo paramtro requerido
    calcularSueldo(10.00,15.00,20.00) // parametro requerido y sobreescribir parametros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00) // usando el parametro bonoEspecial en 2da posicion
    // gracias a los parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa=14.00)
    // usando el parametro bonoEspecial en 1ra posicion
    // usando el parametro sueldo en 2da posicion
    // usando el parametro tasa en 3era posicion
    // gracias a los parametros nombrados
}
fun imprimirNombre(nombre:String): Unit{ //Unit es opcional, es similar al void
    fun otraFuncionAdentro(){
        println("Otra funcion adentro")
    }
    println("Nombre: $nombre") // Uso sin llaves
    println("Nombre: ${nombre}") // Uso con llaves opcional
    println("Nombre: ${nombre + nombre}") // Uso con llaves (concatenado)
    println("Nombre: ${nombre.uppercase()}") // uso con llaves (funcion)
    println("Nombre: $nombre.upppercase()") // INCORRECTO!
    // (no pueden usar sin llaves)
    otraFuncionAdentro()
}
fun calcularSueldo(
    sueldo:Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial:Double? = null // Opcional (nullable)
    // Variable? -> "?" Es Nullable (osea que puede en algun momento ser nulo)
):Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}
abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}
abstract class Numeros( // Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int , (parametro (sin modificador acceso))

    // Caso 2) Parametro y propiedad (atributo) (protected)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    parametroNoUsadoNoPropiedadDeLaClase:Int? = null
){
    init { // bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}
class Suma( // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
): Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
){

}