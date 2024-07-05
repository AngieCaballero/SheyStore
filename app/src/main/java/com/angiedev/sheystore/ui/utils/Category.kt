package com.angiedev.sheystore.ui.utils

enum class Category(val id: Int, name: String, image: String) {

    MUJER (
        1,
        "Mujer",
        "https://cdn-icons-png.flaticon.com/512/5529/5529133.png"
    ),
    HOMBRE (
        2,
        "Hombre",
        "https://cdn-icons-png.flaticon.com/512/10416/10416145.png"
    ),
    NINO (
        3,
        "Niño",
        "https://cdn-icons-png.flaticon.com/512/5069/5069208.png"
    ),
    NINA (
        4,
        "Niña",
        "https://cdn-icons-png.flaticon.com/512/5529/5529133.png"
    ),
    BEBES (
        5,
        "Bebés",
        "https://cdn-icons-png.flaticon.com/512/5529/5529133.png"
    ),
    FAMILIA (
        6,
        "Familia",
        "https://cdn-icons-png.flaticon.com/512/12442/12442152.png"
    ),
    OTROS (
        7,
        "Otros",
        "https://cdn-icons-png.flaticon.com/512/12442/12442142.png"
    ),
    ACCESORIOS (
        8,
        "Accesorios",
        "https://cdn-icons-png.flaticon.com/128/10785/10785670.png"
    );

    companion object {
        val all = entries.toTypedArray()

        fun getIdByName(name: String): Int? {
            return entries.find { it.name == name }?.id
        }
    }
}