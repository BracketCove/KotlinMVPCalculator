package com.wiseassblog.kotlincalculator.data.datamodel

/**
 * Data class for an Operator. Operator is one of:
 * - char "x"; multiply
 * - char "/"; divide
 * - char "+"; add
 * - char "-"; Subtract
 *
 * "x" and "/" are to be evaluated before "+" and "-" as per BEDMAS
 * Created by R_KAY on 9/25/2017.
 */

data class Operator(val operatorValue: Char,
                    val evaluateFirst: Boolean)

