package com.example.prakt_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prakt_1.ui.theme.Prakt_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prakt_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorSwitcher(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

fun checkAndToDouble(input: String): Double? {
    return try {
        val value = input.toDouble()
        if (value >= 0) value else null // Если отрицательное
    } catch (e: NumberFormatException) { // Если не число
        null
    }
}

// Перемикання мыж калькуляторами
@Composable
fun CalculatorSwitcher(modifier: Modifier = Modifier) {

    var selectedCalculator by remember { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Кнопки для вибору калькулятора
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { selectedCalculator = 1 }) {
                Text("Завдання 1")
            }
            Button(onClick = { selectedCalculator = 2 }) {
                Text("Завдання 2")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedCalculator) {
            1 -> Task1()  // Перший калькулятор
            2 -> Task2() // Другий калькулятор
        }
    }
}

// Перший калькулятор
@Composable
fun Task1(modifier: Modifier = Modifier) {
    var hp by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }
    var sp by remember { mutableStateOf("") }
    var np by remember { mutableStateOf("") }
    var op by remember { mutableStateOf("") }
    var wp by remember { mutableStateOf("") }
    var ap by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()



    fun calculate(
        hp: Double, cp: Double, sp: Double, np: Double, op: Double, wp: Double, ap: Double
    ): String {
        val kpc = 100 / (100 - wp)
        val krg = 100 / (100 - wp - ap)

        val hc = hp * kpc
        val cc = cp * kpc
        val sc = sp * kpc
        val nc = np * kpc
        val oc = op * kpc
        val ac = ap * kpc
        val totalCheck = hc + cc + sc + nc + oc + ac

        val hg = hp * krg
        val cg = cp * krg
        val sg = sp * krg
        val ng = np * krg
        val og = op * krg
        val totalCheck2 = hg + cg + sg + ng + og

        val qph = 339 * cp + 1030 * hp - 108.8 * (op - sp) - 25 * wp
        val qch = (qph + 0.025 * wp) * 100.0 / (100 - wp)
        val qgh = (qph + 0.025 * wp) * 100.0 / (100 - wp - ap)

        return if (totalCheck == 100.0 && totalCheck2 == 100.0) {
            """
            Коефіцієнт переходу від робочої до сухої маси: $kpc
            Коефіцієнт переходу від робочої до горючої маси: $krg
            Cклад сухої маси палива:
            Hc = $hc %
            Cc = $cc %
            Sc = $sc %
            Nc = $nc %
            Oc = $oc %
            Ac = $ac %
            
            Cклад горючої маси палива:
            Hg = $hg %
            Cg = $cg %
            Sg = $sg %
            Ng = $ng %
            Og = $og %
            
            Теплота згорання робочої маси: $qph кДж/кг
            Теплота згорання сухої маси: $qch кДж/кг
            Теплота згорання горючої маси: $qgh кДж/кг
            """.trimIndent()
        } else {
            "Помилка в розрахунках"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = hp, onValueChange = { hp = it }, label = { Text("Hp") })
        TextField(value = cp, onValueChange = { cp = it }, label = { Text("Cp") })
        TextField(value = sp, onValueChange = { sp = it }, label = { Text("Sp") })
        TextField(value = np, onValueChange = { np = it }, label = { Text("Np") })
        TextField(value = op, onValueChange = { op = it }, label = { Text("Op") })
        TextField(value = wp, onValueChange = { wp = it }, label = { Text("Wp") })
        TextField(value = ap, onValueChange = { ap = it }, label = { Text("Ap") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val hpValue = checkAndToDouble(hp)
            val cpValue = checkAndToDouble(cp)
            val spValue = checkAndToDouble(sp)
            val npValue = checkAndToDouble(np)
            val opValue = checkAndToDouble(op)
            val wpValue = checkAndToDouble(wp)
            val apValue = checkAndToDouble(ap)

            if (hpValue == null || cpValue == null || spValue == null || npValue == null || opValue == null || wpValue == null || apValue == null) {
                errorMessage = "Одне з полів порожнє або містить неправильні дані!"
                result = ""
            } else {
                result = calculate(hpValue, cpValue, spValue, npValue, opValue, wpValue, apValue)
                errorMessage = ""
            }
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}

// Другий калькулятор
@Composable
fun Task2(modifier: Modifier = Modifier) {
    var cg by remember { mutableStateOf("") }
    var hg by remember { mutableStateOf("") }
    var og by remember { mutableStateOf("") }
    var sg by remember { mutableStateOf("") }
    var qi by remember { mutableStateOf("") }
    var vg by remember { mutableStateOf("") }
    var wg by remember { mutableStateOf("") }
    var ag by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    // Простой расчет для примера (сложение двух чисел)
    fun calculate2(cg: Double, hg: Double, og: Double, sg: Double, qi: Double, vg: Double,
                        wg: Double, ag: Double): String {
        val cp = cg * (100 - wg - ag) / 100.0
        val hp = hg * (100 - wg - ag) / 100.0
        val op = og * (100 - wg - ag) / 100.0
        val sp = sg * (100 - wg - ag) / 100.0
        val ap = ag * (100 - wg) / 100.0
        val vp = vg * (100 - wg) / 100.0

        val qri = qi * (100 - wg - ag/100) - 0.025 * wg

        return """
            Склад робочої маси мазуту:
            Вуглець Сp = $cp %
            Водень Hp = $hp %
            Кисень Op = $op %
            Сірка Sp = $sp %
            Зола Ap = $ap %
            Ванадій Vp = $vp мг/кг
           
            Нижча теплота згоряння мазуту на робочу масу для робочої маси за заданим складом
            компонентів палива: $qri кДж/кг
            """.trimIndent()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = cg, onValueChange = { cg = it }, label = { Text("Cg") })
        TextField(value = hg, onValueChange = { hg = it }, label = { Text("Hg") })
        TextField(value = og, onValueChange = { og = it }, label = { Text("Og") })
        TextField(value = sg, onValueChange = { sg = it }, label = { Text("Sg") })
        TextField(value = qi, onValueChange = { qi = it }, label = { Text("Qi") })
        TextField(value = vg, onValueChange = { vg = it }, label = { Text("Vg") })
        TextField(value = wg, onValueChange = { wg = it }, label = { Text("Wg") })
        TextField(value = ag, onValueChange = { ag = it }, label = { Text("Ag") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val cgValue = checkAndToDouble(cg)
            val hgValue = checkAndToDouble(hg)
            val ogValue = checkAndToDouble(og)
            val sgValue = checkAndToDouble(sg)
            val qiValue = checkAndToDouble(qi)
            val vgValue = checkAndToDouble(vg)
            val wgValue = checkAndToDouble(wg)
            val agValue = checkAndToDouble(ag)

            if (cgValue == null || hgValue == null || ogValue == null || sgValue == null
                || qiValue == null || vgValue == null || wgValue == null || agValue == null) {
                errorMessage = "Одне з полів порожнє або містить неправильні дані!"
                result = ""
            } else {
                result = calculate2(cgValue, hgValue, ogValue, sgValue, qiValue, vgValue, wgValue,agValue)
                errorMessage = ""
            }
        }) {
            Text("Розрахувати")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(result)
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorSwitcherPreview() {
    Prakt_1Theme {
        CalculatorSwitcher()
    }
}